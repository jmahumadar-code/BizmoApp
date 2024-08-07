// Initialize your app
var myApp = new Framework7({
    swipePanel: "left"
});

var $$ = Dom7;

var showMensaje = myApp.toast("","",{});

var cont_fn = 0;

var map = null;
var my_pos = null;
var circle = null;

var servicios;
var servicios_id;

var offerent;

const BG = 0; //Background
const FG = 1; //Foreground

var isMapaReady = false;

var estado = FG;

var snip = null;
var radio = null;

var left = false;
var right = false;

var verOff = false;

var radio_serv = myApp.picker({
    input: '#radio_serv',
    toolbarCloseText: 'Aceptar',
    cols: [
        {
            textAlign: 'center',
            values: ['Automático', '0.5 KM', '1 KM','5 KM', '10 KM', '25 KM', '50 KM', '100 KM', '150 KM']
        }
    ]
});

var fam_serv;
var tipo_serv;
var changeFam = false;
var idFam;
var tipoUser;

var popup_isopen = false;

var view_main = myApp.addView('.view-main', {
    dynamicNavbar: true,
    name: "main"
});

$('.panel-left').on('panel:open', function () {
    left = true;
    if(device.platform !== "browser"){
        map.setClickable(false);
    }
});
$('.panel-left').on('panel:close', function () {
    left = false;
    if(device.platform !== "browser"){
        map.setClickable(true);
    }
});

$('.popup-serv').on('popup:open', function () {
    if(device.platform !== "browser"){
        map.setClickable(false);
    }
    limpiarFormSol();
});

$('.popup-serv').on('popup:close', function () {
    if(device.platform !== "browser"){
        map.setClickable(true);
    }
    limpiarFormSol();
});

$('.popup').on('popup:close', function () {
    popup_isopen = false;
});

$('.popup').on('popup:open', function () {
    popup_isopen = true;
});

$('.popup-offer').on('popup:close', function(){
    if(device.platform !== "browser"){
        map.setClickable(true);
    }
});

$('.popup-offer').on('popup:open', function(){
    if(device.platform !== "browser"){
        map.setClickable(false);
    }
});

document.addEventListener('deviceready', function(){
    if(device.platform !== "browser"){
        var mapDiv = document.getElementById("map");
        mapa_plugin(mapDiv);
        background_plugin();  
    }
        
    $('#env_soli').bind('click', envSolicitudServicio);
    
    $('#oferente').change(cambioModo);
    
    console.log(device.platform);
    if(device.platform === "Android"){
        $('#map').css('padding-top','45px');
        $('#cuadro_datos').css('padding-top','15px');
        $('.popup').css('padding-top','40px');
        console.log("cambiando a transparente");
        statusbarTransparent.enable();
        StatusBar.show();
    }
    //$("#page-main").css("padding-bottom", "50px");
    $("#map").css("height","100%");
        
    document.addEventListener("backbutton", handleBackButton, true);
    
    limpiarFormSol();
    getServicios();
    getConfig();
    cargar_datos();
    if(localStorage.getItem("idLogin") === null){
        cambioModo();
    }
}, false);

function cambioModo(){
    if($(this).prop('checked')){
        $('#searchBox').hide();
        $('#tab3').hide();
        tipoUser = "OFFER";
    }else{
        $('#tab3').show();
        $('#searchBox').show();
        tipoUser = "USER";
    }
    if(device.platform === "browser"){
        localStorage.setItem("lat","-36.60195421670085");
        localStorage.setItem("lon","-72.08333352326122");
    }
    $.ajax({
        dataType: 'json',
        type: 'GET',
        data: {
            sUID: localStorage.getItem("UID"),
            sType: tipoUser,
            lat: localStorage.getItem("lat"),
            lon: localStorage.getItem("lon"),
            mmovil: 1
        },
        url: 'http://170.239.86.159/m/ws/ws_set_login.php',
        success: function (data, status, xhr) {
            console.log("change mode user: "+data.hres)
            if(data.hres > 0){
                localStorage.setItem('idLogin', data.hres);
            }else{
                map.setClickable(false);
                myApp.alert('Error al seleccionar modo usuario.', 'BizAPP', function(){
                    map.setClickable(true);
                });
            }
        },
        error: function (xhr, status) {
            myApp.hidePreloader();
        }
    });

}

