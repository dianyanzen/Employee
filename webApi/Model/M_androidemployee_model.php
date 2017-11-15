<?php

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-10-1
 */

class M_androidemployee_model extends CI_Model {

    function __construct() {
        parent:: __construct();
    }

    function get_all_employee($fields = '*',$company_id) {
        $sql = "select " . $fields . " from tb_m_employee where company_id ='" . $company_id . "' order by employee_name";
        return $this->db->query($sql)->result();
    }

    //edit by nanin mulyani 2017-03-06
    function check_work_email($invite_new_email) {
        $post_work_email = $invite_new_email;
        
		$sql = "
			select user_email from tb_m_users where user_email = '" . $post_work_email . "'
		";
        $quer = $this->db->query($sql);
		return $quer->num_rows();

        // if ($quer->num_rows() > 0) {
        //     return $quer->row();
        // } else {
        //     return false;
        // }
    }

    function check_email_noreg_edit($employee_id) {
        $sql = "select 
					user_email, work_email, no_reg 
				from 
					tb_m_employee 
				where 
					employee_id = '" . $employee_id . "'
		";
        $quer = $this->db->query($sql);

        if ($quer->num_rows() > 0) {
            return $quer->row();
        } else {
            return false;
        }
    }

    function check_no_reg($no_reg,$company_id) {
        $post_no_reg = $no_reg;
        $comp_id = $company_id;
        $sql = "select * from tb_m_employee where no_reg = '$post_no_reg' and company_id = '$comp_id'";
        $quer = $this->db->query($sql);

        if ($quer->num_rows() > 0) {
            return $quer->row();
        } else {
            return false;
        }
    }

    function check_comfirmend_code($code) {
        $this->db->select('e.no_reg, e.user_email, e.work_email, e.email_confirmed_code, e.is_active');
        $this->db->from('tb_m_employee e');
        $this->db->where('e.email_confirmed_code', $code);
        $this->db->where('e.email_confirmed', 0);
        $query = $this->db->get();
        if ($query->num_rows() == 1) {
            return $query->row();
        } else {
            return false;
        }
    }

    function update_email_confirmed($activation_code, $sts) {
        $this->db->where('email_confirmed_code', $activation_code);
        $this->db->update('tb_m_employee', array('email_confirmed' => $sts));
        return $this->db->affected_rows();
    }

    function update_comfirm_email($employee_id) {
        $this->db->where('employee_id', $employee_id);
        $this->db->update('tb_m_employee', array('email_confirmed' => 0));
        return $this->db->affected_rows();
    }

    //end edit

    function get_employees(
    $employee_name = ''
    , $positions = ''
    , $roles = ''
    , $employee_status = ''
    , $show_inactive = 'false'
    , $start = ''
    , $length = ''
    , $id = ''
	, $company_id =''
    ) {

        $where = '';
        $where .= ($employee_name != '') ? " and a.employee_name like '%" . $employee_name . "%'" : '';
        $where .= ($positions != '') ? " and a.position_id in (" . implode(',', $positions) . ")" : '';
        $where .= ($roles != '') ? " and a.role_id in (" . implode(',', $roles) . ")" : '';
        $where .= ($employee_status != '') ? " and a.status in ('" . implode("','", $employee_status) . "')" : '';
        $where .= ($show_inactive == 'true') ? '' : " and a.is_active = '1'";
        $where .= ($id == '') ? '' : " and a.employee_id not in ($id)";
       
        $sql = "
			select
				a.employee_id,
				a.employee_name,
				a.start_working_dt,
				a.handphone1,
				a.handphone2,
				a.photo,
				a.is_active,
				b.position_name,
				c.role_name,
				a.user_email
			from
				tb_m_employee a
				inner join tb_m_position b on b.position_id = a.position_id
				inner join tb_m_role c on c.role_id = a.role_id
			where
				a.company_id = '" . $company_id . "'
		";

        $sql .= $where;
		
        $sql .= " order by a.employee_name ";

        if (($start != '' || $start == '0') && ($length != '' || $length > 0)) {
            $sql .= " limit " . $start . ", " . $length;
        }

        return $this->db->query($sql)->result();
    }

