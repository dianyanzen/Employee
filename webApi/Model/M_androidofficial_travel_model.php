<?php
/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
class M_androidofficial_travel_model extends CI_Model 
{
	function __construct()
	{
		parent:: __construct();
	}
	
	function get_official_travels($sv = '', $s_dt_from = '', $s_dt_to = '', $s_project_id = '', $s_travel_status = '' ,$is_admin = '', $employee_id = '', $company_id = '')
	{
		$where = '';
		$where .= ($s_dt_to != '' && $s_dt_from != '') ? " and dt_to between '" . $s_dt_from . "' and '" . $s_dt_to . "'" : '';
		$where .= ($s_project_id != '') ? " and a.project_id in (" . implode(',', $s_project_id) . ")" : '';
		$where .= ($s_travel_status != '') ? " and a.travel_status = '" . $s_travel_status . "'" : '';
	
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
				a.travel_id, 
				a.employee_assigned_by, 
				a.employee_created_by,
				a.dt_from, 
				a.dt_to,
				a.project_id,
				Replace(a.destination,'\r\n','\n') as destination,
				a.description,
				a.need_approval,
				a.travel_status,
				a.travel_status_by,
				a.travel_status_dt,
				a.travel_reject_reason,
				b.employee_name as assigned_name,
				c.employee_name as created_name,
				count(d.employee_id) as members,
				e.project_name
			from 
				tb_r_official_travel a
				left join tb_m_employee b on b.employee_id = a.employee_assigned_by
				left join tb_m_employee c on c.employee_id = a.employee_created_by
				left join tb_r_official_travel_member d on d.travel_id = a.travel_id
				left join tb_r_projects e on e.project_id = a.project_id
			where
				b.company_id = '" . $company_id . "'
				and (
					a.destination like '%" . $sv . "%' 
					or a.description like '%" . $sv . "%'
					or b.employee_name like '%" . $sv . "%'
					or c.employee_name like '%" . $sv . "%'
				)
				" . $where . "
			group by a.travel_id
			order by a.dt_from desc
		";
		/*
		if ($start != '' && $length != '')
		{
			$sql .= " limit " . $start . ", " . $length;
		}
		*/
		//secho $company_id."<br>";
		//echo $sql."<br>";
		//$results = array('1' => $sv, '2' => $s_dt_from,'3' => $s_dt_to,'4' => $s_project_id,'5' => $s_travel_status,'6' => $is_admin,'7' => $employee_id,'8' => $company_id);
		//print_r($results);
		//echo "<br>";
		return $this->db->query($sql)->result();
    }
	
	function get_official_travels_count($sv = '', $s_dt_from = '', $s_dt_to = '', $s_project_id = '', $s_travel_status = '',$is_admin = '',$employee_id = '', $company_id = '')
	{	
		$where = '';
		$where .= ($s_dt_to != '' && $s_dt_from != '') ? " and dt_to between '" . $s_dt_from . "' and '" . $s_dt_to . "'" : '';
		$where .= ($s_project_id != '') ? " and a.project_id in (" . implode(',', $s_project_id) . ")" : '';
		$where .= ($s_travel_status != '') ? " and a.travel_status = '" . $s_travel_status . "'" : '';
	
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
				and (
					a.destination like '%" . $sv . "%' 
					or a.description like '%" . $sv . "%'
					or b.employee_name like '%" . $sv . "%'
					or c.employee_name like '%" . $sv . "%'
				)
				" . $where . "
		";
		
		$sql .= " order by a.travel_id, a.dt_from desc";
		
		return $this->db->query($sql)->row()->cnt;
    }
	
	function get_official_travel($travel_id,$company_id)
	{	
			
		$sql = "
			select 
				a.travel_id, 
				a.employee_assigned_by, 
				a.employee_created_by,
				a.dt_from, 
				a.dt_to,
				a.project_id,
				a.destination,
				a.description,
				a.need_approval,
				a.travel_status,
				a.travel_status_by,
				a.travel_status_dt,
				a.travel_reject_reason,
				b.employee_name as assigned_name,
				c.employee_name as created_name,
				f.employee_name as travel_status_by_name,
				count(d.employee_id) as members,
				e.project_name,
				b.user_email as user_email_assigned_by,
				c.user_email as user_email_created_by,
				f.user_email as user_email_travel_status_by				
			from 
				tb_r_official_travel a
				inner join tb_m_employee b on b.employee_id = a.employee_assigned_by
				left join tb_m_employee c on c.employee_id = a.employee_created_by
				left join tb_r_official_travel_member d on d.travel_id = a.travel_id
				left join tb_r_projects e on e.project_id = a.project_id
				left join tb_m_employee f on f.employee_id = a.travel_status_by
			where
				b.company_id = '" . $company_id . "'
				and a.travel_id = '" . $travel_id . "'
		";		
		
		return $this->db->query($sql)->result();
    }
	
