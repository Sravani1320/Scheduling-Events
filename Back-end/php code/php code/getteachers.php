<?php

include 'config.php';

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from teacher" ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('tid' => $row['tid'],'name' => $row['name'],
'phone' => $row['phone'],'pass' => $row['pass'],'email' => $row['email']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>