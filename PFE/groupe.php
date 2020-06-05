<?php

include 'cnx.php';

$stid1 = oci_parse($con, "SELECT * FROM groupe ");
  oci_execute($stid1);
   $groupe=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM'] =$row['NOM_GR'];
		$temp['ID'] =$row['ID_GR'];
		array_push($groupe, $temp);
} 
print(json_encode($groupe));

oci_close($con);

?>