function limpiarFormSol(){
    $('#fam_serv').val('');
    $('#tipo_serv').val('');
    $("#tipo_serv").prop( "disabled", true);
    $('#descrip_serv').val('');
    $('#radio_serv').val('Automático');
}

function envSolicitudServicioSimple(){
    if(device.platform !== "browser"){
        map.setClickable(false);
    }
    var rad = 0;
    var tipo = 2080;
    var desc = $('#query').val();
    if(desc !== ""){
        myApp.showPreloader('Solicitando Servicio...');
        $.ajax({
            dataType: 'json',
            type: 'GET',
            data: {
                sFrom: localStorage.getItem('UID'),
                iLogin: localStorage.getItem('idLogin'),
                fk_service: tipo,
                fLat: localStorage.getItem('lat'),
                fLon: localStorage.getItem('lon'),
                sDesc: desc,
                fradius: rad,
                skeys: ''
            },
            url: 'http://170.239.86.159/m/ws/ws_ins_get_service.php',
            success: function (data, status, xhr) {
                if(data.hres > 0){
                    localStorage.setItem('idServicioSol',data.hres);
                    radio = rad*1000;
                    localStorage.setItem('radioBus', radio);
                    if(device.platform !== "browser"){
                        cargarUltimaPos();
                    }
                    $.ajax({
                        dataType: 'json',
                        type: 'GET',
                        data: {
                            idservice: localStorage.getItem('idServicioSol')
                        },
                        url: 'http://170.239.86.159/m/ws/ws_spam_service.php',
                        success: function (data, status, xhr) {
                            myApp.hidePreloader();
                            if(data.hres !== '-1'){
                                myApp.closeModal();
                                llenarOffer(data);
                            }else{
                                myApp.alert('Se ha producido un error en la solicitud, intente nuevamente.', 'BizAPP');
                            }
                        },
                        error: function (xhr, status) {
                            console.log("error............");
                            myApp.hidePreloader();
                        }
                    }); 
                }else{
                    if(data.hres === -1){
                        myApp.hidePreloader();
                        myApp.alert('Se ha producido un error en la solicitud, intente nuevamente.', 'BizAPP');
                    }else{
                        myApp.hidePreloader();
                        myApp.alert('Hay una sesión activa iniciada en otro equipo, se cerrará la sesión', 'BizAPP',function(){
                            logout();
                        });
                    }
                }
            },
            error: function (xhr, status) {
                console.log("error...");
                myApp.hidePreloader();
            }
        }); 
    }else{
        myApp.alert('Debe especificar lo que busca', 'BizAPP', function(){
            map.setClickable(true);
        });
    }
}

