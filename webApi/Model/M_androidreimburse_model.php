<?php

/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */

class M_androidreimburse_model extends CI_Model {

    function __construct() {
        parent:: __construct();
    }

    function getAllProjects($company_id = '') {
        $q = "select project_id, project_name from tb_r_projects where company_id = '" . $company_id . "' ORDER BY project_name ASC ";
        return $this->db->query($q)->result();
    }

    function view_reimburse($id) {
        $q = "
		SELECT
			r.reimburse_id,
			r.employee_id,
			e1.employee_name,
			e1.atasan_id,
			e3.employee_name as atasan,
			r.reimburse_dt,
			r.reimburse_type,
			r.reimburse_description,
			p.project_name,
			r.reimburse_amount as reimburse_amnt_unformat,
			concat('Rp. ',FORMAT(r.reimburse_amount,2)) as reimburse_amount,
			concat('Rp. ',FORMAT(r.reimburse_amount_approved,2)) as reimburse_amount_approved,
			r.reimburse_file,
			e2.employee_name as reimburse_status_by,
			r.reimburse_status as is_approved,
			r.reimburse_status_dt,
			r.reimburse_status,
			r.reimburse_reject_reason,
			r.created_by,
			r.created_dt,
			r.changed_by,
			r.changed_dt,
			r.project_id,
			
			e3.user_email as user_email_atasan,
			e1.user_email as user_email_created_by,
			e2.employee_name as reimburse_status_by_name,
			r.reimburse_status_by as reimburse_status_by_id
		FROM tb_r_reimburse r
		LEFT JOIN tb_m_employee e1 ON e1.employee_id = r.employee_id
		LEFT JOIN tb_m_employee e2 ON e2.employee_id = r.reimburse_status_by
        LEFT JOIN tb_m_employee e3 ON e3.employee_id = e1.atasan_id
        LEFT JOIN tb_r_projects p on p.project_id = r.project_id
        WHERE r.reimburse_id=$id";
        return $this->db->query($q)->row();
		
    }

