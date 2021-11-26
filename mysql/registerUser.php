<?php
$db_server = "sophia.cs.hku.hk";
$db_user = "knchu";
$db_pwd = "00624ckn";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

$action = (isset($_GET['action']) ? $_GET['action'] : "");
if ($action){
    echo($action);
}
$fullName = (isset($_GET['name']) ? $_GET['name'] : "");
if ($fullName){
    echo($fullName);
}
$age = (isset($_GET['age']) ? $_GET['age'] : "");
if ($age){
    echo($age);
}
$email = (isset($_GET['email']) ? $_GET['email'] : "");
if ($email){
    echo($email);
}
$password = (isset($_GET['password']) ? $_GET['password'] : "");
if ($password){
    echo($password);
}

if ($action == "insert" && $fullName && $age && $email && $password) {
$sql = "INSERT INTO user (name,age,email,password) VALUES ('$fullName','$age','$email','$password')";
$res = mysqli_query($link, $sql) or die(mysqli_error());
$user_id = mysqli_insert_id();
echo($user_id);
}


?>
