<?php
/*
* @author: muhamad.deden@arkamaya.co.id
* @created: 2016-02-21
*/
if (! defined('BASEPATH'))
	exit ('No direct script access allowed');

function a($href,
           $title,
           $text) {
    return "<a href=".site_url($href).' title="'.$title.'">'.$text.'</a>';
}
