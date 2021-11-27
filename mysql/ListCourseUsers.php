
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


if ($action == "select" && $course_id) {
    $sql = "SELECT user.name FROM enroll, user, course WHERE enroll.course_id='$course_id' AND enroll.course_id = course.id AND enroll.user_id = user.id";

    // $sql = "SELECT name FROM user WHERE email='u3557110@connect.hku.hk' AND password='123456'";
    $res = mysqli_query($link, $sql) or die(mysqli_error());

    mysqli_close($link);

    echo '{"course_id":"' . $course_id . '"';
    echo ', "name":[';
    $add_delimiter = false;

    while($row = mysqli_fetch_assoc($res)) {
        if ($row['name']){
            echo ($add_delimiter ? ', ' : '') . '"' . $row['name'] . '"';
            $add_delimiter = true;
        }
    }
    echo ']';
    echo '}';
}


?>