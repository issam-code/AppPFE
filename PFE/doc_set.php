<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
 $file_doc=$_POST['file'];
 $nom=$_POST['nom'];
 $matiere=$_POST['matiere'];
 $path_file=pathinfo($_POST['path']);
 $path="document/" . $path_file['basename'];
$url="http://192.168.42.16/pfe/$path";
$stid1 = oci_parse($con, "INSERT INTO shadoc.document (nom_doc,date_pub,matiere,id_doss,id_util,file_doc,id_gr) VALUES('$nom',current_date,'$matiere',(select id_doss from info where id=1),(select id_util from info where id=1),'$url',(select id_gr from info where id=1))");
if(oci_execute($stid1)){
	echo "succes";
file_put_contents($path,base64_decode($file_doc));

}else{echo "error";}

}
?>


	

