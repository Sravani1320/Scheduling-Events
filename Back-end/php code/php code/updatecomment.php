<?php

include 'config.php';
 
$id=$_GET["cid"];
$name=$_GET["name"];
$msg=$_GET["msg"];
$email=$_GET["email"];


$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_dis="update comment set name='$name',msg='$msg' where cid='$id'";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Updated successfully.","status":"true"}';

?>