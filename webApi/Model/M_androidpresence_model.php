<?php

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-1
 */

class M_androidpresence_model extends CI_Model {
	
	private $EMPLOYEE_ID;
	private $COMPANY_ID;
	
    function __construct() {
        parent:: __construct();
    }
	
	function getSelfPresence($presence_dt,  $company_id, $employee_id){
		
		$this->db->select("a.attendance_id, e.employee_id
							, DATE_FORMAT(a.clock_in, '%Y-%m-%d') as attendance_dt
							, DATE_FORMAT(a.clock_in, '%H:%i:%s') as clock_in
							, DATE_FORMAT(a.clock_out, '%H:%i:%s') as clock_out
							, a.permit_out
							, a.permit_starttime, a.permit_endtime
							, a.long_in, a.lat_in
							, SUBTIME(TIMEDIFF(a.clock_out, a.clock_in), fn_GetPrefference(".$company_id.", 'config_attendance', 'breaktime')) as work_hour
							, e.no_reg, e.employee_name, p.position_name, r.role_name, rs.service_periode
							");
        $this->db->from('tb_m_employee e');
		$this->db->join('tb_r_attendance a', "e.employee_id = a.employee_id AND date_format(a.clock_in, '%Y-%m-%d') = '".$presence_dt ."'", 'left');
        $this->db->join('tb_m_position p', 'e.position_id = p.position_id' , 'inner');
        $this->db->join('tb_m_role r', 'e.role_id = r.role_id' , 'inner');
        $this->db->join('tb_m_role_salary rs', 'e.role_salary_id = rs.role_salary_id' , 'inner');
        $this->db->where("e.employee_id", $employee_id);

		$this->db->limit(1);
		$query = $this->db->get();
		
		if ($query->num_rows() == 1) {
			return $query->row();
		} else {
			return false;
		}
	}
	
	function getDetailPresence($id,$company_id, $employee_id){
		
		$this->db->select("a.attendance_id, e.employee_id
							, DATE_FORMAT(a.clock_in, '%Y-%m-%d') as attendance_dt
							, DATE_FORMAT(a.clock_in, '%H:%i:%s') as clock_in
							, DATE_FORMAT(a.clock_out, '%H:%i:%s') as clock_out
							, a.permit_out
							, a.permit_starttime, a.permit_endtime
							, a.long_in, a.lat_in
							, SUBTIME(TIMEDIFF(a.clock_out, a.clock_in), fn_GetPrefference(".$company_id.", 'config_attendance', 'breaktime')) as work_hour
							, e.no_reg, e.employee_name, p.position_name, r.role_name, rs.service_periode
							");
        $this->db->from('tb_m_employee e');
		$this->db->join('tb_r_attendance a', "e.employee_id = a.employee_id", 'left');
        $this->db->join('tb_m_position p', 'e.position_id = p.position_id' , 'inner');
        $this->db->join('tb_m_role r', 'e.role_id = r.role_id' , 'inner');
        $this->db->join('tb_m_role_salary rs', 'e.role_salary_id = rs.role_salary_id' , 'inner');
        $this->db->where("a.attendance_id", $id);
        $this->db->where("e.company_id", $company_id);
		
		
		$this->db->limit(1);
		$query = $this->db->get();
		
		if ($query->num_rows() == 1) {
			return $query->row();
		} else {
			return false;
		}
	}
	
	
	

    function getData($keyword = '', $att_date ='',$company_id ='', $employee_id='') {
        $this->db->select("a.attendance_id, a.employee_id
							, DATE_FORMAT(a.clock_in, '%Y-%m-%d') as attendance_dt
							, DATE_FORMAT(a.clock_in, '%H:%i:%s') as clock_in
							, IFNULL(DATE_FORMAT(a.clock_out, '%H:%i:%s'), '--:--:--') as clock_out
							, SUBTIME(TIMEDIFF(a.clock_out, a.clock_in), fn_GetPrefference(".$company_id.", 'config_attendance', 'breaktime')) as work_hour
							, e.no_reg, e.employee_name, p.position_name, r.role_name, rs.service_periode");
        $this->db->from('tb_r_attendance a');
        $this->db->join('tb_m_employee e', 'a.employee_id = e.employee_id' , 'inner');
        $this->db->join('tb_m_position p', 'e.position_id = p.position_id' , 'inner');
        $this->db->join('tb_m_role r', 'e.role_id = r.role_id' , 'inner');
        $this->db->join('tb_m_role_salary rs', 'e.role_salary_id = rs.role_salary_id' , 'inner');
		
        if ($keyword != ''){
            // $this->db->like('e.employee_name', $keyword);
			// $this->db->or_like('e.no_reg', $keyword);
			$this->db->where("  ( e.employee_name like '%".$keyword."%' or e.no_reg like '%".$keyword."%') ");
        }
		$this->db->where("date_format(clock_in, '%Y-%m-%d') = ", $att_date);
        $this->db->where('e.company_id', $company_id);
        $this->db->order_by('a.clock_in desc');
		
        return $this->db->get()->result();
    }
	
	function countData($keyword = '', $att_date ='',$company_id ='', $employee_id='') {
        $this->db->select('a.attendance_id');
        $this->db->from('tb_r_attendance a');
        $this->db->join('tb_m_employee e', 'a.employee_id = e.employee_id' , 'inner');
        $this->db->join('tb_m_position p', 'e.position_id = p.position_id' , 'inner');
        $this->db->join('tb_m_role r', 'e.role_id = r.role_id' , 'inner');
        $this->db->join('tb_m_role_salary rs', 'e.role_salary_id = rs.role_salary_id' , 'inner');
		
		$this->db->where("date_format(clock_in, '%Y-%m-%d') = ", $att_date);
        $this->db->where('e.company_id', $company_id);
		
		if ($keyword != ''){
            //$this->db->like('e.employee_name', $keyword);
			//$this->db->or_like('e.no_reg', $keyword);
			$this->db->where(" ( e.employee_name like '%".$keyword."%' or e.no_reg like '%".$keyword."%') ");
        }
		
        $this->db->order_by('a.clock_in desc');
		
        return $this->db->count_all_results();
    }
	
	
	function save($attendance_id, $data, $type,$company_id, $employee_id){
		
		if($attendance_id == ""){
			$this->db->insert('tb_r_attendance', $data);
		}else {
			$this->db->set('work_hour', "SUBTIME(TIMEDIFF('".$data['clock_out']."', clock_in) , fn_GetPrefference(".$company_id.", 'config_attendance', 'breaktime'))", FALSE);
			$this->db->where('attendance_id', $attendance_id);
			$this->db->update('tb_r_attendance', $data); 
		}
		return $this->db->affected_rows();
	}
	
	function pause($attendance_id, $data, $status,$company_id, $employee_id){
		if($status == 'Stop' ){
			$this->db->set('permit_out', "ADDTIME( TIMEDIFF('".$data['permit_endtime']."', permit_starttime) , ifnull(permit_out, '00:00:00')) ", FALSE);
			//$this->db->set('permit_out', "TIMEDIFF('".$data['permit_endtime']."', permit_starttime) ", FALSE);
		}
		$this->db->where('attendance_id', $attendance_id);
		$this->db->update('tb_r_attendance', $data);
		return $this->db->affected_rows();
	}
}
