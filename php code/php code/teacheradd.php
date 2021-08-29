<?php

include 'config.php';
//SELECT `sid`, `name`, `email`, `phone`, `pass` FROM `student` WHERE 1
$name=$_GET["name"];
$email=$_GET["email"];
$phone=$_GET["phone"];
$pass=$_GET["pass"];




$con=mysqli_connect($hostname, $username, $password,$dbname);
mysqli_query ($con,"set character_set_results='utf8'");
$query_json = "SELECT * from teacher where email=$email;";
            $result = mysqli_query($con,$query_json);
            $row = mysqli_fetch_array($result);
            if(!$row)
            {
      
$query_dis="insert into teacher(name,email,phone,pass) values
('$name','$email','$phone','$pass');";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query_dis);

mysqli_close($con);

echo '{"message":"Teacher Added successfully.","status":"true"}';
}else{
   echo '{"message":"Teacher is already exist.","status":"false"}'; 
}
?>