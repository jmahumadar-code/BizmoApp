<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">   
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <title>Bizmo Web | Homepage</title>
   <!-- =============== VENDOR STYLES ===============-->
   <!-- FONT AWESOME-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/fontawesome/css/font-awesome.min.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/simple-line-icons/css/simple-line-icons.css">
   <!-- ANIMATE.CSS-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/animate.css/animate.min.css">
   <!-- WHIRL (spinners)-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/whirl/dist/whirl.css">
   <!-- =============== PAGE VENDOR STYLES ===============-->
   <!-- WEATHER ICONS-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/weather-icons/css/weather-icons.min.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/css/app.css" id="maincss">
      <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/blueimp-file-upload/css/jquery.fileupload.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="<?php echo base_url()?>assets/simple-line-icons/css/simple-line-icons.css">
   <link rel="stylesheet" href="<?php echo base_url()?>assets/css/app.css" id="maincss">   
</head>

<body>
   <div class="wrapper">
      <!-- top navbar-->
      <header class="topnavbar-wrapper">
         <!-- START Top Navbar-->
         <nav role="navigation" class="navbar topnavbar">
            <!-- START navbar header-->
            <div class="navbar-header">
               <a href="#/" class="navbar-brand">
                  <div class="brand-logo">
                     <img src="<?php echo base_url()?>images/logo.png" alt="Bizmo" class="img-responsive">
                  </div>
                  <div class="brand-logo-collapsed">
                     <img src="<?php echo base_url()?>images/logo-single.png" alt="Bizmo" class="img-responsive">
                  </div>
               </a>
            </div>
            <!-- END navbar header-->
            <!-- START Nav wrapper-->
            <div class="nav-wrapper">
               <!-- START Left navbar-->
               <ul class="nav navbar-nav">
                  <li>
                     <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
                     <a href="#" data-trigger-resize="" data-toggle-state="aside-collapsed" class="hidden-xs">
                        <em class="fa fa-navicon"></em>
                     </a>
                     <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->
                     <a href="#" data-toggle-state="aside-toggled" data-no-persist="true" class="visible-xs sidebar-toggle">
                        <em class="fa fa-navicon"></em>
                     </a>
                  </li>
                  <!-- START User avatar toggle-->
                  <li>
                     <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
                     <a id="user-block-toggle" href="#user-block" data-toggle="collapse">
                        <em class="icon-user"></em>
                     </a>
                  </li>
                  <!-- END User avatar toggle-->
                  <!-- START lock screen-->
                  <li>
                     <a href="<?php echo base_url()?>login/clogin/logout" title="Salir">
                        <em class="icon-logout"></em>
                     </a>
                  </li>
                  <!-- END lock screen-->
               </ul>
               <!-- END Left navbar-->
               <!-- START Right Navbar-->
               <ul class="nav navbar-nav navbar-right">
                  <!-- Search icon-->
                  <li>
                     <a href="#" data-search-open="">
                        <em class="icon-magnifier"></em>
                     </a>
                  </li>
                  <!-- Fullscreen (only desktops)-->
                  <li class="visible-lg">
                     <a href="#" data-toggle-fullscreen="">
                        <em class="fa fa-expand"></em>
                     </a>
                  </li>
                  <!-- START Alert menu-->
                  <li class="dropdown dropdown-list">
                     <a href="#" data-toggle="dropdown">
                        <em class="icon-bell"></em>
                        <div class="label label-danger">11</div>
                     </a>
                     <!-- START Dropdown menu-->
                     <ul class="dropdown-menu animated flipInX">
                        <li>
                           <!-- START list group-->
                           <div class="list-group">
                              <!-- list item-->
                              <a href="#" class="list-group-item">
                                 <div class="media-box">
                                    <div class="pull-left">
                                       <em class="fa fa-twitter fa-2x text-info"></em>
                                    </div>
                                    <div class="media-box-body clearfix">
                                       <p class="m0">New followers</p>
                                       <p class="m0 text-muted">
                                          <small>1 new follower</small>
                                       </p>
                                    </div>
                                 </div>
                              </a>
                              <!-- list item-->
                              <a href="#" class="list-group-item">
                                 <div class="media-box">
                                    <div class="pull-left">
                                       <em class="fa fa-envelope fa-2x text-warning"></em>
                                    </div>
                                    <div class="media-box-body clearfix">
                                       <p class="m0">New e-mails</p>
                                       <p class="m0 text-muted">
                                          <small>You have 10 new emails</small>
                                       </p>
                                    </div>
                                 </div>
                              </a>
                              <!-- list item-->
                              <a href="#" class="list-group-item">
                                 <div class="media-box">
                                    <div class="pull-left">
                                       <em class="fa fa-tasks fa-2x text-success"></em>
                                    </div>
                                    <div class="media-box-body clearfix">
                                       <p class="m0">Pending Tasks</p>
                                       <p class="m0 text-muted">
                                          <small>11 pending task</small>
                                       </p>
                                    </div>
                                 </div>
                              </a>
                              <!-- last list item-->
                              <a href="#" class="list-group-item">
                                 <small>More notifications</small>
                                 <span class="label label-danger pull-right">14</span>
                              </a>
                           </div>
                           <!-- END list group-->
                        </li>
                     </ul>
                     <!-- END Dropdown menu-->
                  </li>
                  <!-- END Alert menu-->
                  <!-- START Offsidebar button-->
                  <li>
                     <a href="#" data-toggle-state="offsidebar-open" data-no-persist="true">
                        <em class="icon-notebook"></em>
                     </a>
                  </li>
                  <!-- END Offsidebar menu-->
               </ul>
               <!-- END Right Navbar-->
            </div>
            <!-- END Nav wrapper-->
            <!-- START Search form-->
            <form role="search" action="search.html" class="navbar-form">
               <div class="form-group has-feedback">
                  <input type="text" placeholder="Type and hit enter ..." class="form-control">
                  <div data-search-dismiss="" class="fa fa-times form-control-feedback"></div>
               </div>
               <button type="submit" class="hidden btn btn-default">Submit</button>
            </form>
            <!-- END Search form-->
         </nav>
         <!-- END Top Navbar-->
      </header>
      <!-- sidebar-->
      <aside class="aside">
         <!-- START Sidebar (left)-->
         <div class="aside-inner">
            <nav data-sidebar-anyclick-close="" class="sidebar">
               <!-- START sidebar nav-->
               <ul class="nav">
                  <!-- START user info-->
                  <li class="has-user-block">
                     <div id="user-block" class="collapse">
                        <div class="item user-block">
                           <!-- User picture-->
                           <div class="user-block-picture">
                              <div class="user-block-status">
                                 <img src="<?php echo base_url();?>images/user/02.jpg" alt="Avatar" width="60" height="60" class="img-thumbnail img-circle">
                                 <div class="circle circle-success circle-lg"></div>
                              </div>
                           </div>
                           <!-- Name and Job-->
                           <div class="user-block-info">
                              <span class="user-block-name">Hola, <?php echo $this->session->userdata('email');?></span>
                              <span class="user-block-role"><?php echo $this->session->userdata('type_user');?></span>
                           </div>
                        </div>
                     </div>
                  </li>
                  <!-- END user info-->
                  <!-- Iterates over all sidebar items-->
                  <li class="nav-heading ">
                     <span data-localize="sidebar.heading.HEADER">Menú de Opciones</span>
                  </li>
                  <li class=" ">
                     <a href="<?php base_url();?>users/cregister/complete_profile" title="Perfil de Usuario">
                     <?php if ($this->session->userdata('fname') === null ){ ?>
                        <div class="pull-right label label-danger">*</div>
                     <?php } else { ?>
						 <div class="pull-right label label-success">*</div>
				      <?php } ?>
                        <em class="icon-user"></em>
                        <span data-localize="sidebar.nav.WIDGETS">Cuenta</span>
                     </a>
                  </li>
                  <li class=" ">
                     <ul id="layout" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Perfil</li>
                        <li class=" ">
                           <a href="<?php base_url();?>users/cregister/complete_profile" title="Completar Perfil">
                              <span>Perfil</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#dashboard" title="Dashboard" data-toggle="collapse">
                        <div class="pull-right label label-info">3</div>
                        <em class="icon-speedometer"></em>
                        <span data-localize="sidebar.nav.DASHBOARD">Dashboard</span>
                     </a>
                     <ul id="dashboard" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Dashboard</li>
                        <li class=" active">
                           <a href="dashboard.html" title="Dashboard v1">
                              <span>Dashboard v1</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="dashboard_v2.html" title="Dashboard v2">
                              <span>Dashboard v2</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="dashboard_v3.html" title="Dashboard v3">
                              <span>Dashboard v3</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class="nav-heading ">
                     <span data-localize="sidebar.heading.COMPONENTS">Components</span>
                  </li>
                  <li class=" ">
                     <a href="#elements" title="Elements" data-toggle="collapse">
                        <em class="icon-chemistry"></em>
                        <span data-localize="sidebar.nav.element.ELEMENTS">Elements</span>
                     </a>
                     <ul id="elements" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Elements</li>
                        <li class=" ">
                           <a href="buttons.html" title="Buttons">
                              <span data-localize="sidebar.nav.element.BUTTON">Buttons</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="notifications.html" title="Notifications">
                              <span data-localize="sidebar.nav.element.NOTIFICATION">Notifications</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="sweetalert.html" title="Sweet Alert">
                              <span>Sweet Alert</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="tour.html" title="Tour">
                              <span>Tour</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="carousel.html" title="Carousel">
                              <span data-localize="sidebar.nav.element.INTERACTION">Carousel</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="spinners.html" title="Spinners">
                              <span data-localize="sidebar.nav.element.SPINNER">Spinners</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="animations.html" title="Animations">
                              <span data-localize="sidebar.nav.element.ANIMATION">Animations</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="dropdown-animations.html" title="Dropdown">
                              <span data-localize="sidebar.nav.element.DROPDOWN">Dropdown</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="nestable.html" title="Nestable">
                              <span>Nestable</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="sortable.html" title="Sortable">
                              <span>Sortable</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="panels.html" title="Panels">
                              <span data-localize="sidebar.nav.element.PANEL">Panels</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="portlets.html" title="Portlets">
                              <span data-localize="sidebar.nav.element.PORTLET">Portlets</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="grid.html" title="Grid">
                              <span data-localize="sidebar.nav.element.GRID">Grid</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="grid-masonry.html" title="Grid Masonry">
                              <span data-localize="sidebar.nav.element.GRID_MASONRY">Grid Masonry</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="typo.html" title="Typography">
                              <span data-localize="sidebar.nav.element.TYPO">Typography</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="icons-font.html" title="Font Icons">
                              <div class="pull-right label label-success">+400</div>
                              <span data-localize="sidebar.nav.element.FONT_ICON">Font Icons</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="icons-weather.html" title="Weather Icons">
                              <div class="pull-right label label-success">+100</div>
                              <span data-localize="sidebar.nav.element.WEATHER_ICON">Weather Icons</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="colors.html" title="Colors">
                              <span data-localize="sidebar.nav.element.COLOR">Colors</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#forms" title="Forms" data-toggle="collapse">
                        <em class="icon-note"></em>
                        <span data-localize="sidebar.nav.form.FORM">Forms</span>
                     </a>
                     <ul id="forms" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Forms</li>
                        <li class=" ">
                           <a href="form-standard.html" title="Standard">
                              <span data-localize="sidebar.nav.form.STANDARD">Standard</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="form-extended.html" title="Extended">
                              <span data-localize="sidebar.nav.form.EXTENDED">Extended</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="form-validation.html" title="Validation">
                              <span data-localize="sidebar.nav.form.VALIDATION">Validation</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="form-wizard.html" title="Wizard">
                              <span>Wizard</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="form-upload.html" title="Upload">
                              <span>Upload</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="form-xeditable.html" title="xEditable">
                              <span>xEditable</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="form-imagecrop.html" title="Cropper">
                              <span>Cropper</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#charts" title="Charts" data-toggle="collapse">
                        <em class="icon-graph"></em>
                        <span data-localize="sidebar.nav.chart.CHART">Charts</span>
                     </a>
                     <ul id="charts" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Charts</li>
                        <li class=" ">
                           <a href="chart-flot.html" title="Flot">
                              <span data-localize="sidebar.nav.chart.FLOT">Flot</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="chart-radial.html" title="Radial">
                              <span data-localize="sidebar.nav.chart.RADIAL">Radial</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="chart-js.html" title="Chart JS">
                              <span>Chart JS</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="chart-rickshaw.html" title="Rickshaw">
                              <span>Rickshaw</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="chart-morris.html" title="MorrisJS">
                              <span>MorrisJS</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="chart-chartist.html" title="Chartist">
                              <span>Chartist</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#tables" title="Tables" data-toggle="collapse">
                        <em class="icon-grid"></em>
                        <span data-localize="sidebar.nav.table.TABLE">Tables</span>
                     </a>
                     <ul id="tables" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Tables</li>
                        <li class=" ">
                           <a href="table-standard.html" title="Standard">
                              <span data-localize="sidebar.nav.table.STANDARD">Standard</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="table-extended.html" title="Extended">
                              <span data-localize="sidebar.nav.table.EXTENDED">Extended</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="table-datatable.html" title="DataTables">
                              <span data-localize="sidebar.nav.table.DATATABLE">DataTables</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="table-jqgrid.html" title="jqGrid">
                              <span>jqGrid</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#maps" title="Maps" data-toggle="collapse">
                        <em class="icon-map"></em>
                        <span data-localize="sidebar.nav.map.MAP">Maps</span>
                     </a>
                     <ul id="maps" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Maps</li>
                        <li class=" ">
                           <a href="maps-google.html" title="Google Maps">
                              <span data-localize="sidebar.nav.map.GOOGLE">Google Maps</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="maps-vector.html" title="Vector Maps">
                              <span data-localize="sidebar.nav.map.VECTOR">Vector Maps</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class="nav-heading ">
                     <span data-localize="sidebar.heading.MORE">More</span>
                  </li>
                  <li class=" ">
                     <a href="#pages" title="Pages" data-toggle="collapse">
                        <em class="icon-doc"></em>
                        <span data-localize="sidebar.nav.pages.PAGES">Pages</span>
                     </a>
                     <ul id="pages" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Pages</li>
                        <li class=" ">
                           <a href="login.html" title="Login">
                              <span data-localize="sidebar.nav.pages.LOGIN">Login</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="register.html" title="Sign up">
                              <span data-localize="sidebar.nav.pages.REGISTER">Sign up</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="recover.html" title="Recover Password">
                              <span data-localize="sidebar.nav.pages.RECOVER">Recover Password</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="lock.html" title="Lock">
                              <span data-localize="sidebar.nav.pages.LOCK">Lock</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="template.html" title="Starter Template">
                              <span data-localize="sidebar.nav.pages.STARTER">Starter Template</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="404.html" title="404">
                              <span>404</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#extras" title="Extras" data-toggle="collapse">
                        <em class="icon-cup"></em>
                        <span data-localize="sidebar.nav.extra.EXTRA">Extras</span>
                     </a>
                     <ul id="extras" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Extras</li>
                        <li class=" ">
                           <a href="#blog" title="Blog" data-toggle="collapse">
                              <em class="fa fa-angle-right"></em>
                              <span>Blog</span>
                           </a>
                           <ul id="blog" class="nav sidebar-subnav collapse">
                              <li class="sidebar-subnav-header">Blog</li>
                              <li class=" ">
                                 <a href="blog.html" title="List">
                                    <span>List</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="blog-post.html" title="Post">
                                    <span>Post</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="blog-articles.html" title="Articles">

                                    <span>Articles</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="blog-article-view.html" title="Article View">
                                    <span>Article View</span>
                                 </a>
                              </li>
                           </ul>
                        </li>
                        <li class=" ">
                           <a href="#ecommerce" title="eCommerce" data-toggle="collapse">
                              <em class="fa fa-angle-right"></em>
                              <span>eCommerce</span>
                           </a>
                           <ul id="ecommerce" class="nav sidebar-subnav collapse">
                              <li class="sidebar-subnav-header">eCommerce</li>
                              <li class=" ">
                                 <a href="ecommerce-orders.html" title="Orders">
                                    <div class="pull-right label label-info">10</div>
                                    <span>Orders</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="ecommerce-order-view.html" title="Order View">
                                    <span>Order View</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="ecommerce-products.html" title="Products">
                                    <span>Products</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="ecommerce-product-view.html" title="Product View">
                                    <span>Product View</span>
                                 </a>
                              </li>
                           </ul>
                        </li>
                        <li class=" ">
                           <a href="#forum" title="Forum" data-toggle="collapse">
                              <em class="fa fa-angle-right"></em>
                              <span>Forum</span>
                           </a>
                           <ul id="forum" class="nav sidebar-subnav collapse">
                              <li class="sidebar-subnav-header">Forum</li>
                              <li class=" ">
                                 <a href="forum-categories.html" title="Categories">
                                    <span>Categories</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="forum-topics.html" title="Topics">
                                    <span>Topics</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="forum-discussion.html" title="Discussion">
                                    <span>Discussion</span>
                                 </a>
                              </li>
                           </ul>
                        </li>
                        <li class=" ">
                           <a href="mailbox.html" title="Mailbox">
                              <span data-localize="sidebar.nav.extra.MAILBOX">Mailbox</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="timeline.html" title="Timeline">
                              <span data-localize="sidebar.nav.extra.TIMELINE">Timeline</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="calendar.html" title="Calendar">
                              <span data-localize="sidebar.nav.extra.CALENDAR">Calendar</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="invoice.html" title="Invoice">
                              <span data-localize="sidebar.nav.extra.INVOICE">Invoice</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="search.html" title="Search">
                              <span data-localize="sidebar.nav.extra.SEARCH">Search</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="todo.html" title="Todo List">
                              <span data-localize="sidebar.nav.extra.TODO">Todo List</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="profile.html" title="Profile">
                              <span data-localize="sidebar.nav.extra.PROFILE">Profile</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#multilevel" title="Multilevel" data-toggle="collapse">
                        <em class="fa fa-folder-open-o"></em>
                        <span>Multilevel</span>
                     </a>
                     <ul id="multilevel" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Multilevel</li>
                        <li class=" ">
                           <a href="#level1" title="Level 1" data-toggle="collapse">
                              <span>Level 1</span>
                           </a>
                           <ul id="level1" class="nav sidebar-subnav collapse">
                              <li class="sidebar-subnav-header">Level 1</li>
                              <li class=" ">
                                 <a href="multilevel-1.html" title="Level1 Item">
                                    <span>Level1 Item</span>
                                 </a>
                              </li>
                              <li class=" ">
                                 <a href="#level2" title="Level 2" data-toggle="collapse">
                                    <span>Level 2</span>
                                 </a>
                                 <ul id="level2" class="nav sidebar-subnav collapse">
                                    <li class="sidebar-subnav-header">Level 2</li>
                                    <li class=" ">
                                       <a href="#level3" title="Level 3" data-toggle="collapse">
                                          <span>Level 3</span>
                                       </a>
                                       <ul id="level3" class="nav sidebar-subnav collapse">
                                          <li class="sidebar-subnav-header">Level 3</li>
                                          <li class=" ">
                                             <a href="multilevel-3.html" title="Level3 Item">
                                                <span>Level3 Item</span>
                                             </a>
                                          </li>
                                       </ul>
                                    </li>
                                 </ul>
                              </li>
                           </ul>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="documentation.html" title="Documentation">
                        <em class="icon-graduation"></em>
                        <span data-localize="sidebar.nav.DOCUMENTATION">Documentation</span>
                     </a>
                  </li>
               </ul>
               <!-- END sidebar nav-->
            </nav>
         </div>
         <!-- END Sidebar (left)-->
      </aside>
      <!-- offsidebar-->
      <aside class="offsidebar hide">
         <!-- START Off Sidebar (right)-->
         <nav>
            <div role="tabpanel">
               <!-- Nav tabs-->
               <ul role="tablist" class="nav nav-tabs nav-justified">
                  <li role="presentation" class="active">
                     <a href="#app-settings" aria-controls="app-settings" role="tab" data-toggle="tab">
                        <em class="icon-equalizer fa-lg"></em>
                     </a>
                  </li>
                  <li role="presentation">
                     <a href="#app-chat" aria-controls="app-chat" role="tab" data-toggle="tab">
                        <em class="icon-user fa-lg"></em>
                     </a>
                  </li>
               </ul>
               <!-- Tab panes-->
               <div class="tab-content">
                  <div id="app-settings" role="tabpanel" class="tab-pane fade in active">
                     <h3 class="text-center text-thin">Settings</h3>
                     <div class="p">
                        <h4 class="text-muted text-thin">Themes</h4>
                        <div class="table-grid mb">
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-a.css">
                                    <input type="radio" name="setting-theme" checked="checked">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-info"></span>
                                       <span class="color bg-info-light"></span>
                                    </span>
                                    <span class="color bg-white"></span>
                                 </label>
                              </div>
                           </div>
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-b.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-green"></span>
                                       <span class="color bg-green-light"></span>
                                    </span>
                                    <span class="color bg-white"></span>
                                 </label>
                              </div>
                           </div>
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-c.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-purple"></span>
                                       <span class="color bg-purple-light"></span>
                                    </span>
                                    <span class="color bg-white"></span>
                                 </label>
                              </div>
                           </div>
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-d.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-danger"></span>
                                       <span class="color bg-danger-light"></span>
                                    </span>
                                    <span class="color bg-white"></span>
                                 </label>
                              </div>
                           </div>
                        </div>
                        <div class="table-grid mb">
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-e.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-info-dark"></span>
                                       <span class="color bg-info"></span>
                                    </span>
                                    <span class="color bg-gray-dark"></span>
                                 </label>
                              </div>
                           </div>
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-f.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-green-dark"></span>
                                       <span class="color bg-green"></span>
                                    </span>
                                    <span class="color bg-gray-dark"></span>
                                 </label>
                              </div>
                           </div>
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-g.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-purple-dark"></span>
                                       <span class="color bg-purple"></span>
                                    </span>
                                    <span class="color bg-gray-dark"></span>
                                 </label>
                              </div>
                           </div>
                           <div class="col mb">
                              <div class="setting-color">
                                 <label data-load-css="css/theme-h.css">
                                    <input type="radio" name="setting-theme">
                                    <span class="icon-check"></span>
                                    <span class="split">
                                       <span class="color bg-danger-dark"></span>
                                       <span class="color bg-danger"></span>
                                    </span>
                                    <span class="color bg-gray-dark"></span>
                                 </label>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="p">
                        <h4 class="text-muted text-thin">Layout</h4>
                        <div class="clearfix">
                           <p class="pull-left">Fixed</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-fixed" type="checkbox" data-toggle-state="layout-fixed">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">Boxed</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-boxed" type="checkbox" data-toggle-state="layout-boxed">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">RTL</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-rtl" type="checkbox">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                     </div>
                     <div class="p">
                        <h4 class="text-muted text-thin">Aside</h4>
                        <div class="clearfix">
                           <p class="pull-left">Collapsed</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-collapsed" type="checkbox" data-toggle-state="aside-collapsed">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">Float</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-float" type="checkbox" data-toggle-state="aside-float">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">Hover</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-hover" type="checkbox" data-toggle-state="aside-hover">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">Show Scrollbar</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-hover" type="checkbox" data-toggle-state="show-scrollbar" data-target=".sidebar">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                     </div>
                  </div>
                  <div id="app-chat" role="tabpanel" class="tab-pane fade">
                     <h3 class="text-center text-thin">Connections</h3>
                     <ul class="nav">
                        <!-- START list title-->
                        <li class="p">
                           <small class="text-muted">ONLINE</small>
                        </li>
                        <!-- END list title-->
                        <li>
                           <!-- START User status-->
                           <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-success circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="<?php echo base_url();?>images/user/05.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                              <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Juan Sims</strong>
                                    <br>
                                    <small class="text-muted">Designeer</small>
                                 </span>
                              </span>
                           </a>
                           <!-- END User status-->
                           <!-- START User status-->
                           <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-success circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="<?php echo base_url();?>images/user/06.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                              <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Maureen Jenkins</strong>
                                    <br>
                                    <small class="text-muted">Designeer</small>
                                 </span>
                              </span>
                           </a>
                           <!-- END User status-->
                           <!-- START User status-->
                           <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-danger circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="<?php echo base_url();?>images/user/07.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                              <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Billie Dunn</strong>
                                    <br>
                                    <small class="text-muted">Designeer</small>
                                 </span>
                              </span>
                           </a>
                           <!-- END User status-->
                           <!-- START User status-->
                           <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-warning circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="<?php echo base_url();?>images/user/08.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                              <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Tomothy Roberts</strong>
                                    <br>
                                    <small class="text-muted">Designer</small>
                                 </span>
                              </span>
                           </a>
                           <!-- END User status-->
                        </li>
                        <!-- START list title-->
                        <li class="p">
                           <small class="text-muted">OFFLINE</small>
                        </li>
                        <!-- END list title-->
                        <li>
                           <!-- START User status-->
                           <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="<?php echo base_url();?>images/user/09.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                              <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Lawrence Robinson</strong>
                                    <br>
                                    <small class="text-muted">Developer</small>
                                 </span>
                              </span>
                           </a>
                           <!-- END User status-->
                           <!-- START User status-->
                           <a href="#" class="media-box p mt0">
                              <span class="pull-right">
                                 <span class="circle circle-lg"></span>
                              </span>
                              <span class="pull-left">
                                 <!-- Contact avatar-->
                                 <img src="<?php echo base_url();?>images/user/10.jpg" alt="Image" class="media-box-object img-circle thumb48">
                              </span>
                              <!-- Contact info-->
                              <span class="media-box-body">
                                 <span class="media-box-heading">
                                    <strong>Tyrone Owens</strong>
                                    <br>
                                    <small class="text-muted">Designer</small>
                                 </span>
                              </span>
                           </a>
                           <!-- END User status-->
                        </li>
                        <li>
                           <div class="p-lg text-center">
                              <!-- Optional link to list more users-->
                              <a href="#" title="See more contacts" class="btn btn-purple btn-sm">
                                 <strong>Load more..</strong>
                              </a>
                           </div>
                        </li>
                     </ul>
                     <!-- Extra items-->
                     <div class="p">
                        <p>
                           <small class="text-muted">Tasks completion</small>
                        </p>
                        <div class="progress progress-xs m0">
                           <div role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" class="progress-bar progress-bar-success progress-80">
                              <span class="sr-only">80% Complete</span>
                           </div>
                        </div>
                     </div>
                     <div class="p">
                        <p>
                           <small class="text-muted">Upload quota</small>
                        </p>
                        <div class="progress progress-xs m0">
                           <div role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" class="progress-bar progress-bar-warning progress-40">
                              <span class="sr-only">40% Complete</span>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </nav>
         <!-- END Off Sidebar (right)-->
      </aside>
      <!-- Main section-->
      <section>
      
