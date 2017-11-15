<?php
/*
* @author: muhamad.deden@arkamaya.co.id
* @created: 2016-02-20
*/
if (! defined('BASEPATH'))
	exit ('No direct script access allowed');


function to_std_currency($val) {
    return 'Rp. ' . number_format($val, 2);
}


function timepicker_to_hour($val) {
    if(is_null($val)) return $val;

    $vals =  explode(":", $val);
    $hour = $vals[0] + ($vals[1]/60);
    return number_format($hour, 2);
}

function hour_to_timepicker($val) {
    if(is_null($val)) return $val;

    $vals =  explode(".", $val);

    if(count($vals) == 1) {
        return $val . ':00';
    } else if(count($vals) == 2) {
        $minute = doubleval("0.{$vals[1]}") * 60;
        return $vals[0] . ':' . intval($minute);
    }
}
