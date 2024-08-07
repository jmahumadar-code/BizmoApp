<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Cregister extends CI_Controller
{
	
	function __construct()
	{
		# code...
		parent::__construct();
		// Load form helper
		$this->load->helper('form');
		// Load form validation library
		//$this->load->library('form_validation');
	}

	public function index(){
		$this->load->view("users/vRegister");
	}
	
	public function crear_cuenta(){
		$this->load->model("users/mregister");
		
		$email = $this->input->post('email');
		$password = $this->input->post('pass');
		
		$hres=$this->mregister->create_user($email, $password);
		if ($hres  && is_object($hres)){
			$row = $hres->row();
			if ( $row->hres == '-2' ) {
				$data['message_error'] = 'Email ingresado ya está registrado';
				$this->load->view('login/vLogin', $data);					
			}
			else{
			 $data = array(
				'user_name' => $user_name,
				'is_logged_in' => true ,
				'uid' => $row->hres
			  );
			  // $this->session->set_userdata($data);
			   redirect('cmain');
			}
		}
		else // incorrect username or password
		{
			$data['message_error'] = 'No pudo crear el login';
			$this->load->view('login/vLogin', $data);	
		}
	}
	
	public function complete_profile(){
		$this->load->model("users/mregister");
		
		$data['message'] = '';
		$hres=$this->mregister->get_family_s();
		
		if ($hres && is_object($hres)) {
			$data['familys']=$hres;
		}
		else
		   $data['family']=null;		
		$this->load->view("vHeader");
		$this->load->view('users/vProfile', $data);
		$this->load->view("vFooter");
	}
	
	public function get_master_services(){
		$this->load->model("users/mregister");	
		$ifamily = $this->input->post('family');
		$hres=$this->mregister->get_master_s($ifamily);
		
		if ($hres  && is_object($hres)){
			$arr = $hres->result();		
			echo json_encode($arr);
		}
	}
}
?>