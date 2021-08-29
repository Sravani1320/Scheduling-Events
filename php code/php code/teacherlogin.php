<?php
//two parameters coming from app
$uname=$_GET["email"];
$pwd=$_GET["pass"];

// connection string
            include 'config.php';
           //connection checking and establishment
            $con=mysqli_connect($hostname, $username, $password,$dbname);
            mysqli_query ($con,"set character_set_results='utf8'");
            $query_json = "SELECT * from teacher where email='$uname' and pass='$pwd';";
            $result = mysqli_query($con,$query_json);
           
            $row = mysqli_fetch_array($result);
            if(!$row)
            {
                echo '{"message":"Invalid username / password","status":"false"}';
            }else{
             
                
                echo '{"message":"Login successful","status":"true"}';
                
            }
?>