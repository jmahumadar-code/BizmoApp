<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Expires" content="Tue, 01 Jan 1995 12:12:12 GMT">
    <meta http-equiv="Pragma" content="no-cache">
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <title>Bizz Web Registro de Usuario</title>
   <!-- =============== VENDOR STYLES ===============-->
   <!-- FONT AWESOME-->
   <link rel="stylesheet" href="<?php echo base_url() ?>assets/fontawesome/css/font-awesome.min.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="<?php echo base_url() ?>assets/simple-line-icons/css/simple-line-icons.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<?php echo base_url() ?>assets/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<?php echo base_url() ?>assets/css/app.css" id="maincss">
</head>

<body>
   <div class="wrapper">
      <div class="block-center mt-xl wd-xl">
         <!-- START panel-->
         <div class="panel panel-dark panel-flat">
            <div class="panel-heading text-center">
               <a href="#">
                  <img src="<?php echo base_url()?>images/logo.png" alt="Image" class="block-center img-rounded">
               </a>
            </div>
            <div class="panel-body">
               <p class="text-center pv">Registrese para Obtener Beneficios</p>
               <form role="form" action="<?php echo base_url()?>users/cregister/crear_cuenta" method="POST" data-parsley-validate="" novalidate="" class="mb-lg">
                  <div class="form-group has-feedback">
                     <label for="signupInputEmail1" class="text-muted">Email</label>
                     <input id="signupInputEmail1" type="email" placeholder="Enter email" autocomplete="off" required class="form-control" name="email">
                     <span class="fa fa-envelope form-control-feedback text-muted"></span>
                  </div>
                  <div class="form-group has-feedback">
                     <label for="signupInputPassword1" class="text-muted">Password</label>
                     <input id="signupInputPassword1" type="password" placeholder="Password" autocomplete="off" required class="form-control" name="pass">
                     <span class="fa fa-lock form-control-feedback text-muted"></span>
                  </div>
                  <div class="form-group has-feedback">
                     <label for="signupInputRePassword1" class="text-muted">Repita Password</label>
                     <input id="signupInputRePassword1" type="password" placeholder="Retype Password" autocomplete="off" required data-parsley-equalto="#signupInputPassword1" class="form-control">
                     <span class="fa fa-lock form-control-feedback text-muted"></span>
                  </div>
                  <div class="clearfix">
                     <div class="checkbox c-checkbox pull-left mt0">
                        <label>
                           <input type="checkbox" value="" required name="agreed">
                           <span class="fa fa-check"></span>Estoy de acuerdo con los <a href="#">terminos</a>
                        </label>
                     </div>
                  </div>
                  <button type="submit" class="btn btn-block btn-primary mt-lg">Crear la Cuenta</button>
               </form>
               <p class="pt-lg text-center">¿Ya posee cuenta?</p><a href="<?php echo base_url()?>login/clogin" class="btn btn-block btn-default">Entrar</a>
            </div>
         </div>
         <!-- END panel-->
         <div class="p-lg text-center">
            <span>&copy;</span>
            <span><?php echo date('Y');?></span>
            <span>-</span>
            <span>BizzWeb</span>
            <br>
            <span>by <mark><a href="http://www.jamax.cl">Jamax</a></mark></span>
         </div>
      </div>
   </div>
   <!-- =============== VENDOR SCRIPTS ===============-->
   <!-- MODERNIZR-->
   <script src="<?php echo base_url() ?>assets/modernizr/modernizr.custom.js"></script>
   <!-- JQUERY-->
   <script src="<?php echo base_url() ?>assets/jquery/dist/jquery.js"></script>
   <!-- BOOTSTRAP-->
   <script src="<?php echo base_url() ?>assets/bootstrap/dist/js/bootstrap.js"></script>
   <!-- STORAGE API-->
   <script src="<?php echo base_url() ?>assets/jQuery-Storage-API/jquery.storageapi.js"></script>
   <!-- PARSLEY-->
   <script src="<?php echo base_url() ?>assets/parsleyjs/dist/parsley.min.js"></script>
   <!-- =============== APP SCRIPTS ===============-->
   <script src="js/app.js"></script>
</body>

</html>