<?php

include 'config.php';
//SELECT `eid`, `category`, `name`, `dat`, `venue`, `description`, `email`, `image` FROM `event` WHERE 1
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$id=$_GET['id'];
// sql to delete a record
$sql = "DELETE FROM comment WHERE eid='$id' ";
$sql2 = "DELETE FROM eventjoin WHERE eid='$id' ";
$sql3 = "DELETE FROM event WHERE eid='$id' ";
mysqli_query($con, $sql);
mysqli_query($con, $sql2);
if (mysqli_query($con, $sql3)) {
     echo '{"message":"Event deleted successfully","status":"true"}';
 
} else {

       echo '{"message":"Error deleting Event","status":"true"}';
}

mysqli_close($con);
?>