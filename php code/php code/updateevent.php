<?php

include 'config.php';
 
$id=$_GET["id"];
$category=$_GET["category"];
$name=$_GET["name"];
$dat=$_GET["dat"];

$venue=$_GET["venue"];
$description=$_GET["description"];


////SELECT `eid`, `category`, `name`, `dat`, `venue`, `description`, `email`, `image` FROM `event` WHERE 1

$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query_dis="update event set name='$name',category='$category',dat='$dat',venue='$venue',description='$description' where eid='$id'";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Updated successfully.","status":"true"}';

?>