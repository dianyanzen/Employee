<?php
/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-01
 */
class M_androidproject_model extends CI_Model
{
	function __construct()
	{
		parent:: __construct();
	}
	
	function get_all_employee($project_id, $fields = '*',$company_id) {
        $sql = "select " . $fields . " from tb_m_employee where 
		company_id ='" . $company_id . "' 
		and employee_id not in (select employee_id from tb_r_project_members where project_id = '" . $project_id . "')
		order by employee_name
		";
        return $this->db->query($sql)->result();
    }

    /*
    * @author: yanzen@arkamaya.co.id
	* @created: 2017-10-01
    */
    function get_projects_by_name($name,$company_id) {
        return $this->db
                    ->select('project_id, project_name')
                    ->from('tb_r_projects')
                    ->where('company_id', $company_id)
                    ->like('project_name', $name)
                    ->get()
                    ->result();
    }

    /*
    * @author: yanzen@arkamaya.co.id
    * @created: 2017-10-01
    */
    function get_all_projects($company_id) {
        $sql = "
                select
			a.project_id,
			a.company_id,
			a.customer_id,
			a.project_name,
			a.project_description,
			a.project_owner_id,
			a.project_start_date,
			a.project_due_date,
			a.project_status,
			a.project_status_color,
			a.task_total,
			a.task_completed,
			b.customer_name,
			c.employee_name
		from
			tb_r_projects a
			left join tb_m_customer b on b.customer_id = a.customer_id
			left join tb_m_employee c on c.employee_id = a.project_owner_id

		where  a.company_id = '" . $company_id . "'
		order by a.project_id desc
	";

        return $this->db->query($sql)->result();
    }

	function get_projects($sv = '',$company_id)
	{
		$sql = "
			select
				a.project_id,
				a.company_id,
				a.customer_id,
				a.project_name,
				a.project_description,
				a.project_owner_id,
				a.project_start_date,
				a.project_due_date,
				a.project_status,
				a.project_status_color,
				a.task_total,
				a.task_completed,
				b.customer_name,
				c.employee_name,
				d.status_name as status
			from
				tb_r_projects a
				left join tb_m_customer b on b.customer_id = a.customer_id
				left join tb_m_employee c on c.employee_id = a.project_owner_id
				left join tb_r_projects_status d on d.project_status_id = a.project_status AND d.company_id = '" . $company_id . "'
			
			where
				a.company_id = '" . $company_id . "'
				and (a.project_name like '%" . $sv . "%' or a.project_description like '%" . $sv . "%' or b.customer_name like '%" . $sv . "%')
			order by a.project_name
		";


		return $this->db->query($sql)->result();
		
		
    }

	function get_projects_count($sv = '',$company_id)
	{
		$sql = "
			select
				count(a.project_id) as cnt
			from
				tb_r_projects a
				left join tb_m_customer b on b.customer_id = a.customer_id
				left join tb_m_employee c on c.employee_id = a.project_owner_id
				left join tb_r_projects_status d on d.project_status_id = a.project_status AND d.company_id = '" . $company_id . "'
			where
				a.company_id = '" . $company_id . "'
				and (a.project_name like '%" . $sv . "%' or a.project_description like '%" . $sv . "%' or b.customer_name like '%" . $sv . "%')
		";

		$sql .= " order by project_name";

		return $this->db->query($sql)->row()->cnt;
    }

	function get_project($project_id,$company_id)
	{

		$sql = "
			select
				a.project_id,
				a.company_id,
				a.customer_id,
				a.project_name,
				a.project_description,
				a.project_owner_id,
				a.project_due_date,
				a.project_status,
				a.project_status_color,
				a.task_total,
				a.task_completed,
				b.customer_name,
                c.employee_id,
				c.employee_name,
				d.status_name as status
			from
				tb_r_projects a
				left join tb_m_customer b on b.customer_id = a.customer_id
				left join tb_m_employee c on c.employee_id = a.project_owner_id
				left join tb_r_projects_status d on d.project_status_id = a.project_status AND d.company_id = '" . $company_id . "'
			where
				a.company_id = '" . $company_id . "'
				and a.project_id = '" . $project_id . "'
		";
		// echo $sql;
		return $this->db->query($sql)->row();
    }

	function delete($project_id)
	{
		// delete header
		$this->db->where('project_id', $project_id);
		$this->db->delete('tb_r_projects');
	}

