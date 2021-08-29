<?php

include 'config.php';
$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$cid=$_GET['id'];
// sql to delete a record
$sql = "DELETE FROM comment WHERE cid='$cid' ";

if (mysqli_query($con, $sql)) {
     echo '{"message":"Comment deleted successfully","status":"true"}';
 
} else {

       echo '{"message":"Error deleting Event","status":"true"}';
}

mysqli_close($con);
?>