    function get_employees_count($employee_name = '', $positions = '', $roles = '', $employee_status = '', $show_inactive = 'false',$company_id = '') {

        $where = '';
        $where .= ($employee_name != '') ? " and a.employee_name like '%" . $employee_name . "%'" : '';
        $where .= ($positions != '') ? " and a.position_id in (" . implode(',', $positions) . ")" : '';
        $where .= ($roles != '') ? " and a.role_id in (" . implode(',', $roles) . ")" : '';
        $where .= ($employee_status != '') ? " and a.status in ('" . implode("','", $employee_status) . "')" : '';
        $where .= ($show_inactive == 'true') ? '' : " and a.is_active = '1'";

        $sql = "
			select
				count(a.employee_id) as cnt
			from
				tb_m_employee a
				inner join tb_m_position b on b.position_id = a.position_id
				inner join tb_m_role c on c.role_id = a.role_id
			where
				a.company_id = '" . $company_id . "'
		";

        $sql .= $where;
        $sql .= " order by a.employee_name";

        return $this->db->query($sql)->row()->cnt;
    }

    function get_employee($employee_id,$company_id) {

        $sql = "
			select
				a.*,
				b.position_name,
				c.role_name,
				d.name as work_unit_name,
                a2.employee_name as atasan,
				CASE WHEN a.boleh_cuti = 1 THEN 'Boleh' ELSE 'Tidak Boleh' END as cuti,
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
				and a.employee_id = '" . $employee_id . "'
		";



        return $this->db->query($sql)->result();
    }
    
    function get_employe_id($user_name)
    {
        $sql = "SELECT employee_id FROM tb_m_employee WHERE user_email = '".$user_name."'";
        return $this->db->query($sql)->row()->employee_id;
    }
    function get_atasan($employee_id = '',$company_id ='') {
		//edited by nanin mulyani 20170405
        //$employee_id = $this->get_employe_id();
        $sql = "select employee_id, employee_name from tb_m_employee WHERE company_id = '".$company_id."' ";
        if ($employee_id != "") {
			//edited by nanin mulyani 20170405
            //$sql .= " AND employee_id <> $employee_id AND atasan_id <> $employee_id ";
			$sql .= " AND (employee_id <> $employee_id) ";
        }
        return $this->db->query($sql)->result();
    }
	
	//added by nanin mulyani 20170405
	function get_email_hak_akses($company_id){
		$sql = "
			select 
				users.user_email as email_val, users.user_email, users.user_group_id, users.company_id, users.full_name
				, groups.user_group_description
			from tb_m_users as users 
			inner join tb_m_user_group groups on groups.user_group_id = users.user_group_id
			where users.company_id = '" . $company_id . "'
			and user_email not in (select distinct coalesce(user_email,0) from tb_m_employee where company_id = '" . $company_id . "')
		";
		return $this->db->query($sql)->result();
	}
	
	function get_jatah_cuti($employee_id,$company_id){
		$sql = "
			select 
				((select com_preff.system_value_num from tb_m_company_prefferences as com_preff
				where com_preff.system_code = 'jatah_cuti' and com_preff.company_id = '" . $company_id . "') - 
				(select sum(time_off.time_off_days) as time_off_days 
				from tb_r_time_off as time_off where time_off.employee_id = '$employee_id')) as jatah_cuti
		";
		return $this->db->query($sql)->row()->jatah_cuti;
	}
	//end

    /*
     * @author: dian.septian@arkamaya.co.id
     * @created: 2017-02-17
     */

    function calculate_fixed_allowance($employee_id,$company_id) {
        $sql = 'select sum(position_item_amount) position_salary
                 from tb_m_position_items p
                 join tb_m_employee e
                 on  e.position_id = p.position_id
             where employee_id = ?
                   and company_id = ?';

        return
                doubleval(
                $this->db
                        ->query(
                                $sql, array($employee_id, $company_id))
                        ->row()
                ->position_salary);
    }

