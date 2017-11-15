<?php 
 /*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
class M_androidlogin extends CI_Model{	
	function cek_login($table,$where){		
		return $this->db->get_where($table,$where);
	}	
}