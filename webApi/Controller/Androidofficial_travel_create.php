<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
require APPPATH . '/libraries/REST_Controller.php';
 
class Androidofficial_travel_create extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		$this->load->database();
		$this->load->model('M_androidofficial_travel_model','mat');
        $this->load->model('M_androidemployee_model','mae');
		$this->load->model('M_androidshared_model','mas');
		$this->load->model('M_androidproject_model','map');
    }
	
	function index_get() {
		
			
	}
	
	function index_post() {
		$is_admin = $this->post('is_admin');
		$employee_id = $this->post('employee_id');
		$employee_name = $this->post('employee_name');
		$company_id = $this->post('company_id');
		$dt_from = $this->post('dt_from');
		$dt_to = $this->post('dt_to');
		$project_id = $this->post('project_id');
		$post_travel_id = $this->post('travel_id');
		$destination = $this->post('destination');
		$jumlah_peserta = $this->post('jumlah_peserta');
		$peserta = array($this->post('peserta'));
		print_r($peserta);
		die;
		$lada = json_encode($peserta);
		//die;
		print_r($lada);
		die;

			if ($project_id == ''){
			$project_id = 0;
			}
		
		  if ($is_admin =='' || $company_id =='' || $employee_id == '' || $employee_name == '' || $dt_from == '' || $dt_to == '' || $destination =='') {
			  $this->response(array(
			'status' => false,
			'message' => 'Data Gagal Di Simpan'
			), 200);
			} else {
				 $this->response(array(
			'status' => false,
			'message' => $peserta
			), 200);
			/*
			$travel_id 				= $this->mat->save_official_travel($dt_from,$dt_to,$employee_name,$project_id,$post_travel_id,$employee_id,$destination) ;			
			
			$official_travel 		= $this->mat->get_official_travel($travel_id,$company_id);			
			
			// setup data
			$data['created_name'] 	= $official_travel[0]->created_name;
			$data['assigned_name'] 	= $official_travel[0]->assigned_name;
			$data['tgl_awal'] 		= date('d F Y', strtotime($official_travel[0]->dt_from));
			$data['tgl_akhir'] 		= date('d F Y', strtotime($official_travel[0]->dt_to));
			$data['destination'] 	= $official_travel[0]->destination;
			$data['members'] 		= $official_travel[0]->members;
			$data['project_name'] 	= $official_travel[0]->project_name;
			
			// send notif ke atasan nya
			if ($official_travel[0]->user_email_assigned_by != '')
			{
				
				$data['notif_status']	= 'email_atasan';
				$this->mas->send_mail(
					'official_travel_mail_create'
					, $data
					, 'Ada Pengajuan Perjalanan Dinas Baru'
					, $official_travel[0]->user_email_assigned_by
				);				
			}
			
			//Send Email Ke Peserta
			$official_travel_member = $this->mat->get_email_members($travel_id);
			
			$data['notif_status'] = 'email_invite';
			$this->mas->send_mail(
				'official_travel_mail_create'
				, $data
				, 'Anda Masuk Ke Perjalanan Dinas Baru'
				, $official_travel_member
			);
			
			 $this->response(array(
			'status' => true,
			'message' => 'Data Berhasil Di Simpan'
			), 200);
			*/
			}
	}
}
