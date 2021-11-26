
<?php


// return name if correct, else empty


$db_server = "sophia.cs.hku.hk";
$db_user = "knchu";
$db_pwd = "00624ckn";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

$action = (isset($_GET['action']) ? $_GET['action'] : "");
// echo($action);
$email = (isset($_GET['email']) ? $_GET['email'] : "");
// echo($email);
$password = (isset($_GET['password']) ? $_GET['password'] : "");
// echo($password);


if ($action == "select") {
    $sql = "SELECT id, name, phone_number, nationality,language   FROM user WHERE email='$email' AND password='$password'";

    // $sql = "SELECT name FROM user WHERE email='u3557110@connect.hku.hk' AND password='123456'";
    $res = mysqli_query($link, $sql) or die(mysqli_error());

    mysqli_close($link);

    while($row = mysqli_fetch_assoc($res)) {
        echo '{"id":"' . $row['id'];

            if ($row['name']){
                echo '","name":"' . $row['name'];
            }

            if ($row['phone_number']){
                echo '","phone_number":"' . $row['phone_number'];
            }

            if ($row['nationality']){
                echo '","nationality":"' . $row['nationality'];
            }

            if ($row['language']){
                echo '","language":"' . $row['language'];
            }

        echo '"}';
    }
    
}


?>
