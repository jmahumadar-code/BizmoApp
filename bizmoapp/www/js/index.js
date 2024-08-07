// Initialize your app
var myApp = new Framework7({
});

// Export selectors engine
var $$ = Dom7;

var cont_fn = 0;

const BG = 0; //Background
const FG = 1; //Foreground

var estado = FG;

var left = false;
var right = false;

var view_login = myApp.addView('.view-login', {
    dynamicNavbar: true,
    name: "login"
});

document.addEventListener('deviceready', function(){
    if(localStorage.getItem("idLogin") != null){
        window.location = "main.html";
    }
    $('#sub').bind('click',obtener_zona);
    
    $('#mail').keypress(function(keyPress){
        if(keyPress.keyCode == 13){
             $('#password').focus();
        }
    });
    
    $('#password').keypress(function(keyPress){
        if(keyPress.keyCode == 13){
            this.blur(); 
            log_in();
        }
    });
    document.addEventListener("backbutton", handleBackButton, true);
    llenar_datos();
    console.log("todo cargado");
}, false);

function obtener_zona(){
    myApp.showPreloader('Iniciando Sesión...');
    if(device.platform !== "browser"){
        navigator.geolocation.getCurrentPosition(
            function(position){
                console.log("test: "+position.coords.latitude+ " / "+ position.coords.longitude);
                localStorage.setItem("lat", position.coords.latitude);
                localStorage.setItem("lon", position.coords.longitude);
                ajax_obtener_zona(position.coords.latitude, position.coords.longitude);
            },
            function(error){
                myApp.hidePreloader();
                myApp.alert("Se ha producido un error al capturar su posición","BizApp");
            },
            {
                timeout: 10000,
                enableHighAccuracy: true
            }
        );
    }else{
        iniciar_sesion();
    }
}

function ajax_obtener_zona(latitude, longitude){
    console.log("obtener zona");
    $.ajax({
        dataType: 'json',
        type: 'GET',
        data: {
            key: "AIzaSyAFkI3pnhhhjLcD-B5fYcknG_cnJi-NQR4",
            latlng: latitude+","+longitude,
            result_type: "administrative_area_level_1"
        },
        url: 'https://maps.googleapis.com/maps/api/geocode/json',
        success: function (data, status, xhr) {
            console.log(JSON.stringify(data));
            localStorage.setItem("code_country",data.results[0].address_components[1].short_name);
            localStorage.setItem("code_long_country",data.results[0].formatted_address);
            console.log("from");
            $("#from").html(data.results[0].address_components[1].long_name);
            console.log("from2");
            iniciar_sesion();
        },
        error: function (xhr, status) {
            console.log("error");
            myApp.hidePreloader();
            console.log('Error AJAX location.');
        }
    });
}

function iniciar_sesion(){
    console.log("iniciando sesión");
    var mail = $('#mail').val();
    var pass = $('#password').val();
    if(mail.trim().length > 0 && pass.trim().length){
        if($('#che').is(':checked')){
            localStorage.setItem('recordar',$('#che').is(':checked'));
            localStorage.setItem('mail',mail);
            localStorage.setItem('pass',pass);
        }else{
            localStorage.setItem('recordar',false);
            localStorage.removeItem('mail');
            localStorage.removeItem('pass');
        }
        if(device.platform === "browser"){
            localStorage.setItem("code_country","CL");
        }
        $.ajax({
            dataType: 'json',
            type: 'GET',
            data: {
                sMail: mail,
                sPWD: pass,
                sPais: localStorage.getItem("code_country")
            },
            url: 'http://170.239.86.159/m/ws/ws_get_login.php',
            success: function (data, status, xhr) {
                console.log(data.hres);
                if(data.hres !== '-1'){
                    localStorage.setItem('UID',data.uid);
                    localStorage.setItem('fname',data.fname);
                    localStorage.setItem('lname',data.lname);
                    localStorage.setItem('picture',data.picture);
                    localStorage.setItem('fk_country',data.fk_country);
                    localStorage.setItem('phone',data.phone);
                    localStorage.setItem('email',data.email);
                    localStorage.setItem('payment_status',data.payment_status);
                    localStorage.setItem('type_card',data.type_card);
                    localStorage.setItem('type_user',data.type_user);
                    myApp.hidePreloader();
                    $(location).attr('href','main.html');
                }else{
                    myApp.hidePreloader();
                    myApp.alert('Los datos ingresados son incorrectos.', 'BizAPP');
                }
            },
            error: function (xhr, status) {
                myApp.hidePreloader();
                myApp.alert('Se ha producido un error en el inicio de sesión, intente más tarde.', 'BizAPP');
            }
        }); 
    }else{
        myApp.alert('Los campos de datos están vacios.', 'BizAPP');
    }
}

function llenar_datos(){
    if(localStorage.getItem('recordar') === 'true'){
        $('#mail').val(localStorage.getItem('mail'));
        $('#password').val(localStorage.getItem('pass'));
        $('#che').prop('checked', true);
        console.log("call obtener zona");
        obtener_zona();
    }else{
        if(localStorage.getItem('mail') != '' && localStorage.getItem('pass') != ''){
            $('#mail').val(localStorage.getItem('mail'));
            $('#password').val(localStorage.getItem('pass'));
        }
        console.log("check false");
        if(localStorage.getItem('refresh') === 'true'){
            console.log('refresh');
            localStorage.setItem('refresh', 'false');
            location.reload();
        }
        $('#che').prop('checked', false);
    }
}

function handleBackButton(){
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
                    navigator.app.exitApp();
                }
            },
        ]
    });
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

function onBackground(){
    estado = BG;
}

function onForeground(){
    estado = FG;
    cargarUltimaPos();
}