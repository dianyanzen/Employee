<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
require APPPATH . '/libraries/REST_Controller.php';
 
class Androidlogin extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		$this->load->database();
		$this->load->model('M_androidlogin','mal');
		//$this->methods['index_get']['limit'] = 500; // 500 requests per hour per user/key
        //$this->methods['index_post']['limit'] = 100; // 100 requests per hour per user/key
        //$this->methods['index_put']['limit'] = 100; // 100 requests per hour per user/key
        //$this->methods['index_delete']['limit'] = 50; // 50 requests per hour per user/key
    }
 
    // show data login
    function index_get() {
		$email = $this->get('user_email');
		$password = $this->get('user_password');
		$token = $this->get('user_token');
        if ($email=='' && $password =='' && $token =='') {
			  $this->response(array('data' => null,
			  'message' => 'Data Harus Di Isi Terlebih Dahulu',
			'status' => false), 201);
        } else {
			$where = array('user_email' => $email, 'user_password' => md5($password));
			$cek = $this->mal->cek_login('tb_m_users',$where)->num_rows();
			if($cek > 0){
			$where = array('user_email' => $email, 'user_password' => md5($password));
			$this->db->where($where);
				$message = $this->db->get('tb_m_users')->result();
				//$message->status = 'true';
				$this->response(array('status' => true,'message' => 'Sucsess Login','data' =>$message), 200);
				//$this->response($message, 200);
				//$this->response(array('status' => 'true'), 200);
 
		}else{
			
			$this->response(array('data' => null,
			'message' => 'Username Atau Password Salah',
				'status' => false), 201);
		}
        }
       
    }
 
    // insert new data to login
    function index_post() {
		$email = $this->post('user_email');
		$password = $this->input->post('user_password');
		$token = $this->post('user_token');
        //print_r($this->input->post());
			 if ($email=='' && $password =='' && $token =='') {
			  $this->response(array('data' => null,
			  'message' => 'Data Harus Di Isi Terlebih Dahulu',
			'status' => false), 200);
			} else {
			$where = array('user_email' => $email, 'user_password' => md5($password));
			$cek = $this->mal->cek_login('tb_m_users',$where)->num_rows();
			
			//echo $cek;
			if($cek > 0){
			$query = $this->db->query("
			SELECT A.*, C.role_name, D.position_name, E.user_group_description,E.user_group_id,E.is_admin, 
			case when F.employee_name is null then A.employee_name else F.employee_name end as atasan_name FROM tb_m_employee A 
			INNER JOIN tb_m_users B ON A.employee_id = B.employee_id 
			INNER JOIN tb_m_role C ON A.role_id = C.role_id 
			INNER JOIN tb_m_position D ON A.position_id = D.position_id
			INNER JOIN tb_m_user_group E ON B.user_group_id = E.user_group_id
			LEFT JOIN tb_m_employee F ON F.employee_id = A.atasan_id
			WHERE B.user_email = '".$email."' and 
			B.user_password = '".md5($password)."'");
			//return $query->result();
			//$query = $this->db->get();
			
			//$this->db->where($where);
				//$message = $this->db->get('tb_m_users')->result();
				$message = $query->result();
				//$message->status = 'true';
				$this->response(array('status' => true,'message' => 'Sucsess Login','data' =>$message), 200);
 
			}else{
			
			$this->response(array('data' => null,
			'message' => 'Email Atau Password Salah',
				'status' => false), 200);
		}
        }
    }
 
    // update data login
    function index_put() {
        /*
		$login_id = $this->put('login_id');
        $data = array(
                    'login_id' => $this->put('login_id'),
                    'destination'      => $this->put('destination'),
                    'project_id'=> $this->put('project_id'));
                    //'alamat'    => $this->put('alamat'));
        $this->db->where('login_id', $login_id);
        $update = $this->db->update('tb_m_users', $data);
        if ($update) {
            $this->response($data, 200);
        } else {
            $this->response(array('status' => 'fasil', 502));
        }
		*/
    }
 
    // delete login
    function index_delete() {
        /*
		$login_id = $this->delete('login_id');
        $this->db->where('login_id', $login_id);
        $delete = $this->db->delete('tb_m_users');
        if ($delete) {
            $this->response(array('status' => 'success'), 201);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
		*/
    }
 
}
