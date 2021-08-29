<?php

include 'config.php';
 
$name=$_GET["name"];
$email=$_GET["email"];
$phone=$_GET["phone"];
$pass=$_GET["pass"];



$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_dis="update student set name='$name',phone='$phone',pass='$pass' where email='$email' ";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Updated successfully.","status":"true"}';

?>