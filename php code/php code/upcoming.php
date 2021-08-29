<?php

include 'config.php';
//SELECT `eid`, `category`, `name`, `dat`, `venue`, `description`, `email`, `image` FROM `event` WHERE 1
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json ="SELECT * from event ORDER by eid DESC LIMIT 3" ;
$retval_json = mysqli_query($con,$query_json);
$rows = array();
while($row = mysqli_fetch_assoc($retval_json)) {
$rows[] = array('eid' => $row['eid'],
'category' => $row['category'],'name' => $row['name'],'venue' => $row['venue'],'description' => $row['description'],'email' => $row['email'],'image' => $row['image'],'dat' => $row['dat']);
}

echo json_encode($rows);
//Json End
mysqli_close($con);
?>