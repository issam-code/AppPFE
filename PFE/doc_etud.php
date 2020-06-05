
<?php
include 'cnx.php';
$stid1 = oci_parse($con, "SELECT * FROM shadoc.Document where id_gr=(select id_gr from shadoc.info where id=1)  ");
  oci_execute($stid1);
   $document=array();
while($row = oci_fetch_array($stid1, OCI_ASSOC+OCI_RETURN_NULLS)){	
$temp = array();
		$temp['NOM_DOC'] =$row['NOM_DOC'];
		$temp['DATE_PUB'] =$row['DATE_PUB'];
		$temp['MATIERE'] =$row['MATIERE'];
		$temp['URL_FILE'] =$row['FILE_DOC'];
		array_push($document, $temp);
} 
print(json_encode($document));
oci_close($con);

?>

