<?php
/*
* @author: muhamad.deden@arkamaya.co.id
* @created: 2016-02-17
*/
if (! defined('BASEPATH'))
	exit ('No direct script access allowed');

function is_weekend($your_date) {
    $week_day = date('w', strtotime($your_date));
    //returns true if Sunday or Saturday else returns false
    return ($week_day == 0 || $week_day == 6);
}

function to_std_dt($dt) {
    if(empty($dt)) return $dt;
    return date('Y-m-d', strtotime(str_replace('/', '-', $dt)));
}

function to_std_dt_screen($dt) {
    if(empty($dt)) return $dt;
    return date('d/m/Y', strtotime(str_replace('-', '/', $dt)));
}

function to_std_dt_table($dt) {
    if(empty($dt)) return $dt;
    return date('d M Y', strtotime(str_replace('-', '/', $dt)));
}

function extract_date_range_picker($dateStr) {
    $date_range_raw = explode('-', $dateStr);

    $start_date = '';
    $end_date = '';
    if(count($date_range_raw) == 2) {
        $start_date = to_std_dt($date_range_raw[0]);
        $end_date = to_std_dt($date_range_raw[1]);
    }

    return (object)[
                'start' => $start_date,
                'end' => $end_date];
}