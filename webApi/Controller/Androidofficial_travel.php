<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
require APPPATH . '/libraries/REST_Controller.php';
 
class Androidofficial_travel extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		$this->load->database();
		$this->load->model('M_androidofficial_travel_model','mat');
    }
 
    // show data travel
    function index_get() {
		$is_admin = $this->get('is_admin');
		$employee_id = $this->get('employee_id');
		$company_id = $this->get('company_id');
		  if ($is_admin=='' && $company_id =='' && $employee_id =='') {
			  $this->response(array(
			'status' => false,
			'recordsTotal' => 0,
			'recordsFiltered' => 0,
			'data' => $data = array()
			), 200);
			} else {
		$order = $this->get('order');
		$sv		= $this->get('search_view');
		$s_dt_from = ($this->get('s_dt_from', true) != '') ? date('Y-m-d', strtotime( str_replace('/', '-', $this->get('s_dt_from', true)))) : '';
		$s_dt_to = ($this->get('s_dt_to', true) != '') ? date('Y-m-d', strtotime( str_replace('/', '-', $this->get('s_dt_to', true)))) : '';
		$s_project_id = ($this->get('s_project_id') != 'null') ? json_decode($this->get('s_project_id')) : '';
		$s_travel_status = ($this->get('s_travel_status') != '-') ? $this->get('s_travel_status') : '';
		$results 			= $this->mat->get_official_travels($sv, $s_dt_from, $s_dt_to, $s_project_id, $s_travel_status, $is_admin, $employee_id, $company_id);
        $recordsTotal       = (int)$this->mat->get_official_travels_count($sv, $s_dt_from, $s_dt_to, $s_project_id, $s_travel_status,$is_admin,$employee_id, $company_id);
	    $data = array();
		foreach ($results as $r) 
		{
			$datediff = strtotime($r->dt_to) - strtotime($r->dt_from);
			$days = floor($datediff / (60 * 60 * 24));
			$days = ($days == 0) ? 1 : $days;
			
            $row = array();
			
			$ds = $r->dt_from;
			$dt = $r->dt_to;
			$tgl_tampil = date('d M Y', strtotime($ds)) . ' sd ' . date('d M Y', strtotime($dt));
			
			if ($ds == $dt)
			{
				$tgl_tampil = date('d M Y', strtotime($dt));
			}
			elseif (date('Y-m', strtotime($ds)) == date('Y-m', strtotime($dt)))
			{
				$tgl_tampil = date('d', strtotime($ds)) . ' - ' . date('d M Y', strtotime($dt));
			}
			
			$project_name = ($r->project_name != '') ? $r->project_name : '-';
			$row['travel_id'] = $r->travel_id;
			$row['date'] = $tgl_tampil;
			$row['long'] = $days .' Hari';
			$row['destination'] = (substr($r->destination, 0, 200));
			$row['project_name'] = $project_name;
			
			$jml_org = ($r->members > 0) ? $r->members . ' Orang' : '-';
			$row['aproval_by'] = $r->created_name . ' ' . $jml_org;
			
			
			$is_approved = 'Menunggu';
            if ($r->travel_status == 1) {
                $is_approved = 'Disetujui';
            } else if ($r->travel_status == 2) {
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
 
    // insert new data to travel
    function index_post() {
		$is_admin = $this->post('is_admin');
		$employee_id = $this->post('employee_id');
		$company_id = $this->post('company_id');
		 if ($is_admin=='' && $employee_id =='' && $company_id =='') {
			  $this->response(array(
			'status' => false,
			'recordsTotal' => $recordsTotal,
			'recordsFiltered' => $recordsTotal,
			'data' => $data
			), 200);
			} else {
		$order = $this->post('order');
		$sv		= $this->post('search_view');
		$s_dt_from = ($this->post('s_dt_from', true) != '') ? date('Y-m-d', strtotime( str_replace('/', '-', $this->post('s_dt_from', true)))) : '';
		$s_dt_to = ($this->post('s_dt_to', true) != '') ? date('Y-m-d', strtotime( str_replace('/', '-', $this->post('s_dt_to', true)))) : '';
		$s_project_id = ($this->post('s_project_id') != 'null') ? json_decode($this->post('s_project_id')) : '';
		$s_travel_status = ($this->post('s_travel_status') != '-') ? $this->post('s_travel_status') : '';
		$results 			= $this->mat->get_official_travels($sv, $s_dt_from, $s_dt_to, $s_project_id, $s_travel_status, $is_admin, $employee_id, $company_id);
        $recordsTotal       = (int)$this->mat->get_official_travels_count($sv, $s_dt_from, $s_dt_to, $s_project_id, $s_travel_status,$is_admin,$employee_id, $company_id);
	    $data = array();
		foreach ($results as $r) 
		{
			$datediff = strtotime($r->dt_to) - strtotime($r->dt_from);
			$days = floor($datediff / (60 * 60 * 24));
			$days = ($days == 0) ? 1 : $days;
			
            $row = array();
			
			$ds = $r->dt_from;
			$dt = $r->dt_to;
			$tgl_tampil = date('d M Y', strtotime($ds)) . ' sd ' . date('d M Y', strtotime($dt));
			
			if ($ds == $dt)
			{
				$tgl_tampil = date('d M Y', strtotime($dt));
			}
			elseif (date('Y-m', strtotime($ds)) == date('Y-m', strtotime($dt)))
			{
				$tgl_tampil = date('d', strtotime($ds)) . ' - ' . date('d M Y', strtotime($dt));
			}
			
			$project_name = ($r->project_name != '') ? $r->project_name : '-';
			$row['travel_id'] = $r->travel_id;
			$row['date'] = $tgl_tampil;
			$row['long'] = $days .' Hari';
			$row['destination'] = (substr($r->destination, 0, 200));
			$row['project_name'] = $project_name;
			
			$jml_org = ($r->members > 0) ? $r->members . ' Orang' : '-';
			$row['aproval_by'] = $r->created_name . ' ' . $jml_org;
			$row['created_name'] = $r->created_name;
			$row['jml_org'] = $jml_org;
			$is_approved = 'Menunggu';
            if ($r->travel_status == 1) {
                $is_approved = 'Disetujui';
            } else if ($r->travel_status == 2) {
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
 
    // update data travel
    function index_put() {
        
    }
 
    // delete travel
    function index_delete() {
        
    }
 
}
