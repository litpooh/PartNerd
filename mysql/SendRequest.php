<?php


// return name if correct, else empty


$db_server = "sophia.cs.hku.hk";
$db_user = "wkng2";
$db_pwd = "5gnZ1808";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

$action = (isset($_GET['action']) ? $_GET['action'] : "");
// echo($action);
$from = (isset($_GET['from']) ? $_GET['from'] : "");
// echo($course_id);
$to = (isset($_GET['to']) ? $_GET['to'] : "");
// echo($course_id);


if ($action == "insert" && $from && $to) {
    $sql = "INSERT INTO request (requester_user_id, requested_user_id) VALUES ($from,$to)";

    $res = mysqli_query($link, $sql) or die(mysqli_error()); 
    $request_id = mysqli_insert_id();
    mysqli_close($link);
}


?>