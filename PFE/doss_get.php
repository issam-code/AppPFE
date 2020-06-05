
<?php

include 'cnx.php';


$stid1 = oci_parse($con, "select * from shadoc.dossier d, shadoc.gr_prof g where d.id_gr=g.id_gr and g.id_util=(select id_util from shadoc.info where id=1) ");
  oci_execute($stid1);
  $doss=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM_DOSS'] =$row['NOM_DOSS'];
		$temp['ID_DOSS'] =$row['ID_DOSS'];
		array_push($doss, $temp);
} 
print(json_encode($doss));
oci_close($con);

?>


 




