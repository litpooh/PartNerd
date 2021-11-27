<?php
/* Connect to db */
$db_server = "sophia.cs.hku.hk";
$db_user = "kcjleung";
$db_pwd = "VUqv6h08";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

if (isset($_GET['id'])) {
  $request_id = $_GET['id'];
}

$sql = "DELETE FROM request WHERE id = " . $request_id;
$res = mysqli_query($link, $sql) or die(mysqli_error());

mysqli_close(link);
?>




