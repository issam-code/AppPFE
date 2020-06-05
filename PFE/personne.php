<?php

include 'cnx.php';


$stid1 = oci_parse($con, "select nom from shadoc.info where id=1 ");
  oci_execute($stid1);

while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	

		echo $row['NOM'];
		
} 

oci_close($con);

?>