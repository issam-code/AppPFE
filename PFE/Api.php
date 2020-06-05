<?php 

	include 'cnx.php';
$stid1 = oci_parse($con, "SELECT * FROM shadoc.utilisateur where username= '$user' ");
  oci_execute($stid1);
   $utili=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM'] =$row['NOM_UTILI'];
		$temp['PRENOM'] =$row['PRENOM_UTILI'];
		$temp['MAIL'] =$row['MAIL'];
		$temp['IMAGE'] =$row['IMAGE'];
		array_push($utili, $temp);
} 
print(json_encode($utili));
oci_close($con);
	?>