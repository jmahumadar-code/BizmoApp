<!DOCTYPE html>
<html lang="en">
<?php
header('Cache-Control: no-cache');
header('Pragma: no-cache');
?>
<head>
   <meta charset="utf-8">
    <meta http-equiv="Expires" content="Tue, 01 Jan 1995 12:12:12 GMT">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bizz Web</title>

    <!-- Bootstrap -->
    <link href="<?php echo base_url() ?>assets/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/font-awesome.min.css">
	<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/animate.css">
	<link href="<?php echo base_url() ?>assets/css/animate.min.css" rel="stylesheet"> 
	<link href="<?php echo base_url() ?>assets/css/style.css" rel="stylesheet" />	
  </head>
  <body>	
	<header id="header">
        <nav class="navbar navbar-default navbar-static-top" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                   <div class="navbar-brand">
						<a href="index.html"><h1>Bizmo Web</h1></a>
					</div>
                </div>				
                <div class="navbar-collapse collapse">							
					<div class="menu">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation"><a href="<?php echo base_url(); ?>chome" class="active">Home</a></li>
							<li role="presentation"><a href="about.php">About Us</a></li>
							<li role="presentation"><a href="<?php echo base_url();?>login/clogin">Entrar</a></li>
							<li role="presentation"><a href="contact.html">Contacto</a></li>						
						</ul>
					</div>
				</div>		
            </div><!--/.container-->
        </nav><!--/nav-->		
    </header><!--/header-->	
	
	<div class="slider">		
		<div id="about-slider">
			<div id="carousel-slider" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators visible-xs">
					<li data-target="#carousel-slider" data-slide-to="0" class="active"></li>
					<li data-target="#carousel-slider" data-slide-to="1"></li>
					<li data-target="#carousel-slider" data-slide-to="2"></li>
				</ol>

				<div class="carousel-inner">
					<div class="item active">						
						<img src="<?php echo base_url()?>images/slide.jpg" class="img-responsive" alt=""> 
						<div class="carousel-caption">
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.3s">								
								<h2><span>Clean & Modern Design Template</span></h2>
							</div>
							<div class="col-md-10 col-md-offset-1">
								<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.6s">								
									<p>Lorem ipsum dolor sit amet consectetur adipisicing</p>
								</div>
							</div>
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.9s">								
								<form class="form-inline">
									<div class="form-group">
										<button type="livedemo" name="Live Demo" class="btn btn-primary btn-lg" required="required">Live Demo</button>
									</div>
									<div class="form-group">
										<button type="getnow" name="Get Now" class="btn btn-primary btn-lg" required="required">Get Now</button>
									</div>
								</form>
							</div>
						</div>
				    </div>
			
				    <div class="item">
						<img src="<?php echo base_url()?>images/slide.jpg" class="img-responsive" alt=""> 
						<div class="carousel-caption">
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="1.0s">								
								<h2>Fully Responsive</h2>
							</div>
							<div class="col-md-10 col-md-offset-1">
								<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.6s">								
									<p>Lorem ipsum dolor sit amet consectetur adipisicing</p>
								</div>
							</div>
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="1.6s">								
								<form class="form-inline">
									<div class="form-group">
										<button type="livedemo" name="purchase" class="btn btn-primary btn-lg" required="required">Live Demo</button>
									</div>
									<div class="form-group">
										<button type="getnow" name="subscribe" class="btn btn-primary btn-lg" required="required">Get Now</button>
									</div>
								</form>
							</div>
						</div>
				    </div> 
				    <div class="item">
						<img src="<?php echo base_url()?>images/slide.jpg" class="img-responsive" alt=""> 
						<div class="carousel-caption">
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.3s">								
								<h2>Modern Design</h2>
							</div>
							<div class="col-md-10 col-md-offset-1">
								<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.6s">								
									<p>Lorem ipsum dolor sit amet consectetur adipisicing</p>
								</div>
							</div>
							<div class="wow fadeInUp" data-wow-offset="0" data-wow-delay="0.9s">								
								<form class="form-inline">
									<div class="form-group">
										<button type="livedemo" name="purchase" class="btn btn-primary btn-lg" required="required">Live Demo</button>
									</div>
									<div class="form-group">
										<button type="getnow" name="subscribe" class="btn btn-primary btn-lg" required="required">Get Now</button>
									</div>
								</form>
							</div>
						</div>
				    </div> 
				</div>
				
				<a class="left carousel-control hidden-xs" href="#carousel-slider" data-slide="prev">
					<i class="fa fa-angle-left"></i> 
				</a>
				
				<a class=" right carousel-control hidden-xs"href="#carousel-slider" data-slide="next">
					<i class="fa fa-angle-right"></i> 
				</a>
			</div> <!--/#carousel-slider-->
		</div><!--/#about-slider-->
	</div><!--/#slider-->
	
	<div class="about">
		<div class="container">
			<div class="text-center">
				<h2>Quaerat quod voluptate consequuntur </h2>
				<div class="col-md-10 col-md-offset-1">
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quaerat quod voluptate consequuntur ad quasi, 
					dolores obcaecati ex aliquid, dolor provident accusamus omnis dolorum ipsam. Voluptatem ipsum expedita, corporis facilis laborum asperiores nostrum! 
					Amet porro numquam ratione temporibus quia dolorem sint lorem voluptates quasi mollitia.</p>
				</div>
		
				<div class="col-md-6 col-sm-6 col-xs-6">
					<div class="btn-gamp"><a href="#">Learn More</a></div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<div class="gamp-btn"><a href="#">download</a></div>
				</div>
				
			</div>	
		</div>			
	</div>
	<hr>
	
	<div class="services">
		<div class="container">
			<div class="text-center">
				<h2>consectetur adipisicing elit</h2>
			</div>
			<div class="col-md-3 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="300ms">
				<i class="fa fa-heart-o"></i>
				<h3>Fully Responsive</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nostrum nam numquam voluptates cumque inventore, quibusdam corporis consequatur amet.</p>
			</div>
			<div class="col-md-3 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
				<i class="fa fa-cloud"></i>
				<h3>Retina Ready</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nostrum nam numquam voluptates cumque inventore, quibusdam corporis consequatur amet.</p>
			</div>
			<div class="col-md-3 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="900ms">
				<i class="fa fa-book"></i>	
				<h3>Fresh and Clean</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nostrum nam numquam voluptates cumque inventore, quibusdam corporis consequatur amet.</p>
			</div>
			<div class="col-md-3 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="1200ms">
				<i class="fa fa-gear"></i>	
				<h3>Easy to Customize</h3>
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nostrum nam numquam voluptates cumque inventore, quibusdam corporis consequatur amet.</p>
			</div>
		</div>			
	</div>
	
	<section class="action">
		<div class="container">
			<div class="left-text hidden-xs">
				<h4>Amet porro numquam ratione</h4>
				<p><em>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nisi ut explicabo magni sapiente.</em><br><br>Inventore at quia, vel in repellendus, cumque dolorem autem ad quidem mollitia porro blanditiis atque rerum debitis eveniet nostrum aliquam. Sint aperiam expedita sapiente amet officia quis perspiciatis adipisci, iure dolorem esse exercitationem!</p>
			</div>
			<div class="right-image hidden-xs"></div>
		</div>
	</section>
	
	<div class="gallery">
		<div class="text-center">
			<h2>Gallery</h2>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quaerat quod voluptate consequuntur ad quasi, dolores obcaecati ex aliquid, dolor provident </p>
		</div>
		<div class="container">		
			<div class="col-md-4">
				<figure class="effect-marley">
					<img src="<?php echo base_url()?>images/8.jpg" alt="" class="img-responsive"/>
					<figcaption>
						<h4>sweet marley</h4>
						<p>Marley tried to convince her but she was not interested.</p>				
					</figcaption>			
				</figure>
			</div>
			<div class="col-md-4">
				<figure class="effect-marley">
					<img src="<?php echo base_url()?>images/9.jpg" alt="" class="img-responsive"/>
					<figcaption>
						<h4>sweet marley</h4>
						<p>Marley tried to convince her but she was not interested.</p>				
					</figcaption>			
				</figure>
			</div>
			<div class="col-md-4">
				<figure class="effect-marley">
					<img src="<?php echo base_url()?>images/10.jpg" alt="" class="img-responsive"/>
					<figcaption>
						<h4>sweet marley</h4>
						<p>Marley tried to convince her but she was not interested.</p>				
					</figcaption>			
				</figure>
			</div>
		</div>
		
		<div class="container">
			<div class="col-md-4">
				<figure class="effect-marley">
					<img src="<?php echo base_url()?>images/11.jpg" alt="" class="img-responsive"/>
					<figcaption>
						<h4>sweet marley</h4>
						<p>Marley tried to convince her but she was not interested.</p>				
					</figcaption>			
				</figure>
			</div>
			<div class="col-md-4">
				<figure class="effect-marley">
					<img src="<?php echo base_url()?>images/12.jpg" alt="" class="img-responsive"/>
					<figcaption>
						<h4>sweet marley</h4>
						<p>Marley tried to convince her but she was not interested.</p>				
					</figcaption>			
				</figure>
			</div>
			<div class="col-md-4">
				<figure class="effect-marley">
					<img src="<?php echo base_url()?>images/13.jpg" alt="" class="img-responsive"/>
					<figcaption>
						<h4>sweet marley</h4>
						<p>Marley tried to convince her but she was not interested.</p>				
					</figcaption>			
				</figure>
			</div>
		</div>
	</div>
		
	<footer>
		<div class="container">
			<div class="col-md-4 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="300ms">
				<h4>About Us</h4>
				<p>Day is tellus ac cursus commodo, mauesris condime ntum nibh, ut fermentum mas justo sitters.</p>						
				<div class="contact-info">
					<ul>
						<li><i class="fa fa-home fa"></i>Suite 54 Elizebth Street, Victoria State Newyork, USA </li>
						<li><i class="fa fa-phone fa"></i> +38 000 129900</li>
						<li><i class="fa fa-envelope fa"></i> info@domain.net</li>
					</ul>
				</div>
			</div>
			
			<div class="col-md-4 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">				
				<div class="text-center">
					<h4>Photo Gallery</h4>
					<ul class="sidebar-gallery">
						<li><a href="#"><img src="<?php echo base_url()?>images/gallery1.png" alt="" /></a></li>
						<li><a href="#"><img src="<?php echo base_url()?>images/gallery2.png" alt="" /></a></li>
						<li><a href="#"><img src="<?php echo base_url()?>images/gallery3.png" alt="" /></a></li>
						<li><a href="#"><img src="<?php echo base_url()?>images/gallery4.png" alt="" /></a></li>
						<li><a href="#"><img src="<?php echo base_url()?>images/gallery5.png" alt="" /></a></li>
						<li><a href="#"><img src="<?php echo base_url()?>images/gallery6.png" alt="" /></a></li>					
					</ul>
				</div>
			</div>
			
			<div class="col-md-4 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="900ms">				
				<div class="">
					<h4>Newsletter Registration</h4>
					<p>Subscribe today to receive the latest Day news via email. You may unsubscribe from this service at any time</p>
					<div class="btn-gamp">		
						<input type="email" class="form-control" id="exampleInputEmail3" placeholder="Enter Email">
					</div>
					<div class="btn-gamp">
						<a type="submit" class="btn btn-default">Subscribe</a>
					</div>
				
			</div>  
        </div>
			
		</div>	
	</footer>
	
	<div class="sub-footer">
		<div class="container">
			<div class="social-icon">
				<div class="col-md-4">
					<ul class="social-network">
						<li><a href="#" class="fb tool-tip" title="Facebook"><i class="fa fa-facebook"></i></a></li>
						<li><a href="#" class="twitter tool-tip" title="Twitter"><i class="fa fa-twitter"></i></a></li>
						<li><a href="#" class="gplus tool-tip" title="Google Plus"><i class="fa fa-google-plus"></i></a></li>
						<li><a href="#" class="linkedin tool-tip" title="Linkedin"><i class="fa fa-linkedin"></i></a></li>
						<li><a href="#" class="ytube tool-tip" title="You Tube"><i class="fa fa-youtube-play"></i></a></li>
					</ul>	
				</div>
			</div>
			
			<div class="col-md-4 col-md-offset-4">
				<div class="copyright">
					&copy; Bizmo.
                    <div class="credits">
                        
                    </div>
				</div>
			</div>						
		</div>				
	</div>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<?php echo base_url() ?>assets/js/jquery.js"></script>		
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<?php echo base_url() ?>assets/js/bootstrap.min.js"></script>	
	<script src="<?php echo base_url() ?>assets/js/wow.min.js"></script>
	<script>wow = new WOW({}).init();</script>	
    
</body>
</html>