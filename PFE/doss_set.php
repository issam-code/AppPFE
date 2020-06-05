
<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
	
$id_doss=$_POST['id_doss'];
	 $stid01 = oci_parse($con, "update shadoc.info set id_doss='$id_doss' where id=1 ");
    oci_execute($stid01);
	$stid02 = oci_parse($con, "update shadoc.info set id_gr=(select id_gr from shadoc.dossier where id_doss='$id_doss') where id=1 ");
    oci_execute($stid02);
}



?>
