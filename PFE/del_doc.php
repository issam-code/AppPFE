<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
	$id=$_POST['id'];
	$stid1 = oci_parse($con, "delete from shadoc.document where id_doc = '$id'");
$sql=oci_execute($stid1);
if($sql){
	echo "success";

}else{
	
	
	echo "error";

}


}

oci_close($con);
?>