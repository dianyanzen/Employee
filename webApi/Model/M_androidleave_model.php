<?php

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */

class M_androidleave_model extends CI_Model {

    function __construct() {
        parent:: __construct();
    }
	
	function get_sisa_cuti($employee_id)
	{
		$jatah_cuti = $this->maxLeaves();
		$cuti_bersama = $this->cnt_cutiBersama();
		$cuti_terpakai = $this->get_cutiTerpakai($employee_id);
		return $jatah_cuti - $cuti_bersama - $cuti_terpakai;
	}

    function maxLeaves($company_id) {
        $c_id = $company_id;
        
        $sql = "SELECT system_value_num FROM tb_m_company_prefferences WHERE company_id = '".$c_id."' and system_type='config_employee' and system_code='jatah_cuti'";
        $q = $this->db->query($sql);
        $r = $q->row(0);
        return $r->system_value_num;
    }
    
    function cnt_cutiBersama($company_id){
        $year = date('Y');
        $c_id = $company_id;
        
        $sql = "select 
					count(holiday_id) as cnt 
				from 
					tb_m_holiday_cal 
				where 
					company_id = '".$c_id."' and year(holiday_dt) = '". $year . "'
					and holiday_type = 'CUTIBERSAMA'
				";
        $q = $this->db->query($sql);
        $r = $q->row(0);
        return $r->cnt;
    }
        
    function get_cutiTerpakai($id, $status = '1'){
		$id = ($id == '') ? 0 : $id;
        $sql ="select ifnull(sum(time_off_days),0) as cnt from tb_r_time_off where employee_id=". $id ." and time_off_type='CUTI' and time_off_status='".$status."'";
        return $this->db->query($sql)->row()->cnt;
    }      

    function get_employees($company_id) {
        $sql = "select employee_id, employee_name from tb_m_employee WHERE boleh_cuti=1 and company_id='".$company_id."' order by employee_name";
        return $this->db->query($sql)->result();
    }

    function delete_leave($id) {
        $sql = "delete from tb_r_time_off where time_off_id='" . $id . "'";
        $this->db->query($sql);

        return 1;
    }

    function app_leave($data, $id) {

        $this->db
                ->where('time_off_id', $id)
                ->update('tb_r_time_off', $data);
    }

    function update_leave($data,$user_name) {
        $id = $data['time_off_id'];
        $desc = $data['desc'];
        $data = array(
            'time_off_dt_from' => $data['dt_from'],
            'time_off_dt_to' => $data['dt_to'],
            'time_off_description' => '['.$data['cat'].'] '.$desc,
            'time_off_status' => '0',
            'time_off_days' => $data['off_days'],
            'changed_dt' => date("Y-m-d H:i:s"),
            'changed_by' => $user_name
        );

        $this->db
                ->where('time_off_id', $id)
                ->update('tb_r_time_off', $data);
    }

    function get_employee($id) {
        $sql = "select * from tb_m_employee where employee_id='" . $id . "'";
        return $this->db->query($sql)->result_array();
    }

    function save_leave($user_name) 
	{
		$time_off_type = $this->input->post('time_off_type');
		$totx = explode('|', $time_off_type);
		$time_off_type = $totx[0];
		
		$addition_description= '';
		$time_off_description = $this->input->post('time_off_description', true);		
		if ($totx[1] != '-')
		{
			$addition_description = '';
			switch($totx[1])
			{
				case 0:
					$addition_description = '[Cuti Tahunan] ';
					break;
				case 1:
					$addition_description = '[Cuti Melahirkan] ';
					break;
				case 2:
					$addition_description = '[Cuti Haid] ';
					break;
				case 3:
					$addition_description = '[Cuti Menikah] ';
					break;
				case 4:
					$addition_description = '[Cuti Besar] ';
					break;
				default:
					$addition_description = '';
			}
		}
		$time_off_description = $addition_description . $time_off_description;
		
		$time_off_dt_from_to	= $this->input->post('time_off_dt_from_to', true);
		$dtft 					= explode(' - ', $time_off_dt_from_to);
		$dt_from				= date('Y-m-d', strtotime( str_replace('/', '-', $dtft[0])));
		$dt_to					= date('Y-m-d', strtotime( str_replace('/', '-', $dtft[1])));	

		// $date1	= new DateTime($dt_from);
		// $date2	= new DateTime($dt_to);
		// $diff	= $date2->diff($date1)->format('%a');
		
        $data = array(
            'employee_id' 			=> $this->input->post('employee_id', true),
            'time_off_dt_from' 		=> $dt_from,
            'time_off_dt_to' 		=> $dt_to,
            'time_off_days' 		=> $this->input->post('time_off_days', true),
            'time_off_type' 		=> $time_off_type,
            'time_off_description' 	=> $time_off_description,
            'time_off_status' => '0',
            'created_dt' => date("Y-m-d H:i:s"),
            'created_by' => $user_name
        );

        $this->db->insert('tb_r_time_off', $data);
        return $this->db->insert_id();
    }

