<?php

include 'config.php';
//SELECT `cid`, `eid`, `name`, `email`, `msg` FROM `comment` WHERE 1
$eid=$_GET["eid"];
$name=$_GET["name"];
$email=$_GET["email"];
$msg=$_GET["msg"];


$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json = "SELECT * from comment where email='$email' and eid='$eid';";
            $result = mysqli_query($con,$query_json);
            $row = mysqli_fetch_array($result);
            if(!$row)
            {
      
$query_dis="insert into comment(name,email,msg,eid) values
('$name','$email','$msg','$eid');";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Comment Added successfully.","status":"true"}';
}else{
   echo '{"message":"Comment is already given.","status":"false"}'; 
}
?>