function envSolicitudServicio(){    
    var tipo = $('#tipo_serv').val();
    var desc = $('#descrip_serv').val();
    var rad = $('#radio_serv').val();
    if((tipo !== '' && tipo !== 'Otros') || (tipo !== '' && tipo === 'Otros' && desc !== '')){    
        if(rad !== "Automático"){
            rad = rad.replace('KM','').trim();
        }else{
            rad = '0';
        }
        for(i=0; i < servicios_id.length; i++){
            for(j=0; j < servicios_id[i].length; j++){
                var t = servicios_id[i][j][0];
                if(t === tipo){
                    tipo = servicios_id[i][j][1];
                    break;
                }
            }
        }
        myApp.showPreloader('Solicitando Servicio...');
        
        $.ajax({
            dataType: 'json',
            type: 'GET',
            data: {
                sFrom: localStorage.getItem('UID'),
                iLogin: localStorage.getItem('idLogin'),
                fk_service: tipo,
                fLat: localStorage.getItem('lat'),
                fLon: localStorage.getItem('lon'),
                sDesc: desc,
                fradius: rad,
                skeys: ''
            },
            url: 'http://170.239.86.159/m/ws/ws_ins_get_service.php',
            success: function (data, status, xhr) {
                if(data.hres > 0){
                    localStorage.setItem('idServicioSol',data.hres);
                    radio = rad*1000;
                    localStorage.setItem('radioBus', radio);
                    if(device.platform !== "browser"){
                        cargarUltimaPos();
                    }
                    $.ajax({
                        dataType: 'json',
                        type: 'GET',
                        data: {
                            idservice: localStorage.getItem('idServicioSol')
                        },
                        url: 'http://170.239.86.159/m/ws/ws_spam_service.php',
                        success: function (data, status, xhr) {
                            myApp.hidePreloader();
                            if(data.hres !== '-1'){
                                myApp.closeModal();
                                llenarOffer(data);
                            }else{
                                myApp.alert('Se ha producido un error en la solicitud, intente nuevamente.', 'BizAPP');
                            }
                        },
                        error: function (xhr, status) {
                            console.log("error............");
                            myApp.hidePreloader();
                        }
                    }); 
                }else{
                    if(data.hres === -1){
                        myApp.hidePreloader();
                        myApp.alert('Se ha producido un error en la solicitud, intente nuevamente.', 'BizAPP');
                    }else{
                        myApp.hidePreloader();
                        myApp.alert('Hay una sesión activa iniciada en otro equipo, se cerrará la sesión', 'BizAPP',function(){
                            logout();
                        });
                    }
                }
            },
            error: function (xhr, status) {
                console.log("error............");
                myApp.hidePreloader();
            }
        }); 
    }else{
        if(tipo === 'Otros' && desc === ''){
            myApp.alert('El tipo de servicio seleccionado es "Otros", es obligatorio definir una descripción de la solicitud.', 'BizAPP');
        }else{
            myApp.alert('Se debe definir un tipo de Servicio', 'BizAPP');
        }
    }
}

function llenarOffer(json){
    var htmltxt = '';
    offerent = null;
    if(json.length > 0){
        var rda = 0;
        localStorage.setItem('radioBus', 0)
        snip = " ";
        $('#tab2').show();
        var table = $('#tableOffer');
        table.empty();
        offerent = [];
        for(var i = 0; i < json.length - 1 ; i++){
            var uid1 = json[i].uid;
            var lat1 = json[i].lat
            var lon1 = json[i].lon;
            var name1 = json[i].fname;
            var apellido1 = json[i].lname;
            var phone1 = json[i].phone;
            var role1 = json[i].role;
            var gender1 = json[i].gender;
            var distancia1 = json[i].distancia;
            var valuation1 = json[i].valuation;
            var profilename1 = json[i].profile_name;
            var description1 = json[i].description;
            htmltxt += '<tr onClick="cargar_oferente(';
            htmltxt += "'"+uid1+"',"
            htmltxt += "'"+lat1+"',";
            htmltxt += "'"+lon1+"',";
            htmltxt += "'"+name1+" "+apellido1+"',";
            htmltxt += "'"+phone1+"',";
            htmltxt += "'"+role1+"',";
            htmltxt += "'"+gender1+"',";
            htmltxt += "'"+distancia1+"',";
            htmltxt += "'"+valuation1+"'";
            htmltxt += ');"';
            htmltxt += '>';
            htmltxt += '<td>'+name1+'</td>';
            htmltxt += '<td>'+apellido1+'</td>';
            htmltxt += '<td>'+profilename1+'</td>';
            htmltxt += '<td>'+description1+'</td>';
            htmltxt += '<td>'+Math.round(distancia1)+' KM</td>';
            htmltxt += '<td>'+cantestrellas(valuation1)+'</td>';
            htmltxt += '</tr>';
            offerent[i] = [];
            offerent[i][0] = uid1;
            offerent[i][1] = lat1;
            offerent[i][2] = lon1;
            offerent[i][3] = name1+" "+apellido1;
            offerent[i][4] = phone1;
            offerent[i][5] = role1;
            offerent[i][6] = gender1;
            offerent[i][7] = Math.round(distancia1);
            offerent[i][8] = valuation1;
            rda = distancia1;
            if(localStorage.getItem('radioBus') <= rda){
                rda = (rda * 1000) * 1.1;
                localStorage.setItem('radioBus', rda);
            }
        }        $("#cantidadOferentes").html(offerent.length);
        table.append(htmltxt);
            
        if(device.platform !== "browser"){
            map.setClickable(false);
        }
        
        myApp.popup('.popup-offer',false,true);
    }else{
        offerent = null;
        map.setClickable(false);
        myApp.alert('No hay oferentes para la búsqueda.','BizAPP', function(){
            map.setClickable(true);
        })
    }
}

