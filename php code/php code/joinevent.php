<?php

include 'config.php';
//SELECT `cid`, `eid`, `name`, `email`, `msg` FROM `comment` WHERE 1
$eid=$_GET["eid"];
$email=$_GET["email"];
$status="Joined";


$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json = "SELECT * from eventjoin where email='$email' and eid='$eid';";
            $result = mysqli_query($con,$query_json);
            $row = mysqli_fetch_array($result);
            if(!$row)
            {
      
$query_dis="insert into eventjoin(eid,email,status) values
('$eid','$email','$status');";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Event Joined successfully.","status":"true"}';
}else{
   echo '{"message":"Already Joined.","status":"false"}'; 
}
?>