    /*
     * @author: dian.septian@arkamaya.co.id
     * @created: 2017-02-17
     */

    function calculate_basic_salary($employee_id, $year_of_working,$company_id) {
        $first_role_salary_sql = '
            select service_periode, basic_salary
                from tb_m_role_salary r
                join tb_m_employee e
                on r.role_id = e.role_id
            where e.employee_id = ?
                and e.company_id = ?
            order by service_periode
            limit 0,1';

        $first_role_salary = $this->db
                ->query(
                        $first_role_salary_sql, array(
                    $employee_id,
                    $company_id))
                ->row();
        if (empty($first_role_salary))
            return 0;
        if ($first_role_salary->service_periode > $year_of_working) {
            return doubleval($first_role_salary->basic_salary);
        } else {
            $sql = 'select basic_salary
                 from tb_m_role_salary r
                 join tb_m_employee e
                 on r.role_id = e.role_id
                 where e.employee_id = ?
                     and e.company_id = ?
                     and service_periode <= ?
                 order by service_periode
                 desc
                 limit 0,1';

            return doubleval(
                    $this->db
                            ->query(
                                    $sql, array(
                                $employee_id,
                                $company_id,
                                $year_of_working))
                            ->row()
                    ->basic_salary);
        }
    }

    function calculate_year_of_working($employee_id,$company_id) {
        $sql = 'SELECT TIMESTAMPDIFF(YEAR, start_working_dt, NOW()) AS year_of_working
             FROM tb_m_employee
             where employee_id = ?
                   and company_id = ?';

        return doubleval(
                $this->db
                        ->query(
                                $sql, array($employee_id, $company_id))
                        ->row()
                ->year_of_working);
    }

    function get_employee_family($employee_id) {
        $sql = "
			select
				relationship
				, gender
				, fullname
				, born_dt
				, born_place
			from
				tb_m_family
			where
				employee_id = '" . $employee_id . "'
		";
        return $this->db->query($sql)->result();
    }

    function get_employee_education($employee_id) {
        $sql = "
			select
				level
				, institution
				, faculty
				, graduated_dt
				, gpa
			from
				tb_m_education
			where
				employee_id = '" . $employee_id . "'
			order by
				education_id
		";
        return $this->db->query($sql)->result();
    }

    function get_employee_competency($employee_id) {
        $sql = "
			select
				a.*,
				b.competency_name
			from
				tb_m_employee_competency a
			inner join tb_m_competency b on b.competency_id = a.competency_id
			where
				employee_id = '" . $employee_id . "'
			order by
				b.competency_name
		";
        return $this->db->query($sql)->result();
    }

    function get_employee_projects($employee_id) {
        $sql = "
			select
				a.project_id,
				b.project_name,
				b.customer_id,
				c.customer_name,
				a.role
			from
				tb_r_project_members a
				inner join tb_r_projects b on b.project_id = a.project_id
				inner join tb_m_customer c on c.customer_id = b.customer_id
			where
				a.employee_id = '" . $employee_id . "'
			order by
				b.project_name
		";
        return $this->db->query($sql)->result();
    }

    function get_basic_salary($role_id, $service_periode) {
        $sql = "
			select
				basic_salary
			from
				tb_m_role_salary
			where
				role_id = '" . $role_id . "'
				and service_periode >= " . $service_periode . "
			order by service_periode limit 1
		";
        // echo $sql;
        $res = $this->db->query($sql)->result();
        return (count($res) > 0) ? $res[0]->basic_salary : 0;
    }
	
	function get_basic_salary_by_step($role_salary_id) {
        $sql = "
			select
				basic_salary
			from
				tb_m_role_salary
			where
				role_salary_id = '" . $role_salary_id . "'
		";
        $res = $this->db->query($sql)->result();
        return (count($res) > 0) ? $res[0]->basic_salary : 0;
    }