function mostrarAllOff(){
    console.log('all');
    if(offerent != null){
        console.log('dist null');
        verOff = true;
        for(i = 1; i < offerent.length; i++){
            var posi = new plugin.google.maps.LatLng(offerent[i][1], offerent[i][2]);
            var path = "";
            if(device.platform === "iOS"){
                if(offerent[i][5] === "LEGAL"){
                    path =  "img/LEGAL.png";
                }else{
                    path =  "img/"+offerent[i][6]+".png";
                }

            }else{
                if(offerent[i][5] === "LEGAL"){
                    path = "file:///android_asset/www/img/LEGAL.png";
                }else{
                    path = "file:///android_asset/www/img/"+offerent[i][6]+".png";
                }

            }
            map.addMarker({
                'position': posi,
                'title': offerent[i][3],
                'snippet': "Distancia: "+Math.round(offerent[i][7]) + " KM \n Valoración: " + cantestrellas(offerent[i][8]),
                'styles' : {
                    'text-align': 'center',
                    'font-style': 'italic',
                    'font-weight': 'bold',
                    'color': 'red'
                },
                'icon': {
                    'url': path
                }
            });
        }
        posi = new plugin.google.maps.LatLng(localStorage.getItem("lat"), localStorage.getItem("lon"));
        
        console.log("radio" + localStorage.getItem('radioBus'));
        
        map.addCircle({
          'center': posi,
          'radius': localStorage.getItem('radioBus'),
          'strokeColor' : '#05395a',
          'strokeWidth': 2,
          'fillColor' : 'rgba(51, 153, 204, .2)'
        });
        
        map.moveCamera({
          'target': posi,
          'zoom': calculateZoom()
        }, function() {
          console.log("Camera position changed.");
        });
        myApp.closeModal();
        map.setClickable(true);
    }
}

function cerrarSesion(){
    myApp.modal({
        title:  'BizAPP',
        text: '¿Desea Cerrar su Sesión?',
        buttons: [
            {
                text: 'Cancelar',
                bold: true,
                close: true
            },
            {
                text: 'Cerrar Sesión',
                onClick: function() {
                    $('#tab2').hide();
                    backgroundGeolocation.stop();
                    localStorage.setItem('refresh','true');
                    map.remove();
                    logout();
                }
            },
        ]
    });
}

function logout(){
    myApp.showPreloader('Cerrando Sesión...');
    $.ajax({
        dataType: 'json',
        type: 'GET',
        data: {
            iLogin: localStorage.getItem('idLogin')
        },
        url: 'http://170.239.86.159/m/ws/ws_set_logout.php',
        success: function (data, status, xhr) {
            myApp.hidePreloader();
            if(data.hres >= 0){
                myApp.closePanel(false);
                localStorage.setItem('recordar', 'false');
                localStorage.removeItem('UID');
                localStorage.removeItem('fname');
                localStorage.removeItem('lname');
                localStorage.removeItem('picture');
                localStorage.removeItem('fk_country');
                localStorage.removeItem('phone');
                localStorage.removeItem('email');
                localStorage.removeItem('payment_status');
                localStorage.removeItem('type_card');
                localStorage.removeItem('type_user');
                localStorage.removeItem('idLogin');
                
                $(location).attr('href','index.html');
            }else{
                myApp.alert('Se ha producido un error al cerrar su sesión, intente nuevamente', 'BizAPP')
            }
        },
        error: function (xhr, status) {
            console.log('Error AJAX location.');
        }
    });
}

