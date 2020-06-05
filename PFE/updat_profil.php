<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
$pass=$_POST['pass'];
$nom=$_POST['nom'];
$prenom=$_POST['prenom'];
$mail=$_POST['mail'];
$id_gr=$_POST['id_gr'];
$username=$_POST['username'];
$profession=$_POST['profession'];
$image=$_POST['image'];
$path="imgprofil/$nom$prenom.png";
$url="http://192.168.42.16/pfe/$path";
$stida = oci_parse($con, "create user $username identified by $pass ");
oci_execute($stida);
$stidb = oci_parse($con, "grant etud to $username ");
oci_execute($stidb);
$stid1 = oci_parse($con, "INSERT INTO utilisateur(mot_pass,nom_utili,prenom_utili,username,profession,image,mail,id_gr) VALUES('$pass','$nom','$prenom','$username','$profession','$url','$mail','$id_gr')");
$sql=oci_execute($stid1);
if($sql){
	echo "success";
	file_put_contents($path,base64_decode($image));

}else{
	echo "error";

}
} 


?>


