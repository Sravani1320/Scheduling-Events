<?php

include 'config.php';
$eid=$_GET["eid"];
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from eventjoin where eid='$eid' " ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('jid' => $row['jid'],'eid' => $row['eid'],'email' => $row['email']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>