function cargar_oferente(uid, lat, lon, name, phone, role, gender, distancia, valuation){    
    myApp.modal({
        title:  'BizAPP',
        text: '¿Desea ver a este oferente en el Mapa?',
        buttons: [
            {
                text: 'Cancelar',
                close: true
            },
            {
                text: 'Ver',
                bold: true,
                onClick: function() {
                    verOff = true;
                    var posi = new plugin.google.maps.LatLng(lat, lon);
                    var path = "";
                    if(device.platform === "iOS"){
                        if(role === "LEGAL"){
                            path =  "img/LEGAL.png";
                        }else{
                            path =  "img/"+gener+".png";
                        }
                        
                    }else{
                        if(role === "LEGAL"){
                            path = "file:///android_asset/www/img/LEGAL.png";
                        }else{
                            path = "file:///android_asset/www/img/"+gender+".png";
                        }
                        
                    }
                    
                    map.addMarker({
                        'position': posi,
                        'title': name,
                        'snippet': "Distancia: "+Math.round(distancia)+" KM \n Valoración: " + cantestrellas(valuation),
                        'styles' : {
                            'text-align': 'center',
                            'font-style': 'italic',
                            'font-weight': 'bold',
                            'color': 'red'
                        },
                        'icon': {
                            'url': path
                        }
                    });
                    
                    if(localStorage.getItem('radioBus') == 0){
                        var dist = distancia * 1000;
                        dist = dist * 1.1;
                        localStorage.setItem('radioBus', dist);
                    }
                    
                    posi = new plugin.google.maps.LatLng(localStorage.getItem("lat"), localStorage.getItem("lon"));
                    
                    map.addCircle({
                      'center': posi,
                      'radius': localStorage.getItem('radioBus'),
                      'strokeColor' : '#05395a',
                      'strokeWidth': 2,
                      'fillColor' : 'rgba(51, 153, 204, .2)'
                    },function(cir){
                        circle = cir;
                    });
                    
                    map.moveCamera({
                      'target': posi,
                      'zoom': calculateZoom()
                    }, function() {
                      console.log("Camera position changed.");
                    });
                    
                    myApp.closeModal();
                    map.setClickable(true);
                }
            },
        ]
    });
}

function getConfig(){
    var country = localStorage.getItem("code_country");
    var id_country = 1;
    if(country === "CL"){
        $('#flag').attr('src','img/chile.png');
        id_country = 1;
    }else if(country === "NZ"){
        $('#flag').attr('src','img/newzeland.jpg');
    }else{
        $('#flag').attr('src','img/other.png');
    }
    if(device.platform !== "browser"){
        map.setClickable(true);
    }    
}

function getServicios(){
    console.log("serv paso 1");
    $.ajax({
        dataType: 'json',
        type: 'GET',
        data: {},
        url: 'http://170.239.86.159/m/ws/ws_lis_fam_services.php',
        success: function (data, status, xhr) {
            if(data.hres !== '-1'){
                console.log("serv paso 2");
                localStorage.setItem("cantFam",data.length);
                for(i = 0; i < data.length; i++){
                    localStorage.setItem("idFam"+i,data[i].idtfamily_services);
                    localStorage.setItem("desFam"+i,data[i].description);
                }
                $.ajax({
                    dataType: 'json',
                    type: 'GET',
                    data: {},
                    url: 'http://170.239.86.159/m/ws/ws_lis_fservices.php',
                    success: function (data, status, xhr) {
                        if(data.hres !== '-1'){
                            console.log("serv paso 3");
                            localStorage.setItem("cantSev",data.length);
                            servicios = new Array(localStorage.getItem("cantFam"));
                            servicios_id = new Array(localStorage.getItem("cantFam"));
                            for(i = 0; i < localStorage.getItem("cantFam");i++){
                                servicios[i] = new Array();
                                servicios_id[i] = new Array();
                                for(j = 0; j < data.length; j++){
                                    if(localStorage.getItem("idFam"+i) === data[j].idtfamily_services){
                                        var elemento = new Array();
                                        elemento.push(data[j].name);
                                        elemento.push(data[j].idtmaster_services);
                                        servicios_id[i].push(elemento);
                                        servicios[i].push(data[j].name);
                                    }
                                }
                            }
                            cargarServiciosPicker();
                            if(device.platform === "browser"){
                                $('.view-main').show();
                                $('.view-login').hide();
                            }
                        }
                    },
                    error: function (xhr, status) {
                        myApp.hidePreloader();
                        myApp.alert('No se han podido obtener los servicios.', 'BizAPP');
                    }
                });
            }
        },
        error: function (xhr, status) {
            myApp.hidePreloader();
            myApp.alert('No se han podido obtener los servicios.', 'BizAPP');
        }
    });
}

