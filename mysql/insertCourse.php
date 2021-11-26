<?php
$year1Courses = [["COMP1117A", "Computer Programming"],
    ["COMP1117B", "Computer Programming"],
    ["ENGG1330A", "Computer Programming I"],
    ["ENGG1340A", "Computer Programming II"],
    ["ENGG1340B", "Computer Programming II"],
    ["ENGG1340C", "Computer Programming II"],
    ["FITE1010", "Introduction to Financial Technologies"],
    ["FITE2000A", "Foundations of FinTech Programming"],
    ["STAT1005", "Essential skills for undergraduates: Foundations of Data Science"]];

$year2Courses = [["COMP2113A", "Programming Technologies"],
    ["COMP2113B", "Programming Technologies"],
    ["COMP2113C", "Programming Technologies"],
    ["COMP2119A", "Introduction to Data Structures and Algorithms"],
    ["COMP2119B", "Introduction to Data Structures and Algorithms"],
    ["COMP2119C", "Introduction to Data Structures and Algorithms"],
    ["COMP2119D", "Introduction to Data Structures and Algorithms"],
    ["COMP2120A", "Computer Organization"],
    ["COMP2120B", "Computer Organization"],
    ["COMP2121A", "Discrete Mathematics"],
    ["COMP2121B", "Discrete Mathematics"],
    ["COMP2121C", "Discrete Mathematics"],
    ["COMP2396A", "Object-oriented Programming and Java"],
    ["COMP2396B", "Object-oriented Programming and Java"]];

$year3Courses = [["COMP3230A", "Principles of Operating Systems"],
            ["COMP3230B", "Principles of Operating Systems"],
            ["COMP3234A", "Computer and Communication Networks"],
            ["COMP3234B", "Computer and Communication Networks"],
            ["COMP3250A", "Design and Analysis of Algorithms"],
            ["COMP3250B", "Design and Analysis of Algorithms"],
            ["COMP3278A", "Intro. to Database Management Systems"],
            ["COMP3278B", "Intro. to Database Management Systems"],
            ["COMP3297A", "Software Engineering"],
            ["COMP3297B", "Software Engineering"],
            ["COMP3311", "Legal Aspects of Computing"],
            ["COMP3410A", "Internship"],
            ["COMP3410B", "Internship"],
            ["COMP3412A", "Internship"],
            ["COMP3412B", "Internship"]];

$year4Courses = [["COMP4801", "Final Year Project full year"],
            ["COMP4802", "Extended Final Year Project"],
            ["COMP4802", "Extended Final Year Project"],
            ["COMP4804", "Computing and Data Analytics Project"],
            ["COMP4805", "Project (2nd Major)"],
            ["FITE4801", "Project full year"]];

$electives = [["COMP2501", "Introduction to data science and engineering"],
            ["COMP2502", "Computing Fundamentals (Minor in DA&E only)"],
            ["COMP3231", "Computer Architecture"],
            ["COMP3258", "Functional Programming"],
            ["COMP3259", "Principles of Programming Languages"],
            ["COMP3270A", "Artificial Intelligence"],
            ["COMP3270B", "Artificial Intelligence"],
            ["COMP3271", "Computer Graphics"],
            ["COMP3314A", "Machine Learning"],
            ["COMP3314B", "Machine Learning"],
            ["COMP3316", "Quantum Information and Computation"],
            ["COMP3317", "Computer Vision"],
            ["COMP3320", "Electronic Commerce Technology"],
            ["COMP3322A", "Modern Technologies on World Wide Web"],
            ["COMP3322B", "Modern Technologies on World Wide Web"],
            ["COMP3329", "Computer Game Design and Programming"],
            ["COMP3330", "Interactive Mobile Application Design and Programming"],
            ["COMP3340", "Applied Deep Learning"],
            ["COMP3352", "Algorithmic Game Theory"],
            ["COMP3355", "Cyber Security"],
            ["COMP3356", "Robotics"],
            ["COMP3357", "Cryptography"],
            ["COMP3358", "Distributed and Parallel Computing"],
            ["COMP3360", "Data-driven computer animation"],
            ["COMP3361", "Natural language processing"],
            ["COMP3362", "Hands-on AI: experimentation & applications"],
            ["COMP3407", "Scientific Computing"],
            ["FITE2010", "Distributed Ledger & Blockchain"],
            ["FITE3010", "Big Data and Data Mining"],
            ["FITE3012", "E-payment and Crypto-currency"]];


$mscCourses = [["FITE7407","Securities transaction banking"],["FITE7409","Blockchain and cryptocurrency"]];

$allCourses = [$year1Courses, $year2Courses, $year3Courses, $year4Courses, $electives, $mscCourses];

$db_server = "sophia.cs.hku.hk";
$db_user = "knchu";
$db_pwd = "00624ckn";
$link = mysqli_connect($db_server, $db_user, $db_pwd, $db_user) or die(mysqli_error());

$action = (isset($_GET['action']) ? $_GET['action'] : "");

if($action == "insert") {

    foreach ($allCourses as $courses) {
        foreach ($courses as $course) {
            $sql = "INSERT INTO course (code, name) VALUES ('$course[0]','$course[1]');";
            $res = mysqli_query($link, $sql) or die(mysqli_error());
        }
    }

}



?>