<?php
/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
define("WEEKDAY_FIRST", 1.5);
define("WEEKDAY_SECOND", 2);

define("HOLIDAY_FIRST", 2);
define("HOLIDAY_SECOND", 3);
define("HOLIDAY_THIRD", 4);

class M_androidovertime_model extends CI_Model
{
	function __construct() {
		parent:: __construct();
		//$this->load->database();
        $this->load->model('M_androidholiday_model');
        $this->load->model('M_androidemployee_model');
        $this->load->helper('date');
        $this->load->helper('number');
	}

	function get_overtimes($start_date = '', $end_date ='', $ot_status = '-', $sv ='', $employee_id = '',$company_id ='', $is_admin ='') {
		$sql = "
		select 
			o.*, e.* 
		from
			tb_r_overtime o 
			inner join tb_m_employee e on  o.employee_id = e.employee_id
		where
			e.company_id = '".$company_id."'
		";
		
		if ($is_admin != 1) {
            $sql .= " AND (o.employee_id = '".$employee_id."' or e.atasan_id = '".$employee_id."')";
        }
		
        if($start_date != '' && $end_date != '') {
            //$sql = $sql->where("ot_dt between '{$start_date}' and '{$end_date}'");
			$sql .= " and o.ot_dt between '" . $start_date . "' and '" . $end_date . "'";
        }

        if($sv != '') {
            //$sql = $sql->like('employee_name', $sv);
			$sql .= " and e.employee_name like '%" . $sv . "%'";
        }

        if($ot_status != '-' && $ot_status != '' ) {
            //$sql = $sql->where('ot_status', $ot_status);
			$sql .= " and o.ot_status = '" . $ot_status . "'";
        }
		
		$sql .= " order by o.ot_dt desc ";
        //$sql = $sql->order_by('ot_dt', 'DESC');
		//echo $sql;
		//die;
		return $this->db->query($sql)->result();
        //return $sql->get()->result();
	}

    function calculate_overtime_pay($employee_id, $ot_calculate,$company_id) {
        $year_of_working = $this->M_androidemployee_model->calculate_year_of_working($employee_id,$company_id);
        $basic_salary = $this->M_androidemployee_model->calculate_basic_salary($employee_id, $year_of_working,$company_id);
        $fixed_allowance = $this->M_androidemployee_model->calculate_fixed_allowance($employee_id,$company_id);

        $wage = $basic_salary + $fixed_allowance;
        return doubleval(($ot_calculate / 173) * $wage);
    }

    private function calculate_total_ot_of($overtimes, $func) {
        return array_reduce($overtimes, function($carry, $item) use($func) {
           return $carry + timepicker_to_hour($func($item));
        });
    }

    function calculate_total_ot_hour($overtimes, $is_all_ot=TRUE) {
        if($is_all_ot) {
            return $this->calculate_total_ot_of(
                $overtimes,
                function($x) { return $x->ot_hour; });
        } else {
            return $this->calculate_total_ot_of(
                $overtimes,
                function($x) { return $x->ot_approve_hour; });
        }
    }

	function get_overtimes_count($start_date = '', $end_date ='', $ot_status = '-', $sv ='', $employee_id = '',$company_id ='', $is_admin ='') {
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
           $sql .= " AND (o.employee_id = '".$employee_id."' or e.atasan_id = '".$employee_id."')";
        }
		
        if($start_date != '' && $end_date != '') {
			$sql .= " and o.ot_dt between '" . $start_date . "' and '" . $end_date . "'";
        }

        if($sv != '') {
			$sql .= " and e.employee_name like '%" . $sv . "%'";
        }

        if($ot_status != '-' && $ot_status != '' ) {
			$sql .= " and o.ot_status = '" . $ot_status . "'";
        }
		