function cargarServiciosPicker(){
    var arr = new Array();
    for(i = 0; i < localStorage.getItem("cantFam"); i++){
        arr.push(localStorage.getItem("desFam"+i));
    }
    
    fam_serv = myApp.picker({
        input: '#fam_serv',
        toolbarCloseText: 'Aceptar',
        cols: [
            {
                textAlign: 'center',
                values: arr
            }
        ],
        onChange: function(p, values, displayValues){
            for(i = 0; i < localStorage.getItem("cantFam"); i++){
                if(localStorage.getItem("desFam"+i) === values[0]){
                    idFam = i;
                    break;
                }
            }
            $('#tipo_serv').val("");
            if(changeFam){
                tipo_serv.destroy();
                changeFam = true;
            }
            
            tipo_serv = myApp.picker({
                input: '#tipo_serv',
                toolbarCloseText: 'Aceptar',
                cols: [
                    {
                        textAlign: 'center',
                        values: servicios[idFam]
                    }
                ]
            });
            $("#tipo_serv").prop("disabled", false);
        }
    });
}

function handleBackButton(){
    if(!popup_isopen){
        map.setClickable(false);
        myApp.modal({
            title:  'BizAPP',
            text: '¿Desea salir de la aplicación?',
            buttons: [
                {
                    text: 'Cancelar',
                    bold: true,
                    onClick: function() {
                        map.setClickable(true);
                    }
                },
                {
                    text: 'Salir',
                    onClick: function() {
                        backgroundGeolocation.stop();
                        map.remove();
                        navigator.app.exitApp();
                    }
                },
            ]
        });
    }else{
        myApp.closeModal();
        map.setClickable(true);
    }
}

function mapa_plugin(mapDiv){
    map = plugin.google.maps.Map.getMap(mapDiv);
    var pos = null;
    if(localStorage.getItem("lat_ini") !== null){
        pos = new plugin.google.maps.LatLng(localStorage.getItem("lat_ini"), localStorage.getItem("lon_ini"));
    }

    if(pos !== null){
        map.setOptions({
            'mapType': plugin.google.maps.MapTypeId.ROADMAP,
            'controls': {
                'compass': true,
                'indoorPicker': true,
            },
            'camera': {
                'latLng': pos,
            }
        });
    }else{
        map.setOptions({
            'mapType': plugin.google.maps.MapTypeId.ROADMAP,
            'controls': {
                'compass': true,
                'indoorPicker': true,
            }
        });
    }
    map.on(plugin.google.maps.event.MAP_CLICK, cerrar_panel);
    map.on(plugin.google.maps.event.MAP_READY, mapaReady);
}
           
function mapaReady(){
    isMapaReady = true;
    map.setPadding(0,50,0,50);
    console.log("mapa listo, cargar posición");
    cargarUltimaPos();
    cargar_localizacion();
}

function background_plugin(){
    cordova.plugins.backgroundMode.enable();

    cordova.plugins.backgroundMode.on('activate', onBackground);

    cordova.plugins.backgroundMode.on('deactivate', onForeground);
}

function cerrar_panel(){
    if(left === true){
        myApp.closePanel();
        left = false;
    }
}

function calculateZoom(){
    var dist = localStorage.getItem('radioBus');
    var zoom = 18;
    
    if(dist <= 200){
        zoom = 14;
    }else if(dist <= 500){
        zoom = 13;
    }else if(dist <= 1000){
        zoom = 12;
    }else if(dist <= 5000){
        zoom = 11;
    }else if(dist <= 10000){
        zoom = 10;
    }else if(dist <= 25000){
        zoom = 9;
    }else if(dist <= 100000){
        zoom = 8;
    }else if(dist <= 250000){
        zoom = 7;
    }else if(dist <= 1000000){
        zoom = 6;
    }else if(dist <= 2000000){
        zoom = 5;
    }else if(dist <= 4000000){
        zoom = 4;
    }else if(dist <= 8000000){
        zoom = 3;
    }else if(dist <= 10000000){
        zoom = 2;
    }else if(dist <= 20000000){
        zoom = 1;
    }
        
    return zoom;
}

