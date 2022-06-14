<?php

    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $pro_name = $_POST["pro_name"];

    $response = array();

    $sql = "SELECT course_name, course_room FROM course WHERE pro_name = '$pro_name'";
    $res = mysqli_query($con, $sql);

    while($row = mysqli_fetch_assoc($res)) {
        $response[] = $row;
    }

    header('Content-Type: application/json; charset=utf8');
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
    ?>