    function get_position_items($position_id) {
        $sql = "
			select
				position_item_name,
				position_item_amount
			from
				tb_m_position_items
			where
				position_id = '" . $position_id . "'
			order by position_item_name
		";
        return $this->db->query($sql)->result();
    }
	
	function get_role_salary($role_id)
	{
		$sql = "
			select
				role_salary_id, service_periode, basic_salary
			from
				tb_m_role_salary
			where
				role_id = '" . $role_id . "'
			order by service_periode
		";
		return $this->db->query($sql)->result();
	}

    function get_employee_payroll($employee_id, $type) {
        return $this->db->query("
			select
				name,
				amount from
			tb_m_employee_payroll
			where
				employee_id = '" . $employee_id . "' and `type` IN (" . $type . ")
			order by name
		")->result();
    }

    function do_upload($photo) {
        $nama_filenya = $photo;
        if ($_FILES['file_photo']['name'] != '') {
            $temp_file_lama = $nama_filenya;
            if ($temp_file_lama != '') {
                $filep = 'zrcs/default/assets/images/users/' . $temp_file_lama;
                if (file_exists($filep)) {
                    unlink($filep);
                }

                $filep = 'zrcs/default/assets/images/users/thumbs/' . $temp_file_lama;
                if (file_exists($filep)) {
                    unlink($filep);
                }
            }
            $config['upload_path'] = './zrcs/default/assets/images/users';
            $config['allowed_types'] = 'jpg|jpeg|gif|png';
            $config['max_size'] = '20000';
            $config['remove_spaces'] = TRUE;

            $this->load->library('upload', $config);

            $this->upload->do_upload('file_photo');
            // $data = $this->upload->data('file_photo');
            $file_data = $this->upload->data();

            $nama_filenya = $file_data['file_name'];

            $thumbs = array(
                'source_image' => $file_data['full_path'],
                'new_image' => './zrcs/default/assets/images/users/thumbs',
                'maintain_ration' => true,
                'width' => 256,
                'height' => 256
            );

            $this->load->library('image_lib');
            $this->image_lib->initialize($thumbs);

            $this->image_lib->resize();

            // hapus file lama
        }
        return $nama_filenya;
    }

    /*
     * @author: dian.septian@arkamaya.co.id
     * @created: 2017-03-14
     */

    function change_password($id, $old_password, $new_password) {
        if ($this->is_password_matched($id, $old_password)) {
            $this->db->set('user_password', md5($new_password))
                    ->set('is_active', 1)
                    ->where('user_password', md5($old_password))
                    ->where('employee_id', $id)
                    ->update('tb_m_employee');
            return true;
        }
        return false;
    }


    private function is_password_matched($id, $old_password) {
        $emp = $this->db
                ->from('tb_m_employee')
                ->where(array(
                    'employee_id' => $id,
                    'user_password' => md5($old_password)
                ))
                ->get()
                ->row();
        return !empty($emp);
    }

    function save_employee($employee_id = ''
							,$company_id = ''
							,$work_email = ''
							,$invite_new_email =''
							,$atasan_id = ''
							,$position_id = ''
							,$role_id = ''
							,$role_salary_id = ''
							,$no_reg = ''
							,$employee_name = ''
							,$start_working_dt = ''
							,$born_place = ''
							,$born_dt = ''
							,$address = ''
							,$handphone1 = ''
							,$handphone2 = ''
							,$closed_person_name = ''
							,$closed_person_phone = ''
							,$marital = ''
							,$npwp_number = ''
							,$bank_account_number = ''
							,$is_active = ''
							// ,$photo = ''
							,$id_att = ''
							,$religion = ''
							,$gender = ''
							,$married_status = ''
							,$married_since = ''
							,$bpjs_ketenagakerjaan = ''
							,$bpjs_kesehatan = ''
							,$work_unit_id = ''
							,$status = ''
							,$boleh_cuti = ''
							,$identity_no = ''
							) 
	{
        $photo 		= $this->do_upload();
		$work_email = ($work_email == 'new') ? $invite_new_email : $work_email;
		$work_email = ($work_email == '0') ? null : $work_email;
		
        $employee = array
		(
            'atasan_id' 				=> $atasan_id
            , 'company_id' 				=> $company_id
            , 'user_email' 				=> $work_email
            , 'position_id' 			=> $position_id
            , 'role_id' 				=> $role_id
			, 'role_salary_id' 			=> $role_salary_id
            , 'no_reg' 					=> $no_reg
            , 'employee_name' 			=> $employee_name
            , 'start_working_dt' 		=> date('Y-m-d', strtotime(str_replace('/', '-', $start_working_dt)))
            , 'born_place' 				=> $born_place
            , 'born_dt' 				=> date('Y-m-d', strtotime(str_replace('/', '-', $born_dt)))
            , 'address' 				=> $address
            , 'handphone1' 				=> $handphone1
            , 'handphone2' 				=> $handphone2
            , 'closed_person_name' 		=> $closed_person_name
            , 'closed_person_phone' 	=> $closed_person_phone
            , 'marital' 				=> $marital
            , 'npwp_number' 			=> $npwp_number
            , 'bank_account_number' 	=> $bank_account_number
            , 'work_email' 				=> $work_email
            , 'is_active' 				=> $is_active != '' ? '1' : '0'
            , 'photo' 					=> $photo
            , 'id_att' 					=> $id_att
            , 'religion' 				=> $religion
            , 'gender' 					=> $gender
            , 'married_status' 			=> $married_status
            , 'married_since' 			=> ($married_since != '') ? date('Y-m-d', strtotime(str_replace('/', '-', $married_since))) : null
            , 'bpjs_ketenagakerjaan'	=> $bpjs_ketenagakerjaan
            , 'bpjs_kesehatan' 			=> $bpjs_kesehatan
            , 'work_unit_id' 			=> $work_unit_id
            , 'status' 					=> $status
            , 'boleh_cuti'				=> $boleh_cuti
			, 'identity_no' 			=> $identity_no
        );

		
        if
        (
			$employee_id == '' ||
			$employee_id == null
        )
		{
			// new Employee
            $employee['created_by'] = $employee_name;
            $employee['created_dt'] = date('Y-m-d H:i:s');

            $this->db->insert('tb_m_employee', $employee);
			
            $this->insert_details($employee_id);						
        } 
		else 
		{



            $employee['changed_by'] = $employee_name;
            $employee['changed_dt'] = date('Y-m-d H:i:s');

            // update header
            $this->db->where('employee_id', $employee_id);
            $this->db->update('tb_m_employee', $employee);

            // delete items
            $this->db->where('employee_id', $employee_id);
            $this->db->delete('tb_m_family');

            $this->db->where('employee_id', $employee_id);
            $this->db->delete('tb_m_education');

            $this->db->where('employee_id', $employee_id);
            $this->db->delete('tb_m_employee_competency');

            $this->db->where('employee_id', $employee_id);
            $this->db->delete('tb_m_employee_payroll');

            // insert again
            $this->insert_details($employee_id);
        }
        return $employee_id;
    }

    function insert_details($employee_id
							,$employee_name
							,$family_details
							,$education_details
							,$competency_details
							,$allowance_details
							,$deduction_details
							,$pph21
							,$bpjskes) {
								
        foreach (json_decode($family_details) as $d) {
            $this->db->query("
				insert into tb_m_family
				(
					employee_id
					, relationship
					, gender
					, fullname
					, born_dt
					, born_place
					, created_by
					, created_dt
					, changed_by
					, changed_dt
				)
				values
				(
					'" . $employee_id . "'
					, '" . $d->relationship . "'
					, '" . $d->gender . "'
					, '" . $d->fullname . "'
					, '" . date('Y-m-d', strtotime(str_replace('/', '-', $d->born_dt))) . "'
					, '" . $d->born_place . "'
					, '" . $employee_name . "'
					, now()
					, '" . $employee_name . "'
					, now()
				)
			");
        }

        foreach (json_decode($education_details) as $d) {
            $this->db->query("
				insert into tb_m_education
				(
					employee_id
					, level
					, institution
					, faculty
					, graduated_dt
					, gpa
					, created_by
					, created_dt
					, changed_by
					, changed_dt
				)
				values
				(
					'" . $employee_id . "'
					, '" . $d->level . "'
					, '" . $d->institution . "'
					, '" . $d->faculty . "'
					, '" . date('Y-m-d', strtotime(str_replace('/', '-', $d->graduated_dt))) . "'
					, '" . $d->gpa . "'
					, '" . $employee_name . "'
					, now()
					, '" . $employee_name . "'
					, now()
				)
			");
        }

        foreach (json_decode($competency_details) as $d) {
            $this->db->query("
				insert into tb_m_employee_competency
				(
					employee_id
					, competency_id
					, score
					, created_by
					, created_dt
					, changed_by
					, changed_dt
				)
				values
				(
					'" . $employee_id . "'
					, '" . $d->id . "'
					, '" . $d->score . "'
					, '" . $employee_name . "'
					, now()
					, '" . $employee_name . "'
					, now()
				)
			");
        }

        foreach (json_decode($allowance_details) as $d) {
            $this->db->query("
				insert into tb_m_employee_payroll
				(
					employee_id
					, name
					, amount
					, type
					, created_by
					, created_dt
					, changed_by
					, changed_dt
				)
				values
				(
					'" . $employee_id . "'
					, '" . $d->name . "'
					, '" . $d->amount . "'
					, 'allowance'
					, '" . $employee_name . "'
					, now()
					, '" . $employee_name . "'
					, now()
				)
			");
        }

        foreach (json_decode($deduction_details) as $d) {
            $this->db->query("
				insert into tb_m_employee_payroll
				(
					employee_id
					, name
					, amount
					, type
					, created_by
					, created_dt
					, changed_by
					, changed_dt
				)
				values
				(
					'" . $employee_id . "'
					, '" . $d->name . "'
					, '" . $d->amount . "'
					, 'deduction'
					, '" . $employee_name . "'
					, now()
					, '" . $employee_name . "'
					, now()
				)
			");
        }

        $this->db->query("
			insert into tb_m_employee_payroll
			(
				employee_id
				, name
				, amount
				, type
				, created_by
				, created_dt
				, changed_by
				, changed_dt
			)
			values
			(
				'" . $employee_id . "'
				, 'Pajak PPH 21'
				, '" . $pph21 . "'
				, 'pph21'
				, '" . $employee_name . "'
				, now()
				, '" . $employee_name . "'
				, now()
			)
		");

        $this->db->query("
			insert into tb_m_employee_payroll
			(
				employee_id
				, name
				, amount
				, type
				, created_by
				, created_dt
				, changed_by
				, changed_dt
			)
			values
			(
				'" . $employee_id . "'
				, 'BPJS Kesehatan'
				, '" . $bpjskes . "'
				, 'bpjskes'
				, '" . $employee_name . "'
				, now()
				, '" . $employee_name . "'
				, now()
			)
		");

        $this->db->query("
			insert into tb_m_employee_payroll
			(
				employee_id
				, name
				, amount
				, type
				, created_by
				, created_dt
				, changed_by
				, changed_dt
			)
			values
			(
				'" . $employee_id . "'
				, 'BPJS Ketenagakerjaan'
				, '" . $bpjskes . "'
				, 'bpjstk'
				, '" . $employee_name . "'
				, now()
				, '" . $employee_name . "'
				, now()
			)
		");
    }

    function is_active($employee_id) {
        $this->db->query("
			update
				tb_m_employee
			set
				is_active = !is_active
			where employee_id = '" . $employee_id . "'
		");
    }

    function getCompany($company_id) {
        $this->db->select('company_name');
        $this->db->from('tb_m_company');
        $this->db->where('company_id', $company_id);
        return $this->db->get()->row();
    }

}

?>