function cargar_datos(){
    $('#name').html((localStorage.getItem('fname') !== "" && localStorage.getItem('lname') !== "")?localStorage.getItem('fname') + " " + localStorage.getItem('lname'):"Nombre Apellido");
    $('#email').html((localStorage.getItem('email') !== "")?localStorage.getItem('email'):"email@email.cl");
    $('#phone').html((localStorage.getItem('phone') !== "")?localStorage.getItem('phone'):"+56912345678");
    if(localStorage.getItem('type_user') === "CLIENT" || localStorage.getItem('type_user') === ""){
        $("#change_type_user").hide();
    }
}

function cargar_localizacion(){    
    backgroundGeolocation.configure(callbackFn, failureFn, {
        desiredAccuracy: 100,
        distanceFilter: 5,
        stationaryRadius: 5,
        locationProvider: backgroundGeolocation.provider.ANDROID_ACTIVITY_PROVIDER,
        interval: 1000
    });
    backgroundGeolocation.start(); 
}

var callbackFn = function(location) {
    var lat = location.latitude;
    var lng = location.longitude;
    if(estado === FG){
        var pos = new plugin.google.maps.LatLng(lat, lng);
        if(cont_fn === 0){
            onMapInit(map, pos);
            resetMarker(map, pos);
        }else{
            if(verOff === false){
                resetMarker(map, pos);
            }
        }
        localStorage.setItem("lat",lat);
        localStorage.setItem("lon",lng);
        if(tipoUser === "OFFER"){
            $.ajax({
                dataType: 'json',
                type: 'GET',
                data: {
                    ilogin: localStorage.getItem("idLogin"),
                    lat: lat,
                    lon: lng
                },
                url: 'http://170.239.86.159/m/ws/ws_set_position.php',
                success: function (data, status, xhr) {
                    if(data.hres > '-1'){
                        if(data.hres === '2'){
                            cambioModo();
                        }
                    }
                },
                error: function (xhr, status) {
                    console.log("ERROR...");
                }
            });
        }
        cont_fn++;
    }else{
        localStorage.setItem("lat",lat);
        localStorage.setItem("lon",lng);
    }
    backgroundGeolocation.finish();
};

var failureFn = function(error) {
    myApp.alert(error.message,'BizAPP');
    console.log('BackgroundGeolocation error');
};


function onMapInit(map, pos){    
    map.moveCamera({
        'target': pos,
        'zoom': 14,
    });
}

function clearMap(){
    snip = null;
    if(device.platform !== "browser"){
        cargarUltimaPos();
    }
    $('#tab2').hide();
    $('#query').val('');
    myApp.closePanel(true);
}

function resetMarker(map, pos){
    var path = "";
    if(device.platform === "iOS"){
        path =  "img/ME.png";
    }else{
        path = "file:///android_asset/www/img/ME.png";
    }
    if(snip == null){
        map.clear();
        map.addMarker({
            'position': pos,
            'title': "Mi Posición Actual",
            'snippet': '',
            'styles' : {
                'text-align': 'center',
                'font-style': 'italic',
                'font-weight': 'bold',
                'color': 'red'
            },
            'icon': {
                'url': path
            }
        });  
    }
    
}

function cargarUltimaPos(){
    if(localStorage.getItem("lat") !== null && localStorage.getItem("lon") !== null){
        var path = "";
        if(device.platform === "iOS"){
            path =  "img/ME.png";
        }else{
            path = "file:///android_asset/www/img/ME.png";
        }
        var pos = new plugin.google.maps.LatLng(localStorage.getItem("lat"), localStorage.getItem("lon"));
        map.clear();
        map.moveCamera({
            'target': pos,
            'zoom': 14,
        });
        map.addMarker({
            'position': pos,
            'title': "Mi Posición Actual",
            'snippet': '',
            'styles' : {
                'text-align': 'center',
                'font-style': 'italic',
                'font-weight': 'bold',
                'color': 'red'
            },
            'icon': {
                'url': path
            }
        });
    }else{
        console.log("error al cargar la última pos");
    }
}

function onBackground(){
    estado = BG;
}

function cantestrellas(cant){
    var st = "";
    for(i = 0; i < cant; i++){
        st += "★";
    }
    return st;
}

function onForeground(){
    estado = FG;
    cargarUltimaPos();
}