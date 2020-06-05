<?php

include 'cnx.php';
$stid1 = oci_parse($con, "SELECT * FROM utilisateur where id_util=(select id_util from shadoc.info where id=1) ");
  oci_execute($stid1);
   $profil=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM'] =$row['NOM_UTILI'];
		$temp['PRENOM'] =$row['PRENOM_UTILI'];
		$temp['IMAGE'] =$row['IMAGE'];
		$temp['MAIL'] =$row['MAIL'];
		array_push($profil, $temp);
} 
print(json_encode($profil));
oci_close($con);

?>