		$sql .= " order by o.ot_dt desc ";
		// echo $sql;
		// die;
		return $this->db->query($sql)->row()->cnt;
	}

    function count_all() {
        return $this->db->count_all_results('tb_r_overtime');
    }

    function get_ot_calculate($ot_dt, $ot_hour,$company_id) {
        if($this->M_AndroidHoliday_model->is_holiday($ot_dt,$company_id)
            || is_weekend($ot_dt)) {
            $first = $ot_hour - 8;
            if($first < 1) {
                return $ot_hour * HOLIDAY_FIRST;
            } else {
                $second = $ot_hour - 9;
                if($second < 1) {
                    return (HOLIDAY_FIRST * 8) + ($first * HOLIDAY_SECOND);
                } else {
                    return (HOLIDAY_FIRST * 8)
                            + HOLIDAY_SECOND
                            + ($second * HOLIDAY_THIRD);
                }
            }
        } else {
            $next_hour = $ot_hour - 1;
            if($next_hour < 1) {
                return $ot_hour * WEEKDAY_FIRST;
            } else {
                return WEEKDAY_FIRST + ($next_hour * WEEKDAY_SECOND);
            }
        }
    }

    function save_overtime(
                $employee_id,
                $project_id,
                $ot_dt,
                $ot_hour,
				$ot_hour_end,
                $ot_description) 
	{
		$ot_hour_end = $ot_hour_end;
		$ot_hour_tot = timepicker_to_hour($ot_hour_end) - timepicker_to_hour($ot_hour) ; 
		$ot_status_by = $this->get_ot_status_by($employee_id);
		
		$data = array(
            'employee_id' => $employee_id,
            'project_id' => $project_id,
            'created_dt' => date("Y-m-d H:i:s"),
            'ot_dt' => $ot_dt,
            'ot_hour' => $ot_hour,
            'ot_hour_end' => $ot_hour_end,
            'ot_description' => $ot_description,
            'ot_calculate' => $this->get_ot_calculate(
                                        $ot_dt,
                                        $ot_hour_tot),
			'ot_status_by' => $ot_status_by->atasan_id
			);

        $this->db->insert('tb_r_overtime', $data);
        return $this->db->insert_id();
    }

    function update_overtime(
                $ot_id,
                $employee_id,
                $project_id,
                $ot_dt,
                $ot_hour,
				$ot_hour_end,
                $ot_description) {
		$ot_hour_end = $ot_hour_end;
		$ot_hour_tot = timepicker_to_hour($ot_hour_end) - timepicker_to_hour($ot_hour) ; 
        $data = array(
            'employee_id' => $employee_id,
            'project_id' => $project_id,
            'ot_dt' => $ot_dt,
            'ot_hour' => $ot_hour,
            'ot_hour_end' => $ot_hour_end,
            'ot_description' => $ot_description,
            'ot_calculate' => $this->get_ot_calculate(
                                        $ot_dt,
                                        timepicker_to_hour($ot_hour_tot))
        );

        $this->db
             ->where('ot_id', $ot_id)
             ->update('tb_r_overtime', $data);

        return $this->db->insert_id();
    }

    function approve($ot_id,
                     $ot_dt,
                     $ot_approve_hour,
                     $ot_reject_reason = "",
					 $ot_approve_hour_end,
					 $employee_id) {
		$ot_approve_hour_end = $ot_approve_hour_end;
		$ot_hour_tot = timepicker_to_hour($ot_approve_hour_end) - timepicker_to_hour($ot_approve_hour) ; 
         $data = array(
             'ot_status_dt' => date("Y-m-d H:i:s"),
             'ot_status' => '1',
             'ot_approve_hour' => $ot_approve_hour,
             'ot_approve_hour_end' => $ot_approve_hour_end,
             'ot_status_by' => $employee_id,
             'ot_approve_calculate' => $this->get_ot_calculate(
                                                $ot_dt,
                                                $ot_hour_tot),
             'ot_reject_reason' => $ot_reject_reason
         );

         $this->db
              ->where('ot_id', $ot_id)
              ->update('tb_r_overtime', $data);

         return $this->db->insert_id();
    }

    function get_ot_status_message($ot_status) {
        switch ($ot_status) {
            case '1':
                return 'Disetujui';
            case '2':
                return 'Ditolak';
            default:
                return 'Menunggu';
        }
    }

    function reject(
        $overtime_id,
        $ot_status_reject,$employee_id) {

        $data = array(
            'ot_status_dt' => date("Y-m-d H:i:s"),
            'ot_status' => '2',
            'ot_status_by' => $employee_id,
            'ot_reject_reason' => $ot_status_reject,
            'ot_approve_hour' => NULL,
            'ot_approve_calculate' => NULL
        );

        $this->db
             ->where('ot_id', $overtime_id)
             ->update('tb_r_overtime', $data);

        return $this->db->insert_id();
    }

    function get_overtime($overtime_id,$company_id) {
        $overtime =
            $this->db
                 ->from('tb_r_overtime as o')
                 ->join('tb_m_employee as e',
                        'o.employee_id = e.employee_id',
                        'inner')
                 ->where(array(
                     'o.ot_id' => $overtime_id,
                     'e.company_id' => $company_id
                 ))
                 ->get()
                 ->row();
        return $overtime;
    }

    function is_ot_exists($employee_id, $ot_dt,$company_id) {
        $ot = $this->db
             ->from('tb_r_overtime as o')
             ->join('tb_m_employee as e',
                     'o.employee_id = e.employee_id',
                     'inner')
             ->where(array(
                 'o.employee_id' => $employee_id,
                 'o.ot_dt' => $ot_dt,
                 'e.company_id' => $company_id
             ))
             ->get()
             ->row();

        return !is_null($ot);
    }

    function delete($ot_id) {
        $this->db->delete('tb_r_overtime', array('ot_id' => $ot_id));
    }
	
	//Added By Yoga 
	function get_email_atasan($employee_id,$company_id) {
        $sql = "select
				a2.employee_name as atasan,
				a2.user_email as email_atasan,
				e.service_periode
			from
				tb_m_employee a
				inner join tb_m_position b on b.position_id = a.position_id
				inner join tb_m_role c on c.role_id = a.role_id
				inner join tb_m_work_unit d on d.work_unit_id = a.work_unit_id
				left join tb_m_role_salary e on e.role_salary_id = a.role_salary_id
                LEFT join tb_m_employee a2 ON a2.employee_id = a.atasan_id
			where
				a.company_id = '" . $company_id . "'
				and a.employee_id = '" . $employee_id . "'";
        return $this->db->query($sql)->row();
    }
	
	function get_overtime_det($ot_id,$company_id) {
        $sql = "select 
				a.ot_id, 
				a.employee_id, 
				a.project_id,
				a.ot_dt, 
				a.ot_hour,
				a.ot_hour_end,
				a.ot_calculate,
				a.ot_description,
				a.ot_status,
				a.ot_reject_reason,
				b.employee_name as employee_name,
				b.user_email as employee_email,
				e.project_name,
				g.employee_name as reject_by,
				f.user_email as email_atasan,
				f.employee_name as nama_atasan
			from 
				tb_r_overtime a
				inner join tb_m_employee b on b.employee_id = a.employee_id
				left join tb_m_employee f on f.employee_id = b.atasan_id
				left join tb_m_employee g on g.employee_id = a.ot_status_by
				left join tb_r_projects e on e.project_id = a.project_id
			where
				b.company_id = '" . $company_id . "'
				and a.ot_id = '" . $ot_id . "'"	;
        return $this->db->query($sql)->row();
    }
	
	function get_ot_status_by($employee_id) // ambil atasan nya	
	{
		$sql = "select atasan_id from tb_m_employee where employee_id = '" . $employee_id . "'";
		return $this->db->query($sql)->row();
	}
}
