<?php
/*
* @author: muhamad.deden@arkamaya.co.id
* @created: 2016-02-17
*/
class M_androidholiday_model extends CI_Model
{
	function __construct()
	{
		parent:: __construct();
		$this->load->database();
	}

    public function is_holiday($dt,$company_id)
    {
        return $this->db
                    ->from('tb_m_holiday_cal')
                    ->where(array(
                             'holiday_dt' => $dt,
                             'company_id' => $company_id
                         ))
                    ->count_all_results() > 0;
    }
}
