<?php
/* Connect to db */
$db_server = "sophia.cs.hku.hk";
$db_user = "kcjleung";
$db_pwd = "VUqv6h08";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

if (isset($_GET['id'])) {
  $request_id = $_GET['id'];
}

echo '{';

$requester = array();
$requestname = array();
$nationality = array();
$language = array();
$phone = array();
$email = array();

$sql = "SELECT requester_user_id FROM request WHERE id = " . $request_id;
$res = mysqli_query($link, $sql) or die(mysqli_error());

while ($row = mysqli_fetch_array($res)) {
  array_push($requester, $row['requester_user_id']);
}

for ($i = 0; $i < count($requester); $i++) {
  $sql_name = "SELECT name, nationality, language, phone_number, email FROM user WHERE id = " . $requester[$i];
  $res2 = mysqli_query($link, $sql_name) or die(mysqli_error());

  while ($row2 = mysqli_fetch_array($res2)) {
    array_push($requestname, $row2['name']);
    array_push($nationality, $row2['nationality']);
    array_push($language, $row2['language']);
    array_push($phone, $row2['phone_number']);
    array_push($email, $row2['email']);
  }
}

mysqli_close(link);

echo '"name":' . $requestname[0] . ', "nationality":' . $nationality[0] . ', "language":' . $language[0] . ', "phone":' . $phone[0] . ', "email":'. $email[0] . '}';
?>
