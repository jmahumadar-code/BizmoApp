<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/**
* 
*/
class Cmain extends CI_Controller
{
	
	function __construct()
	{
		# code...
		parent::__construct();
	}

	public function index(){
		$data['message_error']='';
		$this->load->view("vHeader",$data);
		$this->load->view("vPpal",$data);
		$this->load->view("vFooter",$data);
	}
}