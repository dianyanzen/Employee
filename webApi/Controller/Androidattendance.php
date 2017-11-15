<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-1
 */
 require APPPATH . '/libraries/REST_Controller.php';
 
 class Androidattendance extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		
		$this->load->database();
		$this->load->model('M_androidpresence_model', 'pm');
		$this->load->model('M_androidshared_model','mas');
		$this->load->model('M_androidleave_model','lm');
        $this->load->model('M_androidattendance_model','am');
        $this->load->model('M_androidemployee_model','mae');
		$this->load->library('encrypt');
    }
	 // show data Presence
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
		$dt_to = to_std_dt($this->get('dt_to'));
		$dt_from = to_std_dt($this->get('dt_from'));
		$sv	= $this->get('search');
		$employees = $this->get('employees');
		
		$results = $this->am->get_attendances(
                                          $sv,
                                          $dt_from,
										  $dt_to,
                                          $employees,
										  $employee_id,
										  $company_id,
										  $is_admin);
        $recordsTotal = $this->am->get_attendances_count(
                                          $sv,
                                          $dt_from,
										  $dt_to,
                                          $employees,
										  $employee_id,
										  $company_id,
										  $is_admin);
		$get_hour_start = $this->am->get_hour_attendance('work_hour_start',$company_id);
		$get_hour_end = $this->am->get_hour_attendance('work_hour_end',$company_id);
        $data = array();
        foreach ($results as $r) {
            $row = array();
			$status = "";
			$in = explode(",", strtotime(date('H:i:s', strtotime(str_replace('-', '/', $r->clock_in)))));
			$out = explode(",", strtotime(date('H:i:s', strtotime(str_replace('-', '/', $r->clock_out)))));
			$hour_in = strtotime($get_hour_start);
			$hour_out = strtotime($get_hour_end);
			
			for($i=0;$i<count($in);$i++){
				for($j=0;$j<count($out);$j++){
					if ($in[$i] > $hour_in) {
						$status .= "T";
					}elseif ($out[$j] > $hour_out) {
						$status .= "T";
					}else{
						$status .= "";
					} 
				 }
			  }
			$row = array();
			$row['attendance_id'] = $r->attendance_id;
			$row['employee_name'] = $r->employee_name;
			$dt = $r->clock_in;
			//echo $dt;
			//die;
			$tgl_tampil = date('d M Y', strtotime($dt));
			$row['attendance_dt'] = $tgl_tampil;
			$row['clock_in'] = to_std_dt_table($r->clock_in);
			$row['clock_in'] = ($r->clock_in != '') ?  date('H:i:s', strtotime(str_replace('-', '/', $r->clock_in))) : '-';
			$row['clock_out'] = ($r->clock_out != '') ? date('H:i:s', strtotime(str_replace('-', '/', $r->clock_out))) : '-';
			if($r->work_hour == null){
			$wh = '-';	
			}else{
			$wh = $r->work_hour;	
			}
			$row['work_hour'] = $wh;
            $row['status'] = $status;
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
		$order = $this->input->post('order');
		$dt_to = to_std_dt($this->post('dt_to'));
		$dt_from = to_std_dt($this->post('dt_from'));

		$sv	= $this->post('search');
		$employees = $this->post('employees');
		
		$results = $this->am->get_attendances(
                                          $sv,
                                          $dt_from,
										  $dt_to,
                                          $employees,
										  $employee_id,
										  $company_id,
										  $is_admin);
        $recordsTotal = $this->am->get_attendances_count(
                                          $sv,
                                          $dt_from,
										  $dt_to,
                                          $employees,
										  $employee_id,
										  $company_id,
										  $is_admin);
		$get_hour_start = $this->am->get_hour_attendance('work_hour_start',$company_id);
		$get_hour_end = $this->am->get_hour_attendance('work_hour_end',$company_id);
        $data = array();
        foreach ($results as $r) {
            $row = array();
			$status = "";
			$in = explode(",", strtotime(date('H:i:s', strtotime(str_replace('-', '/', $r->clock_in)))));
			$out = explode(",", strtotime(date('H:i:s', strtotime(str_replace('-', '/', $r->clock_out)))));
			$hour_in = strtotime($get_hour_start);
			$hour_out = strtotime($get_hour_end);
			
			for($i=0;$i<count($in);$i++){
				for($j=0;$j<count($out);$j++){
					if ($in[$i] > $hour_in) {
						$status .= "T";
					}elseif ($out[$j] > $hour_out) {
						$status .= "T";
					}else{
						$status .= "";
					} 
				 }
			  }
			$row = array();
			$row['attendance_id'] = $r->attendance_id;
			$row['employee_name'] = $r->employee_name;
			$dt = $r->clock_in;
			//echo $dt;
			//die;
			$tgl_tampil = date('d M Y', strtotime($dt));
			$row['attendance_dt'] = $tgl_tampil;
			$row['clock_in'] = to_std_dt_table($r->clock_in);
			$row['clock_in'] = ($r->clock_in != '') ?  date('H:i:s', strtotime(str_replace('-', '/', $r->clock_in))) : '-';
			$row['clock_out'] = ($r->clock_out != '') ? date('H:i:s', strtotime(str_replace('-', '/', $r->clock_out))) : '-';
			if($r->work_hour == null){
			$wh = '-';	
			}else{
			$wh = $r->work_hour;	
			}
			$row['work_hour'] = $wh;
            $row['status'] = $status;
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
 }
