<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
$nom=$_POST['NOM'];

if ($nom==null){
echo "null";}
else{
$stid2 = oci_parse($con, "insert into groupe (nom_gr,id_util) values ('$nom',(select id_util from shadoc.info where id=1))");
$r=oci_execute($stid2);
$stid1 = oci_parse($con, "INSERT INTO Dossier(nom_doss,id_util,id_gr) VALUES ('$nom',(select id_util from shadoc.info where id=1),(select id_gr from groupe where nom_gr ='$nom'))");
$r=oci_execute($stid1);

if($r){
	echo "success";
}else{
	echo "error";
}
}
}
?>