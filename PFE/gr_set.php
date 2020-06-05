<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
	
$id_gr=$_POST['id_gr'];
	 $stid01 = oci_parse($con, "update shadoc.info set id_gr=$id_gr where id=1 ");
    oci_execute($stid01);
}



?>