<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Mregister extends CI_Model{
	
	public function create_user($user, $pass){
	 try {
		$this->db->reconnect();
		$sql = "call sp_create_login('$user', '$pass');";
		$result = $this->db->query($sql); 
		$this->db->close();
	} catch (Exception $e) {
		echo $e->getMessage();
	}
	return  $result;   
    }
	
	public function get_login($user, $pass){
	 try {
		$this->db->reconnect();
		$sql = "call sp_get_login('$user', '$pass');";
		$result = $this->db->query($sql); 
		$this->db->close();
	} catch (Exception $e) {
		echo $e->getMessage();
	}
	return  $result;   
    }	
	
	public function get_family_s(){
	 try {
		$this->db->reconnect();
		$sql = "call sp_lis_fam_services();";
		$result = $this->db->query($sql); 
		$this->db->close();
	} catch (Exception $e) {
		echo $e->getMessage();
	}
	return  $result;   
    }
	
	public function get_master_s($ifamily){
	 try {
		$this->db->reconnect();
		$sql = "call sp_lis_sub_services($ifamily);";
		$result = $this->db->query($sql); 
		$this->db->close();
	} catch (Exception $e) {
		echo $e->getMessage();
	}
	return  $result;   
    }	
	
}

?>