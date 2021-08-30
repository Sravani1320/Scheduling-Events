<?php
$eid=$_GET["eid"];

            include 'config.php';
           
            $con=mysqli_connect($hostname, $username, $password,$dbname);
            mysqli_query ($con,"set character_set_results='utf8'");
           
                $sql = "SELECT COUNT(eid) FROM `eventjoin` WHERE eid='$eid'";
                $result = mysqli_query($con, $sql);
                $count = mysqli_fetch_row($result)[0];
              //  echo $count;
            if(!$count)
            {
                echo '{"message":"0","status":"false"}';
            }else{
                echo '{"message":'. $count . ',"status":"true"}';
            }
?>