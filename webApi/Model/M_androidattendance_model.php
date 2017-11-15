<?php
/*
* @author: muhamad.deden@arkamaya.co.id
* @created: 2017-03-09
*/
class M_androidattendance_model extends CI_Model
{
	function __construct() {
		parent:: __construct();
        $this->load->model('M_androidholiday_model');
        $this->load->helper('date');
        $this->load->helper('number');
	}

	function get_attendances($sv = ''
                            ,$start_date = ''
                            ,$end_date = ''
                            ,$employees = ''
							,$employee_id=''
							,$company_id='') {

        		
        $sql = "
			select
				a.*, e.* 
			from 
				tb_r_attendance a 
				inner join tb_m_employee e
				on a.employee_id = e.employee_id
			where
				e.company_id = '".$company_id."'
			";

        if($start_date != '' && $end_date != '') {
            //$sql = $sql->where("a.clock_in between '{$start_date} 0:0:0' and '{$end_date} 23:59:0'");
			//$sql .=" and a.clock_in betwen '".$start_date."' and '".$end_date."'";
			$sql .="and date(a.clock_in) >= '".$start_date."' and date(a.clock_in) < DATE_ADD('".$end_date."', INTERVAL 1 DAY)";
        }

        if($sv != '') {
            //$sql = $sql->like('e.employee_name', $sv);
			$sql .=" and e.employee_name like '%".$sv."'%";
        }

        if($employees != '') {
            //$sql = $sql->where_in('e.employee_id', $employees);
			$sql .=" and e.employee_id = '".$employees."'";
        }

        // if($is_admin != 1) {
            // $sql = $sql->where("e.employee_id", $employee_id);
        // }

        // $sql = $sql->order_by('attendance_id');
		$sql .=" order by a.clock_in desc, a.attendance_id";
		
        // return $sql->get()->result();
		return $this->db->query($sql)->result();
	}

    function get_attendances_count($sv = ''
                                   ,$start_date = ''
                                   ,$end_date = ''
                                   ,$employees = ''
								   ,$employee_id=''
								   , $company_id='') {
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

        if($start_date != '' && $end_date != '') {
            //$sql = $sql->where("a.clock_in between '{$start_date} 0:0:0' and '{$end_date} 23:59:0'");
		$sql .="and date(a.clock_in) >= '".$start_date."' and date(a.clock_in) < DATE_ADD('".$end_date."', INTERVAL 1 DAY)";
        }

        if($sv != '') {
            //$sql = $sql->like('e.employee_name', $sv);
			$sql .=" and e.employee_name like '%".$sv."'%";
        }

        if($employees != '') {
            //$sql = $sql->where_in('e.employee_id', $employees);
			$sql .=" and e.employee_id = '".$employees."'";
        }

		return $this->db->query($sql)->row()->cnt;
	}
	
	function get_hour_attendance($system_code='',$company_id =''){
		 // $sql = $this->db
                    // ->select('system_value_txt as hour')
                    // ->from('tb_m_company_prefferences ')
                    // ->where('system_code="$system_code" ');
                    
		// return $sql->get()->row()->hour;
		  $sql = " select
                    system_value_txt as hour
                    from tb_m_company_prefferences 
					where system_code = '" . $system_code . "'
					and company_id = '" . $company_id . "'
        ";
        return $this->db->query($sql)->row()->hour;
    // }
	}

}
