<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/**
* 
*/
class Clogin extends CI_Controller
{
	
	function __construct()
	{
		# code...
		parent::__construct();
	}

	public function index(){
		$data['message_error']='';
		$this->load->view("login/vLogin",$data);
	}

	public function get_login(){
	$this->load->model("users/mregister");
	
	$email = $this->input->post('email');
	$password = $this->input->post('pass');
	
	$hres=$this->mregister->get_login($email, $password);
	if ($hres  && is_object($hres)){
		$row = $hres->row();
		if ( isset($row)){
		 $data = array(
			'user_name' => $user_name,
			'is_logged_in' => true ,
			'uid' => $row->hres,
			'email' =>$row->email,
			'type_user' =>$row->type_user,
			'fname' =>$row->fname,
			'lname' =>$row->lname,
		  );
		   $this->session->set_userdata($data);
		   redirect('cmain');
		}
		else{
			$data['message_error'] = 'Usuario o clave inválida';
			$this->load->view('login/vLogin', $data);	
			}
	}
	else // incorrect username or password
	{
		$data['message_error'] = 'Usuario o clave inválida';
		$this->load->view('login/vLogin', $data);	
	}
	}	
     public function logout(){
		session_destroy();
		$this->load->view("vHome");
	 }

}

?>