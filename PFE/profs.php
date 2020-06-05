<?php

include 'cnx.php';
$stid1 = oci_parse($con, "SELECT * FROM utilisateur where profession='Professeur' ");
  oci_execute($stid1);
   $prof=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM'] =$row['NOM_UTILI'] . "" . $row['PRENOM_UTILI'];
		$temp['IMAGE'] =$row['IMAGE'];
		$temp['ID_PROF'] =$row['ID_UTIL'];
		$temp['USERNAME'] =$row['USERNAME'];
		array_push($prof, $temp);
} 
print(json_encode($prof));
oci_close($con);

?>