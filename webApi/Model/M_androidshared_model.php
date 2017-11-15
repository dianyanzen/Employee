<?php
/*
 * @author: yanzen@arkamaya.co.id
 * @created: 2017-9-29
 */
class M_androidshared_model extends CI_Model 
{
	function __construct()
	{
		parent:: __construct();
	}
	
	function get_company_prefferences($system_type, $system_code, $order_by = '',$company_id) 
	{
		$sql = "
			select 
				system_value_txt, system_value_num, system_code
			from
				tb_m_company_prefferences
			where
				company_id = '" . $company_id . "'
		";
		
		
		$sql .= ($system_type != '') ? " and system_type = '" . $system_type . "'" : '';
		$sql .= ($system_code != '') ? " and system_code = '" . $system_code . "'" : '';
		
		$sql .= ($order_by != '') ? " order by " . $order_by : '';
		
		$sysval = $this->db->query($sql)->result();
		if (count($sysval)>0)
		{
			return $sysval[0];
		}
		else
		{
			$row = new stdClass();
			$row->system_code = '';
            $row->system_value_txt = '';
            $row->system_value_num = '';
            return $row;
		}        
    }
	
	function get_company_preferences_by_type($system_type,$company_id)
	{
		$sql = "
			select 
				*
			from
				tb_m_company_prefferences
			where
				company_id = '" . $company_id . "'
				and system_type = '" . $system_type . "'
		";
		return $this->db->query($sql)->result();
	}
	
	function get_company_field($field, $company_id) {
		$sql = "
			select " . $field . " from tb_m_company where company_id = '" . $company_id . "'
		";
		return $this->db->query($sql)->result_array();
	}

	


	// Added by angga@arkamaya.co.id
	// 2017-02-06
	function get_account_by_type($type,$company_id)
	{				

		$sql = "
			select * from tb_m_account where company_id = '" . $company_id . "' and account_type = '" . $type . "'
		";

		return $this->db->query($sql)->result_array();
	}

	function get_account_list($type, $priority,$company_id)
	{
		$sql = "";

		if($type != '' && $priority == '')
		{
			$sql = "
				select * from tb_m_account 
				where company_id = '" . $company_id . "' 
				and account_type = '" . $type . "'";
		}
		else if($type == '' && $priority != '')
		{
			$sql = "
				select * from tb_m_account 
				where company_id = '" . $company_id . "' 
				and account_type = '" . $priority . "'

				union

				select * from tb_m_account
				where company_id = '" . $company_id . "' 
				and account_type not in ('" . $priority . "')";
		}

		return $this->db->query($sql)->result_array();
	}

	function delete_company_preference($preff_id)
	{
		$this->db->where('preff_id', $preff_id);
		$this->db->delete('tb_m_company_prefferences');
	}

	function check_duplicate_company_preference($system_type, $system_code,$company_id)
	{
		$sql = "
			select 
				count(*) as cnt
			from
				tb_m_company_prefferences
			where
				company_id = '" . $company_id . "'
				and system_type = '" . $system_type . "'
				and system_code = '" . $system_code . "'
		";

		return $this->db->query($sql)->row()->cnt;
	}
	/*
	function save_company_preference($company_id,$system_type,$system_value_txt,$system_value_txt,$system_value_num,$preff_id,$employee_name)
	{
		$pref = array
		(
            'company_id' => $company_id, 
            'system_type' => $system_type, 
            'system_code' => $system_value_txt, 
            'system_value_txt' => $system_value_txt, 
            'system_value_num' => $system_value_num
        );

        if 
		(
			$preff_id == '' || 
			$preff_id == null
		) 
		{
            $pref['created_by'] = $employee_name;
            $pref['created_dt'] = date('Y-m-d H:i:s');

            $this->db->insert('tb_m_company_prefferences', $pref);
        } 
		else 
		{
			$pref_id = $preff_id;
			
            $pref['changed_by'] = $employee_name;
            $pref['changed_dt'] = date('Y-m-d H:i:s');

            // update header
			$this->db->where('preff_id', $pref_id);
			$this->db->update('tb_m_company_prefferences', $pref);
        }
	}
*/
	function get_company($company_id)
	{
		$sql = 'select * from tb_m_company where company_id = ' . $company_id;

		return $this->db->query($sql)->result();
	}
	
	//hadi 10/03/2017
	function _dmy_to_ymd($dmy, $splitter)
	{
		$datex = explode($splitter, $dmy);
		return ( count($datex) == 3 ) ? $datex[2].'-'.$datex[1].'-'.$datex[0] : null;
	}
	function _ymd_to_dmy($ymd, $splitter)
	{
		$datex = explode($splitter, $ymd);
		return ( count($datex) == 3 ) ? $datex[2].'/'.$datex[1].'/'.$datex[0] : null;
	}
	
	function send_mail($view, $data, $subject, $user_email)
	{
		
		$this->load->library('email');
			
		$message = $this->load->view($view, $data, true);
		
		$config['protocol'] = 'smtp';
		$config['smtp_host'] = SMTP_HOST;
		$config['smtp_port'] = SMTP_PORT;
		$config['smtp_timeout'] = '7';
		$config['smtp_user'] = SMTP_USER;
		$config['smtp_pass'] = SMTP_PASS;
		$config['charset'] = 'utf-8';
		$config['newline'] = "\r\n";
		$config['mailtype'] = 'html';
		$config['validation'] = TRUE;
			
		$this->email->initialize($config);
		$this->email->from(SMTP_USER, EMAIL_ALIAS);
		$this->email->to($user_email);
		
		// cc to admin of company_id
		// $this->email->cc($this->email_admin());
		
		$this->email->subject($subject);
		$this->email->message($message);

		if ($this->email->send()) {
			return true;
		} else {
			return false;
		}
	}
	
	function email_admin($company_id)
	{
		return $this->db->query("select user_email from tb_m_company where company_id = '" . $company_id . "'")->row()->user_email;
	}
}

?>