<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $std_id = $_POST["std_id"];

    $response = array();

    $sql = "select course.course_name, course.pro_name, course.course_room from course left join cregist on course.course_name = cregist.course_name where std_id = '$std_id'";
    $res = mysqli_query($con, $sql);

    while($row = mysqli_fetch_assoc($res)) {
        $response[] = $row;
    }

    header('Content-Type: application/json; charset=utf8');
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
    ?>