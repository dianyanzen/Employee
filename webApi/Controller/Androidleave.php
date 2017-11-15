<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
require APPPATH . '/libraries/REST_Controller.php';
 
class Androidleave extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		
		$this->load->database();
		$this->load->model('M_androidshared_model','mas');
        $this->load->model('M_androidleave_model', 'lm');
        $this->load->model('M_androidemployee_model','mae');
        $this->load->helper('date');
    }
 
    // show data leave
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
		$user_name = $this->get('user_name');
		$order = $this->get('order');
		$employee_name = $this->get('employee_name');
        $sv = $this->get('search');
        
		$leave_status = $this->get('leave_status', true);
		$leave_dt_to = $this->get('leave_dt_to', true);
		$leave_dt_from = $this->get('leave_dt_from', true);

        // $date_range = extract_date_range_picker($this->get('date_range'));
        // $dt_from = $date_range->start;
        // $dt_to = $date_range->end;

        $results = $this->lm->getLeaves($leave_status, to_std_dt($leave_dt_from), to_std_dt($leave_dt_to), $sv, $company_id, $is_admin, $employee_id);
        $recordsTotal = (int) $this->lm->getLeaves_count($leave_status, to_std_dt($leave_dt_from), to_std_dt($leave_dt_to), $sv, $company_id, $is_admin, $employee_id);

        $data = array();
        foreach ($results as $r) {
            $row = array();
			
			$ds = $r->time_off_dt_from;
			$dt = $r->time_off_dt_to;
			$tgl_tampil = date('d M Y', strtotime($ds)) . ' sd ' . date('d M Y', strtotime($dt));
			
			if ($ds == $dt)
			{
				$tgl_tampil = date('d M Y', strtotime($dt));
			}
			elseif (date('Y-m', strtotime($ds)) == date('Y-m', strtotime($dt)))
			{
				$tgl_tampil = date('d', strtotime($ds)) . ' - ' . date('d M Y', strtotime($dt));
			}
			$row['time_off_id'] = $r->time_off_id;
			$row['date'] = $tgl_tampil;
            $row['time_off_days'] = $r->time_off_days . ' Hari';
            $row['time_off_type'] = $r->time_off_type;
            $row['employee_name'] = $r->employee_name;
            $row['time_off_status'] = $this->lm->get_leave_status_message($r->time_off_status);
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
 
    // insert new data to leave
    function index_post() {
		$is_admin = $this->post('is_admin');
		$employee_id = $this->post('employee_id');
		$company_id = $this->post('company_id');
		$this->response(array('1' => $is_admin
		,'2' => $employee_id
		,'3' => $company_id
		,'3' => 3
		),200);
		die;
        //$src_data = $this->post('search_view');
		 if ($is_admin=='' && $company_id =='' && $employee_id =='') {
			  $this->response(array(
			'status' => false,
			'recordsTotal' => 0,
			'recordsFiltered' => 0,
			'data' => $data = array()
			), 200);
			} else {
		$user_name = $this->post('user_name');
		$order = $this->post('order');
        $sv = $this->post('search');
        
		$leave_status = $this->post('leave_status', true);
		$leave_dt_to = $this->post('leave_dt_to', true);
		$leave_dt_from = $this->post('leave_dt_from', true);

        // $date_range = extract_date_range_picker($this->post('date_range'));
        // $dt_from = $date_range->start;
        // $dt_to = $date_range->end;

        $results = $this->lm->getLeaves($leave_status, to_std_dt($leave_dt_from), to_std_dt($leave_dt_to), $sv, $company_id, $is_admin, $employee_id);
        $recordsTotal = (int) $this->lm->getLeaves_count($leave_status, to_std_dt($leave_dt_from), to_std_dt($leave_dt_to), $sv, $company_id, $is_admin, $employee_id);

        $data = array();
        foreach ($results as $r) {
            $row = array();
			
			$ds = $r->time_off_dt_from;
			$dt = $r->time_off_dt_to;
			$tgl_tampil = date('d M Y', strtotime($ds)) . ' sd ' . date('d M Y', strtotime($dt));
			
			if ($ds == $dt)
			{
				$tgl_tampil = date('d M Y', strtotime($dt));
			}
			elseif (date('Y-m', strtotime($ds)) == date('Y-m', strtotime($dt)))
			{
				$tgl_tampil = date('d', strtotime($ds)) . ' - ' . date('d M Y', strtotime($dt));
			}
			$row['time_off_id'] = $r->time_off_id;
			$row['date'] = $tgl_tampil;
            $row['time_off_days'] = $r->time_off_days . ' Hari';
            $row['time_off_type'] = $r->time_off_type;
            $row['employee_name'] = $r->employee_name;
            $row['time_off_status'] = $this->lm->get_leave_status_message($r->time_off_status);
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
 
    // update data leave
    function index_put() {
        
    }
 
    // delete leave
    function index_delete() {
        
    }
 
}
