<section>
         <!-- Page content-->
         <div class="content-wrapper">
            <h3>Perfil de Usuario <small>&nbsp;Favor complete su perfil si es oferente</small></h3>
            <div class="panel panel-default">
               <div class="panel-body">
                  <form id="example-form" action="#">
                     <div>
                        <h4>Cuenta
                           <br>
                           <small>Define tipo usuario o prestador</small>
                        </h4>
                        <fieldset>
                        <div class="row">
               			<div class="col-sm-6">
                           <label for="userName">RUT *</label>
                           <input id="rut" name="rut" type="text" class="form-control required" size="15">
							<div class="checkbox c-checkbox">
                                 <label>
                                 <input type="checkbox" name="offer">
                                 <span class="fa fa-check"></span>Oferente de Servicios
                                 </label>
                           </div>                                  
                           <label for="password">Nombre *</label>
                           <input id="fname" name="fname" type="text" class="form-control required">
                           <label for="confirm">Apellidos *</label>
                           <input id="lname" name="lname" type="text" class="form-control required">
                        </div>
                        <div class="col-sm-6">
                           <label for="confirm">Foto: </label>
                           <br>
                           <img  src="" id="imagePreview" style="width:150px;height:150px;" alt=""/>   
                           <br/>
                           <br/>
							<span class="btn btn-success fileinput-button">
                                 <i class="fa fa-fw fa-plus"></i>
                        	     <span>Agregar</span>
                                 <br>
                                 <input type="file" name="files" multiple="true" id="files" accept="image/*" onchange="document.getElementById('imagePreview').src = window.URL.createObjectURL(this.files[0])">
                            </span>    

					   </div>
                       </div>                         
                           <p>(*) Obligatorios</p>
                        </fieldset>
                        <h4>Perfil
                        	<br>
                           <small>Habilidades del Oferente</small>
                        </h4>
                        <fieldset style="overflow:inherit;">
                            <small>Debe indicar al menos 1 especialidad desarrollada</small>
                            <div class="row">
                            <div class="col-sm-6" style="column-fill:balance; color:#9CF;">
                               Especialidad (*): &nbsp; <small>Referencia 1 de trabajos</small>
                               <select id="family1" name="family1" class="form-control m-b" onchange="javascript: GetSub1();" required>
                                  <?php if ($familys && is_object($familys)){
									      $i=0;
                                          foreach($familys->result_array() as $family) {
                                            $id=$family['idtfamily_services'];
                                            $sfam=$family['description'];
											if ($i==0)	
                                            	echo "<option value='". $id ."' selected>$sfam</option>\n";
											else
												echo "<option value='". $id ."'>$sfam</option>\n";
											$i++;
                                           }
                                        }
                                   ?>
                               </select>
                               Sub Especialidad (*): &nbsp;
                               <select id="mservices1" name="mservices2" class="form-control m-b" required>
                               </select>	
                               Descripción (*):&nbsp;
                               <input id="desc1" name="desc" type="text" class="form-control required text" placeholder="Ingrese una descripción del trabajo realizado">
                               Fecha del Trabajo (*): &nbsp;
                               <input id="fec1" name="fec1" type="date" class="form-control required date">
                           </div>
                            <div class="col-sm-6" style="column-fill:balance; color:#666;">  
     							Especialidad (2):
                                <select id="family1" name="family1" class="form-control m-b"  onchange="javascript: GetSub2();">
                                  <?php if ($familys && is_object($familys)){
									  	  $i=0;
                                          foreach($familys->result_array() as $family) {
                                            $id=$family['idtfamily_services'];
                                            $sfam=$family['description'];	
  											if ($i==0)	
                                            	echo "<option value='". $id ."' selected>$sfam</option>\n";
											else
												echo "<option value='". $id ."'>$sfam</option>\n";
											$i++;
                                           }
                                        }
                                   ?>
                               </select>
                               Sub Especialidad (2)
                               <select id="mservices1" name="mservices2" class="form-control m-b">
                               </select>	
							  Descripción (*):&nbsp;
                               <input id="desc2" name="desc2" type="text" class="form-control text" placeholder="Ingrese una descripción del trabajo realizado">
                               Fecha del Trabajo (*): &nbsp;
                               <input id="fec2" name="fec2" type="date" class="form-control date">                            </div>
                             
                          </div>
                          <p>(*) Mandatory</p>
                        </fieldset>
                        <h4>Referencias
                           <br>
                           <small>Avales del perfil descrito</small>
                        </h4>
                        <fieldset>
                           <p class="lead text-center">Almost there!</p>
                        </fieldset>  
                        <h4>Finalizar
                           <br>
                           <small>Grabar y Continuar</small>
                        </h4>
                        <fieldset>
                           <p class="lead">One last check</p>
                           <div class="checkbox c-checkbox">
                              <label>
                                 <input type="checkbox" required="required" name="terms">
                                 <span class="fa fa-check"></span>Acepto los Terminos Y Condiciones.
                                 </label>
                           </div>
                        </fieldset>
                     </div>
                  </form>
               </div>
            </div>
</section>
<script>
function GetSub1(){
	var searchThis = $("#family1").val();
	$("#mservices1").empty();
    $.ajax({
      type: "POST",
      url: "<?php echo base_url();?>users/cregister/get_master_services" ,
      dataType: "json",
	  data: {family : searchThis},
      success:function(data){
            $.each(data, function(k,v){
            	$("#mservices1").append("<option value=\""+v.idtmaster_services+"\">"+v.description+"</option>");
             });
	      }
    });	
}
function GetSub2(){
	var searchThis = $("#family2").val();
	$("#mservices2").empty();
    $.ajax({
      type: "POST",
      url: "<?php echo base_url();?>users/cregister/get_master_services" ,
      dataType: "json",
	  data: {family : searchThis},
      success:function(data){
            $.each(data, function(k,v){
            	$("#mservices2").append("<option value=\""+v.idtmaster_services+"\">"+v.description+"</option>");
             });
	      }
    });	
}

$( document ).ready(function() {
	GetSub1();
    GetSub2();
	alert($("#family2").val());
});
</script>    