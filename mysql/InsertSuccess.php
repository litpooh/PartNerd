
<?php


// return name if correct, else empty


$db_server = "sophia.cs.hku.hk";
$db_user = "wkng2";
$db_pwd = "5gnZ1808";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

$action = (isset($_GET['action']) ? $_GET['action'] : "");
// echo($action);
$course_id = (isset($_GET['course_id']) ? $_GET['course_id'] : "");
// echo($course_id);
$user_id = (isset($_GET['user_id']) ? $_GET['user_id'] : "");


if ($action == "insert" && $course_id && $user_id) {
    $sql = "INSERT INTO enroll (course_id, user_id) VALUES ($course_id,$user_id)";
    $res = mysqli_query($link, $sql) or die(mysqli_error()); 

    $enroll_id = mysqli_insert_id();

    $sql2 = "SELECT id, name FROM user WHERE id='$user_id'";
    $res2 = mysqli_query($link, $sql2) or die(mysqli_error()); 
    $res3 = mysqli_query($link, $sql2) or die(mysqli_error()); 

    mysqli_close($link);
    
    echo '{"course_id":"' . $course_id . '"';

    echo ', "user_id":';
    $add_delimiter = false;
    while($row = mysqli_fetch_assoc($res2)) {
        if ($row['id']){
            echo ($add_delimiter ? ', ' : '') . '"' . $row['id'] . '"';
            $add_delimiter = true;
        }
    }

    echo ', "user_name":';
    $add_delimiter = false;
    while($row = mysqli_fetch_assoc($res3)) {
        if ($row['name']){
            echo ($add_delimiter ? ', ' : '') . '"' . $row['name'] . '"';
            $add_delimiter = true;
        }
    }
    echo '}';

}

?>