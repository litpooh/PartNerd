<?php
/* Connect to db */
$db_server = "sophia.cs.hku.hk";
$db_user = "kcjleung";
$db_pwd = "VUqv6h08";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

if (isset($_GET['id'])) {
  $user_id = $_GET['id'];
}

echo '{';

$requests = array();
$requestid = array();
$requestname = array();

$sql = "SELECT id, requester_user_id FROM request WHERE requested_user_id = " . $user_id;
$res = mysqli_query($link, $sql) or die(mysqli_error());

while ($row = mysqli_fetch_array($res)) {
  array_push($requests, $row['requester_user_id']);
  array_push($requestid, $row['id']);
}

for ($i = 0; $i < count($requests); $i++) {
  $sql_name = "SELECT name FROM user WHERE id = " . $requests[$i];
  $res2 = mysqli_query($link, $sql_name) or die(mysqli_error());

  while ($row2 = mysqli_fetch_array($res2)) {
    array_push($requestname, $row2['name']);
  }
}

mysqli_close(link);

echo '"requesters":[';
$add_delimiter = false;
for ($i = 0; $i < count($requestname); $i++) {
  echo ($add_delimiter ? ', [' : '[') . '"' . $requestid[$i] . '", "' . $requestname[$i] . '"]';
  $add_delimiter = true;
}

echo ']}';
?>

