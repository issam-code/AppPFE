<?php
include 'cnx.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
$user=$_POST['username'];
$user_pass=$_POST['password'];
 $stid1 = oci_parse($con, "select * from utilisateur where username ='$user' and mot_pass='$user_pass' ");
oci_execute($stid1);
 if(oci_fetch_row($stid1)){
	 $con = oci_connect("$user", "$user_pass", "$server");
	 $stid01 = oci_parse($con, "update shadoc.info set username='$user',id_gr=(select id_gr from shadoc.utilisateur where username ='$user'),id_util=(select id_util from shadoc.utilisateur where username ='$user'),nom=(select nom_utili from shadoc.utilisateur where username ='$user') where id=1 ");
    oci_execute($stid01);
	$stid = oci_parse($con, "select PROFESSION from shadoc.utilisateur where username ='$user' ");
    oci_execute($stid);
	while (($row = oci_fetch_row($stid)) != false) {
    echo "$row[0]" ;
}
}else{
	echo "error";
}

}




?>