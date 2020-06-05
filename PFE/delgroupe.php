<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
	$nom=$_POST['NOM'];
	$stid1 = oci_parse($con, "delete from dossier where nom_doss = '$nom'");
$sql=oci_execute($stid1);
if($sql){
	$stid2 = oci_parse($con, "delete from groupe where nom_gr = '$nom'");
$sql=oci_execute($stid2);
if($sql){
	echo "success";

}else{
	
	$stid = oci_parse($con, "INSERT INTO Dossier(nom_doss,id_util,id_gr) VALUES ('$nom',(select id_util from utilisateur where profession ='Admin'),(select id_gr from groupe where nom_gr ='$nom'))");
    oci_execute($stid);
	echo "error";

}}else{
	echo "error";

}

}

oci_close($con);
?>