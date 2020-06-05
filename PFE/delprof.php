<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
$username=$_POST['username'];
$id=$_POST['id'];
$stid3 = oci_parse($con, "delete from shadoc.gr_prof where id_util = '$id'");
$sql=oci_execute($stid3);
$stid1 = oci_parse($con, "delete from utilisateur where username = '$username'");
$sql=oci_execute($stid1);
if($sql){
	$stid2 = oci_parse($con, "drop user $username cascade ");
oci_execute($stid2);
	echo "success";

}else{
	echo "error";

}
}
 


?>