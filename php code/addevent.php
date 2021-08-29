<?php
if (!is_dir('images/')) {
    mkdir('images/', 0777, true);
}
include 'config.php';
//SELECT `eid`, `category`, `name`, `dat`, `venue`, `description`, `email` FROM `event` WHERE 1 
$category=$_POST["category"];
$name=$_POST["name"];
$venue=$_POST["venue"];
$description=$_POST["description"];
$email=$_POST["email"];
$dat=$_POST["dat"];


$result = array("success" => $_FILES["file"]["name"]);
$file_path = basename( $_FILES['file']['name']);
$picimg_url='images/'.$file_path;
if(move_uploaded_file($_FILES['file']['tmp_name'], 'images/'.$file_path)) {
    $result = array("success" => "File successfully uploaded");
} else{
    $result = array("success" => "error uploading file");
}
$con=mysqli_connect($hostname, $username, $password, $dbname);
mysqli_query ($con,"set character_set_results='utf8'");

$query="insert into event(category,name,dat,venue,description,email,image) values($category,$name,$dat,$venue,$description,$email,'$picimg_url');";
//echo $query_dis; 

$retval_dis = mysqli_query($con,$query);

mysqli_close($con);

echo '{"message":"Event Added successfully.","status":"true"}';

?>