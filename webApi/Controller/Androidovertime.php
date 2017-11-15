<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-1
 */
 require APPPATH . '/libraries/REST_Controller.php';
 
 class Androidovertime extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		
		$this->load->database();
		//model
		$this->load->model('M_androidshared_model','mas');
		$this->load->model('M_androidovertime_model','mao');
        $this->load->model('M_androidemployee_model','mae');
        $this->load->model('M_androidproject_model','map');
		
		//helper
		$this->load->helper('date');
        //$this->load->helper('number');
        $this->load->helper('html');
    }
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
		$sv		= $this->get('search_view');
		$dt_to = $this->get('dt_to');
		$dt_from = $this->get('dt_from');
        $ot_status = $this->get('ot_status');
		$results = $this->mao->get_overtimes(to_std_dt($dt_from),  to_std_dt($dt_to), $ot_status, $sv,$employee_id,$company_id,$is_admin);
        $recordsTotal = (int)$this->mao->get_overtimes_count(to_std_dt($dt_from),  to_std_dt($dt_to), $ot_status, $sv,$employee_id,$company_id,$is_admin);
		$data = array();
        foreach ($results as $x) {
		$row = array();
		
		$dt = $x->ot_dt;
		$tgl_tampil = date('d M Y', strtotime($dt));
		$row['ot_dt'] = $tgl_tampil;
		//echo $x->ot_dt;
        $row['employee_id'] = $x->employee_id;
        $row['employee_name'] = $x->employee_name;
		$row['ot_id'] = $x->ot_id;
        $row['ot_description'] =  nl2br($x->ot_description);
		$row['ot_hour'] =  $x->ot_hour;
		$row['ot_hour_end'] =  $x->ot_hour_end;
        $row['count_hour'] = $this->count_hour($x->ot_hour,$x->ot_hour_end);
		$row['ot_calculate'] = $x->ot_calculate ;
        //$row['calculate_overtime_pay'] = to_std_currency($this->mao->calculate_overtime_pay($x->employee_id, $x->ot_calculate));
        $row['calculate_overtime_pay'] = to_std_currency($this->mao->calculate_overtime_pay($x->employee_id, $x->ot_calculate,$x->company_id));
        $row['ot_status'] = $this->mao->get_ot_status_message($x->ot_status);
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
		$order = $this->post('order');
		$employee_name = $this->post('employee_name');
		$sv		= $this->post('search_view');
		$dt_to = $this->post('dt_to');
		$dt_from = $this->post('dt_from');
        $ot_status = $this->post('ot_status');
		$results = $this->mao->get_overtimes(to_std_dt($dt_from),  to_std_dt($dt_to), $ot_status, $sv,$employee_id,$company_id,$is_admin);
        $recordsTotal = (int)$this->mao->get_overtimes_count(to_std_dt($dt_from),  to_std_dt($dt_to), $ot_status, $sv,$employee_id,$company_id,$is_admin);
		$data = array();
        foreach ($results as $x) {
		$row = array();
		$dt = $x->ot_dt;
		$tgl_tampil = date('d M Y', strtotime($dt));
		$row['ot_dt'] = $tgl_tampil;
		//echo $x->ot_dt;
        $row['employee_id'] = $x->employee_id;
		$row['employee_name'] = $x->employee_name;
		$row['ot_id'] = $x->ot_id;
        $row['ot_description'] =  nl2br($x->ot_description);
		$row['ot_hour'] =  $x->ot_hour;
		$row['ot_hour_end'] =  $x->ot_hour_end;
        $row['count_hour'] = $this->count_hour($x->ot_hour,$x->ot_hour_end);
		$row['ot_calculate'] = $x->ot_calculate ;
        //$row['calculate_overtime_pay'] = to_std_currency($this->mao->calculate_overtime_pay($x->employee_id, $x->ot_calculate));
        $row['calculate_overtime_pay'] = to_std_currency($this->mao->calculate_overtime_pay($x->employee_id, $x->ot_calculate,$x->company_id));
        $row['ot_status'] = $this->mao->get_ot_status_message($x->ot_status);
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
	function count_hour($hour_start,$hour_end){
		$ot_hour_s = strtotime(date('Y-m-d').' '.$hour_start.':00');
		$ot_hour_e = strtotime(date('Y-m-d').' '.$hour_end.':00');
		$begin_day_unix = strtotime(date('Y-m-d').' 00:00:00');
		$jumlah_time = date('H:i', ($ot_hour_e - ($ot_hour_s - $begin_day_unix)));
		
		return $jumlah_time;
	}
 }
?>