    function get_leave($id,$company_id) {
        $sql = "select 
					e.employee_name
					, e.atasan_id
					, e.user_email as user_email
					, ea.employee_name as atasan_name
					, ea.user_email as user_email_atasan
					, eb.employee_name as leave_status_by_name
					, r.* 
				from 
					tb_r_time_off r 
					inner join tb_m_employee e on r.employee_id = e.employee_id
					left join tb_m_employee ea on ea.employee_id = e.atasan_id
					left join tb_m_employee eb on eb.employee_id = r.time_off_status_by
				where 
					r.time_off_id='" . $id . "'";
        return $this->db->query($sql, array($id, $company_id))->row();
    }

    function get_leave_status_message($ot_status) {
        switch ($ot_status) {
            case '1':
                //return "<span style='margin-left: -20px' class='label label-success'>Diterima</span>";
                return 'Disetujui';
            case '2':
                //return "<span style='margin-left: -20px' class='label label-brown'>Ditolak</span>";
                return 'Ditolak';
            default:
                return 'Menunggu';
            //return "<span style='margin-left: -20px' class='label label-warning'>Pengajuan</span>";
            //return 'Pengajuan';
        }
    }

    function get_positions($start = '', $length = '', $sv = '',$company_id='') {
        $sql = "
		select 
		a.position_id, 
		a.position_name, 
		a.position_description,
		count(b.employee_id) as jmlh_pegawai 
		from 
		tb_m_position a
		left join tb_m_employee b on b.position_id = a.position_id
		where
		a.company_id = '" . $company_id . "'
		and (a.position_name like '%" . $sv . "%' or a.position_description like '%" . $sv . "%')
		group by a.position_id
		order by a.position_name
		";

        // $sql .= " order by position_name";

        if ($start != '' && $length != '') {
            $sql .= " limit " . $start . ", " . $length;
        }

        return $this->db->query($sql)->result();
    }
	
	function getLeaves($status = '-', $date_from = '', $date_to = '', $sv = '',$company_id='',$is_admin ='', $employee_id = '') {

        $sql = "
			select 
                e.employee_name,
                t.*
                from 
					tb_r_time_off t inner join tb_m_employee e
                on t.employee_id=e.employee_id
                WHERE 1 
		";

        if ($status != '-' && $status != '') {
            $sql .= " and time_off_status = '" . $status . "'";
        }

        if (($date_from != '') && ($date_to != '')) {
            $sql .= " and (( time_off_dt_from BETWEEN '" . $date_from . "' and '" . $date_to . "' ) or (time_off_dt_to BETWEEN '" . $date_from . "' and '" . $date_to . "'  ))";
        }

        if ($is_admin != 1) {
            $sql .=" and (e.employee_id = '" . $employee_id . "' or e.atasan_id = '" . $employee_id . "')";
        }
		$sql .= " and e.company_id = '" . $company_id . "'";		
		if ($sv != '') {
            $sql .= " and e.employee_name like '%" . $sv . "%'";
        }
		
		
        $sql.=" order by time_off_dt_from desc";
		
		//echo $sql;
		//die;
        return $this->db->query($sql)->result();
    }

    function getLeaves_count($status = '-', $date_from = '', $date_to = '', $sv = '',$company_id='',$is_admin ='', $employee_id = '') {
        $sql = "
			select 
                count(t.time_off_id) as cnt
                from 
					tb_r_time_off t inner join tb_m_employee e
                on t.employee_id=e.employee_id
                WHERE 1  
		";

        if ($status != '-' && $status != '') {
            $sql .= " and time_off_status = '" . $status . "'";
        }

        if (($date_from != '') && ($date_to != '')) {
            $sql .= " and (( time_off_dt_from BETWEEN '" . $date_from . "' and '" . $date_to . "' ) or (time_off_dt_to BETWEEN '" . $date_from . "' and '" . $date_to . "'  ))";
        }

        if ($is_admin != 1) {
            $sql .=" and (e.employee_id = '" . $employee_id . "' or e.atasan_id = '" . $employee_id . "')";
        }
		$sql .= " and e.company_id = '" . $company_id . "'";
		
		if ($sv != '') {
            $sql .= " and e.employee_name like '%" . $sv . "%'";
        }
        $sql.=" order by time_off_dt_from desc";
		 // echo $sql;
		// die;
        return $this->db->query($sql)->row()->cnt;
    }

}
