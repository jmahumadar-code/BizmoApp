<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Expires" content="Tue, 01 Jan 1995 12:12:12 GMT">
    <meta http-equiv="Pragma" content="no-cache">
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <title>Login Acceso sistema Bizz Web</title>
   <!-- =============== VENDOR STYLES ===============-->
   <!-- FONT AWESOME-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/fontawesome/css/font-awesome.min.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/simple-line-icons/css/simple-line-icons.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/css/app.css" id="maincss">
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
               <p class="text-center pv">INICIE SESION</p>
               <form role="form" data-parsley-validate="" novalidate class="mb-lg" action="<?php base_url()?>clogin/get_login" method="POST">
                  <div class="form-group has-feedback">
                     <input id="exampleInputEmail1" type="email" placeholder="Enter email" autocomplete="off" required class="form-control" name="email">
                     <span class="fa fa-envelope form-control-feedback text-muted"></span>
                  </div>
                  <div class="form-group has-feedback">
                     <input id="exampleInputPassword1" type="password" placeholder="Password" required class="form-control" name="pass">
                     <span class="fa fa-lock form-control-feedback text-muted"></span>
                  </div>
                  <div class="clearfix">
                     <div class="checkbox c-checkbox pull-left mt0">
                        <label>
                           <input type="checkbox" value="" name="remember">
                           <span class="fa fa-check"></span>Recuerdeme</label>
                     </div>
                     <div class="pull-right"><a href="recover.html" class="text-muted">Forgot your password?</a>
                     </div>
                  </div>
                  <button type="submit" class="btn btn-block btn-primary mt-lg">Login</button>
               </form>
                <p class="pt-lg text-center">Necesita Registrarse?</p><a href="<?php echo base_url();?>users/cregister" class="btn btn-block btn-default">Registrese acá</a>
            </div>
			<?php  if (strlen($message_error)>5) { ?>
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span					                    <em class="fa fa-exclamation-circle"></em>
                    <span class="sr-only">Error:</span>
                    <?php echo $message_error; ?>
                 </div>
            <?php }  ?>
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
   <script src="<?php echo base_url()?>assets/modernizr/modernizr.custom.js"></script>
   <!-- JQUERY-->
   <script src="<?php echo base_url()?>assets/jquery/dist/jquery.js"></script>
   <!-- BOOTSTRAP-->
   <script src="<?php echo base_url()?>assets/bootstrap/dist/js/bootstrap.js"></script>
   <!-- STORAGE API-->
   <script src="<?php echo base_url()?>assets/jQuery-Storage-API/jquery.storageapi.js"></script>
   <!-- PARSLEY-->
   <script src="<?php echo base_url()?>assets/parsleyjs/dist/parsley.min.js"></script>
   <!-- =============== APP SCRIPTS ===============-->
   <script src="<?php echo base_url()?>assets/js/app.js"></script>
</body>

</html>