	function get_official_travel_members($travel_id)
	{
		$sql = "
			select 
				a.*,
				b.employee_name,
				b.no_reg
			from
				tb_r_official_travel_member a
				inner join tb_m_employee b on b.employee_id = a.employee_id
			where
				a.travel_id = '" . $travel_id . "'
			order by b.employee_name
		";
		return $this->db->query($sql)->result();
	}
	
	function delete($travel_id)
	{	
		// delete members
		$this->db->where('travel_id', $travel_id);		
		$this->db->delete('tb_r_official_travel_member');
	
		// delete header
		$this->db->where('travel_id', $travel_id);		
		$this->db->delete('tb_r_official_travel');
	}
	
	function save_official_travel($dt_from,$dt_to,$employee_name,$project_id,$get_travel_id='',$employee_id,$destination)
	{
		//$dt_from_to		= $this->input->post('dt_from_to', true);
		//$dtft 			= explode(' - ', $dt_from_to);
		$dt_from		= $dt_from;
		$dt_to			= $dt_to;
		$assg_by		= $employee_id;
		
		$official_travel_h = array
		(
            'employee_assigned_by' => $assg_by
            , 'employee_created_by' => $assg_by
			, 'dt_from' => date('Y-m-d', strtotime( str_replace('/', '-', $dt_from)))
			, 'dt_to' => date('Y-m-d', strtotime( str_replace('/', '-', $dt_to)))
			, 'project_id' => $project_id
			, 'destination' => $destination
			// , 'description' => $this->input->post('parent', true)
			// , 'need_approval' => $this->input->post('parent', true)			
        );
		
		$travel_id = 0;

        if 
		(
			$get_travel_id == '' || 
			$get_travel_id == null
		)
		{
            $official_travel_h['created_by'] = $employee_name;
            $official_travel_h['created_dt'] = date('Y-m-d H:i:s');
			//print_r($official_travel_h);
			//die;
            $this->db->insert('tb_r_official_travel', $official_travel_h);
			$travel_id = $this->db->insert_id();
			$this->insert_details($travel_id,$employee_id);
        }
		else 
		{
			$travel_id = $get_travel_id;
			
            $official_travel_h['changed_by'] = $employee_name;
            $official_travel_h['changed_dt'] = date('Y-m-d H:i:s');

            // update header
			$this->db->where('travel_id', $travel_id);
			$this->db->update('tb_r_official_travel', $official_travel_h);	

			// delete members
			$this->db->where('travel_id', $travel_id);		
			$this->db->delete('tb_r_official_travel_member');			
			
			// insert again
			$this->insert_details($travel_id,$employee_id);
        }
        return $travel_id;
	}
	
	function insert_details($travel_id,$employee_id)
	{
		//foreach (json_decode($this->input->post('items', true)) as $d)
		//{
			$this->db->query("
				insert into tb_r_official_travel_member
				(
					travel_id
					, employee_id					
				)
				values 
				(
					'" . $travel_id . "'
					, '" . $employee_id . "'
				)			
			");
		//}
	}
	
	function get_assignedby($employee_id)
	{
		$sql = "select e.atasan_id, a.employee_name as atasan_name
				 from tb_m_employee e 
				left join tb_m_employee a on a.employee_id = e.atasan_id
				where 
				e.employee_id = '".$employee_id."'";
		return $this->db->query($sql)->row();
		
	}
	
	function approval($travel_id,$employee_id,$travel_status,$travel_reject_reason)
	{		
		$sql = "
			update 
				tb_r_official_travel 
			set 
				travel_status = '" . $travel_status . "'
				, travel_status_by = '" . $employee_id . "'
				, travel_status_dt = NOW()
				, travel_reject_reason = '" . $travel_reject_reason . "'
			where
				travel_id = '" . $travel_id . "'
		";
		$this->db->query($sql);
	}
	
	//added by yoga
	function get_email_members($travel_id)
	{
		$sql = "
			select 
				group_concat(user_email) as email
			from
				tb_r_official_travel_member a
				inner join tb_m_employee b on b.employee_id = a.employee_id
			where
				a.travel_id = '" . $travel_id . "'
			order by b.employee_name
		";
		return $this->db->query($sql)->row()->email;
	}
}
?>