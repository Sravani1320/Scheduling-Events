<?php
//SELECT `cid`, `eid`, `name`, `email`, `msg` FROM `comment` WHERE 1
include 'config.php';
$eid=$_GET["eid"];
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from comment where eid='$eid' " ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('cid' => $row['cid'],'eid' => $row['eid'],'name' => $row['name'],'email' => $row['email'],'msg' => $row['msg']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>