<?php

defined('BASEPATH') OR exit('No direct script access allowed');

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-3
 */
 require APPPATH . '/libraries/REST_Controller.php';
 
 class Androiddashboard extends REST_Controller {
 
    function __construct($config = 'rest') {
        parent::__construct($config);
		
		$this->load->database();
		//model
        $this->load->model('M_androiddashboard_model','mad');
		
    }
	// show data count all menu
    function index_get() {
		$is_admin = $this->get('is_admin');
		$employee_id = $this->get('employee_id');
		$company_id = $this->get('company_id');
		
		 if ($is_admin!='' && $company_id !='' && $employee_id !='') {
			//Data Dashboard row 1 (clock in)
			$stat1 = $this->mad->get_clock($employee_id,$company_id);
			if ($stat1 =='--:--:--'){
			$inf1 = 'Anda Belum Clock In';
			}else{
			$inf1 = 'Anda Sudah Clock In';
			}
			$data1 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 1
				, 'main_dashboard' => 'Clock In'
				, 'status_dashboard' => $stat1
				, 'info_dashboard' => $inf1
			);
			//Data Dashboard row 2 (office travel)
			$stat2 = $this->mad->get_travel($is_admin,$employee_id,$company_id);
			$inf2 = $this->mad->get_travel_status($is_admin,$employee_id,$company_id);
			$data2 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 2
				, 'main_dashboard' => 'Dinas Luar'
				, 'status_dashboard' => $stat2
				, 'info_dashboard' => 'Disetujui: '.$inf2.' Dinas Luar'
			);
			//Data Dashboard row 3 (claim)
			$stat3 = $this->mad->get_reimburses($is_admin,$employee_id,$company_id);
			$inf3 = $this->mad->get_reimburses_status($is_admin,$employee_id,$company_id);
			$data3 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 3
				, 'main_dashboard' => 'Klaim'
				, 'status_dashboard' => $stat3
				, 'info_dashboard' => 'Disetujui: '.$inf3.' Klaim'
			);
			//Data Dashboard row 4 (leaves)
			$stat4 = $this->mad->getLeaves($is_admin,$employee_id,$company_id);
			$inf4 = $this->mad->getLeaves_status($is_admin,$employee_id,$company_id);
			$data4 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 4
				, 'main_dashboard' => 'Izin'
				, 'status_dashboard' => $stat4
				, 'info_dashboard' => 'Disetujui: '.$inf4.' Izin'
			);
			//Data Dashboard row 5 (overtime)
			$stat5 = $this->mad->getOvertime($is_admin,$employee_id,$company_id);
			$inf5 = $this->mad->getOvertime_status($is_admin,$employee_id,$company_id);
			$data5 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 5
				, 'main_dashboard' => 'Lembur'
				, 'status_dashboard' => $stat5
				, 'info_dashboard' => 'Disetujui: '.$inf5.' Lembur'
			);
			//Data Dashboard row 6 (attendance)
			$stat6 = $this->mad->get_attendances($employee_id,$company_id);
			$inf6 = $this->mad->get_attendances_status($employee_id,$company_id);
			$data6 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 6
				, 'main_dashboard' => 'Kehadiran'
				, 'status_dashboard' => $stat6
				, 'info_dashboard' => 'Dari: '.$inf6.' Pegawai'
			);
			//Data Dashboard row 7 (project)
			$stat7 = $this->mad->getProject($employee_id,$company_id);
			if ($stat7 == '')
			{
				$stat7 = 0;
			}
			$inf7 = $this->mad->getProject_status($employee_id,$company_id);
			if ($inf7 == '')
			{
				$inf7 = 0;
			}
			$data7 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 7
				, 'main_dashboard' => 'Tugas'
				, 'status_dashboard' => $stat7
				, 'info_dashboard' => 'Selesai: '.$inf7.' Tugas'
			);
			//Data Dashboard row 8 (calendar)
			$stat8 = $this->mad->getHoliday($company_id);
			$inf8 = $this->mad->getHoliday_status($company_id);
			$data8 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 8
				, 'main_dashboard' => 'Libur'
				, 'status_dashboard' => $stat8
				, 'info_dashboard' => $inf8.' Libur Yang Akan Datang '
			);
			$result[] = $data1;
			$result[] = $data2;
			$result[] = $data3;
			$result[] = $data4;
			$result[] = $data5;
			$result[] = $data6;
			$result[] = $data7;
			$result[] = $data8;
			
			  $this->response(array(
			'status' => true,
			'recordsTotal' => 8,
			'recordsFiltered' => 8,
			'data' => $result
			), 200);
			
			
		 }
	}
	 function index_post() {
	 $is_admin = $this->post('is_admin');
		$employee_id = $this->post('employee_id');
		$company_id = $this->post('company_id');
		
		 if ($is_admin!='' && $company_id !='' && $employee_id !='') {
			//Data Dashboard row 1 (clock in)
			$stat1 = $this->mad->get_clock($employee_id,$company_id);
			if ($stat1 =='--:--:--'){
			$inf1 = 'Anda Belum Clock In';
			}else{
			$inf1 = 'Anda Sudah Clock In';
			}
			$data1 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 1
				, 'main_dashboard' => 'Clock In'
				, 'status_dashboard' => $stat1
				, 'info_dashboard' => $inf1
			);
			//Data Dashboard row 2 (office travel)
			$stat2 = $this->mad->get_travel($is_admin,$employee_id,$company_id);
			$inf2 = $this->mad->get_travel_status($is_admin,$employee_id,$company_id);
			$data2 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 2
				, 'main_dashboard' => 'Dinas Luar'
				, 'status_dashboard' => $stat2
				, 'info_dashboard' => 'Disetujui: '.$inf2.' Dinas Luar'
			);
			//Data Dashboard row 3 (claim)
			$stat3 = $this->mad->get_reimburses($is_admin,$employee_id,$company_id);
			$inf3 = $this->mad->get_reimburses_status($is_admin,$employee_id,$company_id);
			$data3 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 3
				, 'main_dashboard' => 'Klaim'
				, 'status_dashboard' => $stat3
				, 'info_dashboard' => 'Disetujui: '.$inf3.' Klaim'
			);
			//Data Dashboard row 4 (leaves)
			$stat4 = $this->mad->getLeaves($is_admin,$employee_id,$company_id);
			$inf4 = $this->mad->getLeaves_status($is_admin,$employee_id,$company_id);
			$data4 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 4
				, 'main_dashboard' => 'Izin'
				, 'status_dashboard' => $stat4
				, 'info_dashboard' => 'Disetujui: '.$inf4.' Izin'
			);
			//Data Dashboard row 5 (overtime)
			$stat5 = $this->mad->getOvertime($is_admin,$employee_id,$company_id);
			$inf5 = $this->mad->getOvertime_status($is_admin,$employee_id,$company_id);
			$data5 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 5
				, 'main_dashboard' => 'Lembur'
				, 'status_dashboard' => $stat5
				, 'info_dashboard' => 'Disetujui: '.$inf5.' Lembur'
			);
			//Data Dashboard row 6 (attendance)
			$stat6 = $this->mad->get_attendances($employee_id,$company_id);
			$inf6 = $this->mad->get_attendances_status($employee_id,$company_id);
			$data6 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 6
				, 'main_dashboard' => 'Kehadiran'
				, 'status_dashboard' => $stat6
				, 'info_dashboard' => 'Dari: '.$inf6.' Pegawai'
			);
			//Data Dashboard row 7 (project)
			$stat7 = $this->mad->getProject($employee_id,$company_id);
			if ($stat7 == '')
			{
				$stat7 = 0;
			}
			$inf7 = $this->mad->getProject_status($employee_id,$company_id);
			if ($inf7 == '')
			{
				$inf7 = 0;
			}
			$data7 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 7
				, 'main_dashboard' => 'Tugas'
				, 'status_dashboard' => $stat7
				, 'info_dashboard' => 'Selesai: '.$inf7.' Tugas'
			);
			//Data Dashboard row 8 (calendar)
			$stat8 = $this->mad->getHoliday($company_id);
			$inf8 = $this->mad->getHoliday_status($company_id);
			$data8 = array(
				'company_id' => $company_id
				, 'employee_id' => $employee_id
				, 'main_id' => 8
				, 'main_dashboard' => 'Kalender'
				, 'status_dashboard' => $stat8
				, 'info_dashboard' => $inf8.' Hari Libur Yang Akan Datang '
			);
			$result[] = $data1;
			$result[] = $data2;
			$result[] = $data3;
			$result[] = $data4;
			$result[] = $data5;
			$result[] = $data6;
			$result[] = $data7;
			$result[] = $data8;
			
			  $this->response(array(
			'status' => true,
			'recordsTotal' => 8,
			'recordsFiltered' => 8,
			'data' => $result
			), 200);
			
			
		 }
	 }
 }
