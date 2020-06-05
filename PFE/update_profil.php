<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
$nom=$_POST['nom'];
$prenom=$_POST['prenom'];
$mail=$_POST['mail'];
$pass_act=$_POST['pass_act'];
$username=$_POST['username'];
$pass_new=$_POST['pass_new'];
$image=$_POST['image'];
$sql="select id_util from shadoc.info where id=1";
$stida0a = oci_parse($con, "select username from shadoc.info where id=1 ");
	oci_execute($stida0a);
	while (($row = oci_fetch_row($stida0a)) != false) {
    $usrnm= $row[0];
}
$path="imgprofil/$usrnm.png";
$url="http://192.168.42.16/pfe/$path";
$pass=$pass_act;
$stida0 = oci_parse($con, "select mot_pass from shadoc.utilisateur where id_util =($sql) ");
	oci_execute($stida0);
	while (($row = oci_fetch_row($stida0)) != false) {
    $mp= $row[0];
}
if($pass_act == null){
	echo "saisir le mot de passe actuel ";
}else{
	
    if($mp== $pass_act){
$msg="la modification de";
if($nom != null){
	 $stid01 = oci_parse($con, "update shadoc.utilisateur set nom_utili='$nom' where id_util=($sql) ");
    if(oci_execute($stid01)){$msg= $msg . " -nom";}
	}
if($prenom != null){
	 $stid02 = oci_parse($con, "update shadoc.utilisateur set prenom_utili='$prenom' where id_util=($sql) ");
    if(oci_execute($stid02)){$msg= $msg . " -prenom";}
	}
	if($mail != null){
	 $stid03 = oci_parse($con, "update shadoc.utilisateur set mail='$mail' where id_util=($sql) ");
    if(oci_execute($stid03)){$msg= $msg . " -mail";}
	}
	if($pass_new != null){
	 $stidaZ = oci_parse($con, "alter user $usrnm identified by $pass_new ");
     oci_execute($stidaZ);
	 $pass=$pass_new;
	 $stid04 = oci_parse($con, "update shadoc.utilisateur set mot_pass='$pass_new' where id_util=($sql) ");
    if(oci_execute($stid04)){  $msg= $msg . " -mot de passe";}
	}
	if($image != "no_image"){
		$stid07 = oci_parse($con, "update shadoc.utilisateur set image='$url' where id_util=($sql) ");
    if(oci_execute($stid07)){
	file_put_contents($path,base64_decode($image));
	$msg= $msg . " -image";}
	}
	
	if($username != null){
		$stid05a = oci_parse($con, "drop user $usrnm cascade ");
     oci_execute($stid05a);
	$stid05 = oci_parse($con, "create user $username identified by $pass ");
   oci_execute($stid05);
   $stidb1 = oci_parse($con, "grant etud to $username ");
    oci_execute($stidb1);
	$stidp1 = oci_parse($con, "update shadoc.info set username='$username' where id=1 ");
    oci_execute($stidp1);
	 $stid06 = oci_parse($con, "update shadoc.utilisateur set username='$username' where id_util=($sql) ");
    if(oci_execute($stid06)){  $msg= $msg . " -username";}
	}
	if($msg=="la modification de"){ echo "saisir les valeur pour modifier";
	}else{echo $msg . " bien effectuee";}}else{echo "le mot de passe incorrect ! ";}	
}
oci_close($con);
}
?>