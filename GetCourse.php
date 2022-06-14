<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $std_id = $_POST["std_id"];

    $response = array();

    $sql = "select course_name, pro_name from course where course_id not in (select course_id from cregist where std_id = '$std_id')";
    $res = mysqli_query($con, $sql);

    while($row = mysqli_fetch_assoc($res)) {
        $response[] = $row;
    }

    header('Content-Type: application/json; charset=utf8');
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>
