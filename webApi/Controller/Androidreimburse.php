<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
require APPPATH . '/libraries/REST_Controller.php';
 
class Androidreimburse extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		
		$this->load->database();
		$this->load->model('M_androidshared_model','mas');
        $this->load->model('M_androidreimburse_model', 'rm');
    }
 
    // show data reimburse
    function index_get() {
		$is_admin = $this->get('is_admin');
		$employee_id = $this->get('employee_id');
		$company_id = $this->get('company_id');
        //$src_data = $this->get('search_view');
		 if ($is_admin=='' && $company_id =='' && $employee_id =='') {
			  $this->response(array(
			'status' => false,
			'recordsTotal' => 0,
			'recordsFiltered' => 0,
			'data' => $data = array()
			), 200);
			} else {
		$order = $this->get('order');
        $employee_name = $this->get('employee_name');
        $reimburse_status = $this->get('reimburse_status');
		if ($reimburse_status=='') {
			$reimburse_status = '-';
		}
        $reimburse_dt_from = $this->get('reimburse_dt_from');
        $reimburse_dt_to = $this->get('reimburse_dt_to');
		
        if ($reimburse_dt_from  != "") {
            $reimburse_dt_from = date('Y-m-d', strtotime(str_replace('/', '-', $this->get('reimburse_dt_from'))));
        } else {
            $reimburse_dt_from = "";
        }
        if ($reimburse_dt_to != "") {
            $reimburse_dt_to = date('Y-m-d', strtotime(str_replace('/', '-', $this->get('reimburse_dt_to'))));
        } else {
            $reimburse_dt_to = "";
        }

        $results = $this->rm->get_reimburses($is_admin, $employee_id,$employee_name,$reimburse_status,$reimburse_dt_from,$reimburse_dt_to,$company_id);
		//echo $results;
		
        $recordsTotal = (int) $this->rm->get_reimburses_count($is_admin, $employee_id, $employee_name,$reimburse_status,$reimburse_dt_from,$reimburse_dt_to,$company_id);

        $data = array();
        foreach ($results as $r) {
            $row = array();
			$row['reimburse_id'] = $r->reimburse_id;
            $row['date'] = date('d M Y', strtotime($r->reimburse_dt));
            $row['amount'] = $r->reimburse_amount;
            
			// $row[] = $r->reimburse_type;
            $row['reimburse_type'] = $r->reimburse_type;
            $row['reimburse_description'] = $r->reimburse_description;
            $row['employee_name'] = $r->employee_name;
            
			// $row[] = $r->project_name;
 			$is_approved = 'Menunggu';
            if ($r->is_approved == 1) {
                $is_approved = 'Disetujui';
            } else if ($r->is_approved == 2) {
                $is_approved = 'Ditolak';
            }
            $row['status'] = $is_approved;
            $data[] = $row;
        }

       $this->response(array(
			'status' => true,
			'recordsTotal' => $recordsTotal,
			'recordsFiltered' => $recordsTotal,
			'data' => $data
		), 200);
		}
    }
 
    // insert new data to reimburse
    function index_post() {
		
		$is_admin = $this->post('is_admin');
		$employee_id = $this->post('employee_id');
		$company_id = $this->post('company_id');
        //$src_data = $this->post('search_view');
		 if ($is_admin=='' && $company_id =='' && $employee_id =='') {
			  $this->response(array(
			'status' => false,
			'recordsTotal' => 0,
			'recordsFiltered' => 0,
			'data' => $data = array()
			), 200);
			} else {
		$order = $this->post('order');
        $employee_name = $this->post('employee_name');
        $reimburse_status = $this->post('reimburse_status');
		if ($reimburse_status=='') {
			$reimburse_status = '-';
		}
        $reimburse_dt_from = $this->post('reimburse_dt_from');
        $reimburse_dt_to = $this->post('reimburse_dt_to');
		
        if ($reimburse_dt_from  != "") {
            $reimburse_dt_from = date('Y-m-d', strtotime(str_replace('/', '-', $this->post('reimburse_dt_from'))));
        } else {
            $reimburse_dt_from = "";
        }
        if ($reimburse_dt_to != "") {
            $reimburse_dt_to = date('Y-m-d', strtotime(str_replace('/', '-', $this->post('reimburse_dt_to'))));
        } else {
            $reimburse_dt_to = "";
        }

        $results = $this->rm->get_reimburses($is_admin, $employee_id,$employee_name,$reimburse_status,$reimburse_dt_from,$reimburse_dt_to,$company_id);
		//echo $results;
		
        $recordsTotal = (int) $this->rm->get_reimburses_count($is_admin, $employee_id, $employee_name,$reimburse_status,$reimburse_dt_from,$reimburse_dt_to,$company_id);

        $data = array();
        foreach ($results as $r) {
            $row = array();
			$row['reimburse_id'] = $r->reimburse_id;
            $row['date'] = date('d M Y', strtotime($r->reimburse_dt));
            $row['amount'] = $r->reimburse_amount;
            
			// $row[] = $r->reimburse_type;
            $row['reimburse_type'] = $r->reimburse_type;
           
			 $row['reimburse_description'] = $r->reimburse_description;
            $row['employee_name'] = $r->employee_name;
            
			// $row[] = $r->project_name;
 			$is_approved = 'Menunggu';
            if ($r->is_approved == 1) {
                $is_approved = 'Disetujui';
            } else if ($r->is_approved == 2) {
                $is_approved = 'Ditolak';
            }
            $row['status'] = $is_approved;
            $data[] = $row;
        }

       $this->response(array(
			'status' => true,
			'recordsTotal' => $recordsTotal,
			'recordsFiltered' => $recordsTotal,
			'data' => $data
		), 200);
		}
    }
 
    // update data reimburse
    function index_put() {
        
    }
 
    // delete reimburse
    function index_delete() {
        
    }
 
}
