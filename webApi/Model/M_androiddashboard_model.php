<?php 
 /*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-02
 */
class M_androiddashboard_model extends CI_Model{	
	function cek_exsist($table,$where){		
		return $this->db->get_where($table,$where);
	}	
	function get_clock($employee_id='', $company_id='') {
		$sql = "
			select
				DATE_FORMAT(a.clock_in, '%H:%i:%s') as clock_in
			from 
				tb_r_attendance a 
				inner join tb_m_employee e
				on a.employee_id = e.employee_id
			where
				e.company_id = '".$company_id."'
			";

			$sql .=" and date(a.clock_in) >= '".date("Y-m-d")."' and date(a.clock_in) < DATE_ADD('".date("Y-m-d")."', INTERVAL 1 DAY)";
        
			$sql .=" and e.employee_id = '".$employee_id."'";
	
		$query = $this->db->query($sql);
		if ($query->num_rows() == 1) {
			return $query->row()->clock_in;
		} else {
			return $query = '--:--:--';
		}
		;
	}
	function get_travel($is_admin = '',$employee_id = '', $company_id = '')
	{	
		$where = '';
	
		if ($is_admin == '0')
		{
			if ($employee_id != '')
			{
				$where .= " and 
				(
					a.employee_created_by = '" . $employee_id . "'
					or 
					a.employee_assigned_by =  '" . $employee_id . "'				
				)";
			}
		}
	
		$sql = "
			select 
				count(a.travel_id) as cnt
			from
				tb_r_official_travel a
				left join tb_m_employee b on b.employee_id = a.employee_assigned_by
				left join tb_m_employee c on c.employee_id = a.employee_created_by
			where
				b.company_id = '" . $company_id . "'
				" . $where . "
		";
		
		$sql .= " order by a.travel_id, a.dt_from desc";
		
		return $this->db->query($sql)->row()->cnt;
    }
	function get_travel_status($is_admin = '',$employee_id = '', $company_id = '')
	{	
		$where = '';
	
		if ($is_admin == '0')
		{
			if ($employee_id != '')
			{
				$where .= " and 
				(
					a.employee_created_by = '" . $employee_id . "'
					or 
					a.employee_assigned_by =  '" . $employee_id . "'				
				)";
			}
		}
	
		$sql = "
			select 
				count(a.travel_id) as cnt
			from
				tb_r_official_travel a
				left join tb_m_employee b on b.employee_id = a.employee_assigned_by
				left join tb_m_employee c on c.employee_id = a.employee_created_by
			where
				b.company_id = '" . $company_id . "'
				and a.travel_status = '1'
				" . $where . "
		";
		
		$sql .= " order by a.travel_id, a.dt_from desc";
		
		return $this->db->query($sql)->row()->cnt;
    }
	function get_reimburses($is_admin = '', $employee_id = '',$company_id = '') {

        $sql = "SELECT COUNT(r.reimburse_id) as cnt FROM tb_r_reimburse r 
         LEFT JOIN tb_m_employee e1 ON e1.employee_id = r.employee_id
	LEFT JOIN tb_m_employee e2 ON e2.employee_id = r.reimburse_status_by
        LEFT JOIN tb_r_projects p on p.project_id = r.project_id
        WHERE  e1.company_id = '" . $company_id . "'  ";
        if ($is_admin != 1) {
            $sql .= " AND r.employee_id = $employee_id ";
        }
        if ($is_admin != 1) {
            $sql .= " OR ( e1.atasan_id = $employee_id )";
        }
        return $this->db->query($sql)->row()->cnt;
    }
	function get_reimburses_status($is_admin = '', $employee_id = '',$company_id = '') {

        $sql = "SELECT COUNT(r.reimburse_id) as cnt FROM tb_r_reimburse r 
        LEFT JOIN tb_m_employee e1 ON e1.employee_id = r.employee_id
		LEFT JOIN tb_m_employee e2 ON e2.employee_id = r.reimburse_status_by
        LEFT JOIN tb_r_projects p on p.project_id = r.project_id
        WHERE  e1.company_id = '" . $company_id . "'  ";
		$sql .= " AND r.reimburse_status = '1'";
        if ($is_admin != 1) {
            $sql .= " AND r.employee_id = $employee_id ";
        }
        if ($is_admin != 1) {
            $sql .= " OR ( e1.atasan_id = $employee_id )";
        }
		$sql .= " OR ( e1.atasan_id = $employee_id )";
		
        return $this->db->query($sql)->row()->cnt;
    }
	function get_attendances($employee_id='', $company_id='') {
		$sql = "
			select
				count(a.attendance_id) as cnt
			from 
				tb_r_attendance a 
				inner join tb_m_employee e
				on a.employee_id = e.employee_id
			where
				e.company_id = '".$company_id."'
			";
			$sql .=" and date(a.clock_in) >= '".date("Y-m-d")."' and date(a.clock_in) < DATE_ADD('".date("Y-m-d")."', INTERVAL 1 DAY)";

		return $this->db->query($sql)->row()->cnt;
	}
	function get_attendances_status($employee_id='', $company_id='') {
		$sql = "
			select
				count(e.employee_id) as cnt
			from 
				tb_m_employee e
			where
				e.company_id = '".$company_id."'
			";
		return $this->db->query($sql)->row()->cnt;
	}
	function getLeaves($is_admin ='', $employee_id = '',$company_id='') {
        $sql = "
			select 
                count(t.time_off_id) as cnt
                from 
					tb_r_time_off t inner join tb_m_employee e
                on t.employee_id=e.employee_id
                WHERE 1  
		";
        if ($is_admin != 1) {
            $sql .=" and (e.employee_id = '" . $employee_id . "' or e.atasan_id = '" . $employee_id . "')";
        }
		$sql .= " and e.company_id = '" . $company_id . "'";
        return $this->db->query($sql)->row()->cnt;
    }
	function getLeaves_status($is_admin ='', $employee_id = '',$company_id='') {
        $sql = "
			select 
                count(t.time_off_id) as cnt
                from 
					tb_r_time_off t inner join tb_m_employee e
                on t.employee_id=e.employee_id
                WHERE 1  
		";
        if ($is_admin != 1) {
            $sql .=" and (e.employee_id = '" . $employee_id . "' or e.atasan_id = '" . $employee_id . "')";
        }
		$sql .= " and e.company_id = '" . $company_id . "'";
		$sql .= " and t.time_off_status  = '1'";
        return $this->db->query($sql)->row()->cnt;
    }
	function getOvertime($is_admin ='',$employee_id = '',$company_id ='' ) {
		$sql = "
		select 
			count(o.ot_id) as cnt
		from
			tb_r_overtime o 
			inner join tb_m_employee e on  o.employee_id = e.employee_id
		where
			e.company_id = '".$company_id."'
		";
		
		if ($is_admin != 1) {
            $sql .= " AND (o.employee_id = $employee_id or e.atasan_id = $employee_id)";
        }

		
		
		return $this->db->query($sql)->row()->cnt;
	}
	function getOvertime_status($is_admin ='',$employee_id = '',$company_id ='' ) {
		$sql = "
		select 
			count(o.ot_id) as cnt
		from
			tb_r_overtime o 
			inner join tb_m_employee e on  o.employee_id = e.employee_id
		where
			e.company_id = '".$company_id."'
		";
		
		if ($is_admin != 1) {
            $sql .= " AND (o.employee_id = $employee_id or e.atasan_id = $employee_id)";
        }
		$sql .= " and o.ot_status = '1'";
  
		
		
		return $this->db->query($sql)->row()->cnt;
	}
	function getProject($employee_id = '',$company_id ='' ) {
		$sql = "
		select 
			sum(p.task_total) as cnt
		from
			tb_r_projects p 
		where
			p.company_id = '".$company_id."'
		";
		
		
		return $this->db->query($sql)->row()->cnt;
	}
	function getProject_status($employee_id = '',$company_id ='' ) {
		$sql = "
		select 
			sum(p.task_completed) as cnt
		from
			tb_r_projects p 
		where
			p.company_id = '".$company_id."'
		";
		
		return $this->db->query($sql)->row()->cnt;
	}
	function getHoliday($company_id ='' ) {
		$sql = "
		select 
			count(h.holiday_id) as cnt
		from
			tb_m_holiday_cal h 
		where
			h.company_id = '".$company_id."'
		";
		

		
		
		return $this->db->query($sql)->row()->cnt;
	}
	function getHoliday_status($company_id ='' ) {
		$sql = "
		select 
			count(h.holiday_id) as cnt
		from
			tb_m_holiday_cal h 
		where
			h.company_id = '".$company_id."'
			and date(h.holiday_dt) >= '".date("Y-m-d")."' 
		";
		
	
		
		return $this->db->query($sql)->row()->cnt;
	}
}