	/*
	function save_project($company_id
						,$customer_id
						,$customer_id
						,$project_name
						,$project_description
						,$project_owner_id
						,$project_start_date
						,$project_due_date
						,$project_status
						,$project_status_color
						,$project_id
						,$employee_name
						)
	{
		$project_h = array
		(
                        'company_id' => $company_id
                        , 'customer_id' => $customer_id
			, 'project_name' => $project_name
			, 'project_description' => $project_description
			, 'project_owner_id' => $project_owner_id
			, 'project_start_date' => date('Y-m-d', strtotime( str_replace('/', '-', $project_start_date)))
			, 'project_due_date' => date('Y-m-d', strtotime( str_replace('/', '-', $project_due_date)))
			, 'project_status' => $project_status
			, 'project_status_color' => $project_status_color
        );

        if
		(
			$project_id == '' ||
			$project_id == null
		)
		{
			$project_h['created_by'] = $employee_name;
			$project_h['created_dt'] = date('Y-m-d H:i:s');

			$this->db->insert('tb_r_projects', $project_h);
			//  $this->insert_details($this->db->insert_id());
			$project_id = $this->db->insert_id();
		}
		else
		{

                        $project_h['changed_by'] = $employee_name;
                        $project_h['changed_dt'] = date('Y-m-d H:i:s');

            // update header
			$this->db->where('project_id', $project_id);
			$this->db->update('tb_r_projects', $project_h);


        }
        return $project_id;
	}

	*/

	function get_members($project_id,$company_id,$is_admin)
	{

		$sql = "
				SELECT
					m.project_id,
					m.employee_id,
					m.role,
					m.activity_id,
					m.activity_val,
					m.priority_id,
					m.priority_val,
					m.active,
					m.joined_dt,
					e.employee_name,
					r.member_role_cd,
					r.member_role_nm
				FROM
					tb_r_project_members m
					LEFT JOIN tb_m_employee e ON m.employee_id = e.employee_id
					LEFT JOIN tb_r_project_member_roles r ON m.role = r.id
				WHERE
					e.company_id = '" . $company_id . "'
					AND m.project_id = '" . $project_id . "'
				ORDER BY e.employee_name
		";

		
		if($is_admin == 1)
		{
			$sql .="";
		}
		else
		{
			
			$role = $this->get_role_value($project_id);
			// jika Project Manager
			if($role == "PM")
			{
				$sql .="";
			}
			// jika Analyst
			elseif ($role == "AN") 
			{
				$sql .=" AND r.member_role_cd != 'PM'";
			}
			// jika Team Leader
			elseif ($role == "TL") 
			{
				$sql .=" AND (r.member_role_cd != 'AN' AND r.member_role_cd != 'PM')";
			}
			// jika Progremmer 
			elseif ($role == "PG")
			{
				$sql .=" AND r.member_role_cd = 'PG'";
			}
			// Technical Writter
			else
			{
				 $sql .=" AND r.member_role_cd = 'TW'";
			}
		}
		

		return $this->db->query($sql)->result();

	}

	function get_project_member($project_id,$company_id)
	{
	   $sql = "select
					a.milestone_id,
					a.project_id,
					b.employee_id,
					c.employee_name
					from tb_r_milestones a
					left join tb_r_project_members b ON a.project_id = b.project_id
					left join tb_m_employee c ON b.employee_id = c.employee_id
					where a.project_id = ".$project_id." and c.company_id = ".$company_id;
		return $this->db->query($sql)->result();
	}


	function get_total_members($project_id)
	{
		$sql = "select employee_id from tb_r_project_members where project_id =".$project_id;
		$query = $this->db->query($sql);
		return $query->num_rows();
	}

	function get_roles($company_id)
	{
		$sql = "SELECT 
					id, 
					member_role_cd, 
					member_role_nm 
				FROM tb_r_project_member_roles
				WHERE company_id = ".$company_id;
		return $this->db->query($sql)->result();
	}
	
	//Added By Yoga
	function get_project_status($company_id)
	{
		$sql = "SELECT project_status_id as code,status_name as project_status 
				FROM tb_r_projects_status where company_id = ".$company_id;
		return $this->db->query($sql)->result();
	}
	//end
	 function get_role_value($project_id,$company_id,$user_name)
	 {
		$role = $this->db->query("
			SELECT 
				b.member_role_cd 
			FROM
				tb_r_project_members a
			LEFT JOIN tb_r_project_member_roles b ON a.role = b.id 
			LEFT JOIN tb_m_employee c ON a.employee_id = c.employee_id
			WHERE 
				a.project_id = ".$project_id."
				AND c.company_id = ".$company_id."
				AND c.user_email = '".$user_name."'
		");
		if(empty($role)){
			return 0;
		}else{
			return $role->row('member_role_cd');
		}
	}
	
	
}