    function get_reimburses($is_admin = '', $employee_id = '',$employee_name = '',$reimburse_status = '-',$reimburse_dt_from = '',$reimburse_dt_to = '',$company_id = '') {
        $sql = "

SELECT 
	reimburse_id, 
	employee_id, 
    employee_name, 
    reimburse_dt, 
    reimburse_type,
    reimburse_description,
    project_name,
    reimburse_amount, 
    reimburse_amount_approved, 
    reimburse_file, 
    reimburse_status_by, 
    is_approved,
    reimburse_status_dt, 
    created_by, 
    created_dt, 
    changed_by, 
    changed_dt
    FROM 
    (
	SELECT
		@ROWNUM:=@ROWNUM+1 as Row,
		r.reimburse_id,
		r.employee_id,
		e1.employee_name,
		r.reimburse_dt,
		r.reimburse_type,
                r.reimburse_description,
                p.project_name,
		concat('Rp. ',FORMAT(r.reimburse_amount,2)) as reimburse_amount,
		concat('Rp. ',FORMAT(r.reimburse_amount_approved,2)) as reimburse_amount_approved,
		r.reimburse_file,
		e2.employee_name as reimburse_status_by,
		r.reimburse_status as is_approved,
		r.reimburse_status_dt,
		r.created_by,
		r.created_dt,
		r.changed_by,
		r.changed_dt
	FROM tb_r_reimburse r
	LEFT JOIN tb_m_employee e1 ON e1.employee_id = r.employee_id
	LEFT JOIN tb_m_employee e2 ON e2.employee_id = r.reimburse_status_by
        LEFT JOIN tb_r_projects p on p.project_id = r.project_id
	JOIN (SELECT @ROWNUM := 0) R
        WHERE e1.company_id = '" . $company_id . "' 
        ";

        if ($is_admin != 1) {
            $sql .= " AND (r.employee_id = $employee_id OR e1.atasan_id = $employee_id )";
        }

        
        if ($reimburse_status != "-") {
            $sql .= " AND r.reimburse_status = '" . $reimburse_status . "'";
        }

        if ($reimburse_dt_from != "" && $reimburse_dt_to != "") {
            $sql .= " AND r.reimburse_dt BETWEEN '" . $reimburse_dt_from . "' AND '" . $reimburse_dt_to . "'";
        }

        if ($employee_name != "") {
            $sql .= " AND e1.employee_name LIKE '%" . $employee_name . "%' ";
        }
        
        $sql .= " order by r.reimburse_dt desc )t ";
        
        return $this->db->query($sql)->result();
		// echo $sql."<br><br>";
		
    }

    function get_employees($company_id) {
        //$sql = "select employee_id, employee_name from tb_m_employee WHERE atasan_id IS NOT NULL";
        $sql = "select employee_id, employee_name from tb_m_employee WHERE company_id = '".$company_id."' order by employee_name";
        return $this->db->query($sql)->result();
    }

    function get_reimburses_count($is_admin = '', $employee_id = '', $employee_name = '',$reimburse_status = '-',$reimburse_dt_from = '',$reimburse_dt_to = '',$company_id = '') {

        $sql = "SELECT COUNT(r.reimburse_id) as cnt FROM tb_r_reimburse r 
         LEFT JOIN tb_m_employee e1 ON e1.employee_id = r.employee_id
	LEFT JOIN tb_m_employee e2 ON e2.employee_id = r.reimburse_status_by
        LEFT JOIN tb_r_projects p on p.project_id = r.project_id
        WHERE  e1.company_id = '" . $company_id . "'  ";
        if ($is_admin != 1) {
            $sql .= " AND r.employee_id = $employee_id ";
        }

        // if($src_data['search']==true)
        //{
        if ($reimburse_status != "-") {
            $sql .= " AND r.reimburse_status = '" . $reimburse_status . "'";
        }

        if ($reimburse_dt_from != "" && $reimburse_dt_to != "") {
            $sql .= " AND r.reimburse_dt BETWEEN '" . $reimburse_dt_from . "' AND '" . $reimburse_dt_to . "'";
        }

        if ($employee_name != "") {
            $sql .= " AND e1.employee_name LIKE '%" . $employee_name . "%' ";
        }

        //}
        if ($is_admin != 1) {
            $sql .= " OR ( e1.atasan_id = $employee_id ";
            if ($reimburse_status != "") {
                $sql .= " AND r.reimburse_status = '" . $reimburse_status . "'";
            }

            if ($reimburse_dt_from != "" && $reimburse_dt_to != "") {
                $sql .= " AND r.reimburse_dt BETWEEN '" . $reimburse_dt_from . "' AND '" . $reimburse_dt_to . "'";
            }
            if ($employee_name != "") {
                $sql .= " AND e1.employee_name LIKE '%" . $employee_name . "%' ";
            }
            $sql .= ")";
        }
        return $this->db->query($sql)->row()->cnt;
		// echo $sql."<br><br>";
		// die;
    }

    function save($postdata, $reimburse_id) {
        if ($reimburse_id != NULL) {
            $this->db->where("reimburse_id", $reimburse_id);
            $this->db->update('tb_r_reimburse', $postdata);
        } else {
            $this->db->insert('tb_r_reimburse', $postdata);
			$reimburse_id = $this->db->insert_id();
        }
		return $reimburse_id;
    }

    function approve($approve_data, $reimburse_id) {
        $this->db->where('reimburse_id', $reimburse_id);
        $this->db->update('tb_r_reimburse', $approve_data);
    }

    function delete($reimburse_id) {
        $this->db->delete('tb_r_reimburse', array('reimburse_id' => $reimburse_id));
    }
    
    function upload_err_message($message)
    {
  
        switch($message)
        {
            case "<p>The uploaded file exceeds the maximum allowed size in your PHP configuration file.</p>";
                return "Ukuran file melebihi batas yang ditentukan (2MB).";
                break;
            case "<p>The uploaded file exceeds the maximum size allowed by the submission form.</p>";
                return "Ukuran file melebihi batas yang ditentukan (2MB).";
                break;
            case "<p>The file you are attempting to upload is larger than the permitted size.</p>";
                return "Ukuran file melebihi batas yang ditentukan (2MB).";
                break;
            case "<p>The image you are attempting to upload doesn't fit into the allowed dimensions.</p>";
                return "Ukuran file melebihi batas yang ditentukan (2MB).";
                break;
            case "<p>The filetype you are attempting to upload is not allowed.</p>";
                return "Jenis file yang diupload tidak diperbolehkan. Berikut adalah file yang diperbolehkan : zip,7z,rar,gif,jpg,png.";
                break;
            case "<p>The upload path does not appear to be valid.</p>";
                return "Upload path tidak valid.";
                break;
            case "<p>The upload destination folder does not appear to be writable.</p>";
                return "Upload path tidak valid.";
                break;
        }
    }

}
