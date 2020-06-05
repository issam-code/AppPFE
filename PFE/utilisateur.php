<?php

include 'login.php';
$stid1 = oci_parse($con, "SELECT * FROM groupe ");
  oci_execute($stid1);
  $ut=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM'] =$row['NOM_gr'] ;
		array_push($ut, $temp);
} 
print(json_encode($ut));
oci_close($con);
?>
