                               
<?php
include 'cnx.php';

$stid1 = oci_parse($con, "SELECT * FROM shadoc.utilisateur where id_gr=(select id_gr from shadoc.info where id=1) ");
  oci_execute($stid1);
   $etud=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM'] =$row['NOM_UTILI'] . "" . $row['PRENOM_UTILI'];
		$temp['IMAGE'] =$row['IMAGE'];
		$temp['ID_ETUD'] =$row['ID_UTIL'];
		$temp['USERNAME'] =$row['USERNAME'];
		array_push($etud, $temp);
} 
print(json_encode($etud));
oci_close($con);
?>

