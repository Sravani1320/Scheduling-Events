<?php

include 'config.php';
//SELECT `eid`, `category`, `name`, `dat`, `venue`, `description`, `email`, `image` FROM `event` WHERE 1
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$id=$_GET['id'];
// sql to delete a record
$sql = "DELETE FROM teacher WHERE tid='$id' ";

if (mysqli_query($con, $sql)) {
     echo '{"message":"Teacher deleted successfully","status":"true"}';
 
} else {

       echo '{"message":"Error deleting Event","status":"true"}';
}

mysqli_close($con);
?>