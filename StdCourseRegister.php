<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $std_id = $_POST["std_id"];
    $course_name = $_POST["course_name"];

    $sql = "SELECT course_id FROM course WHERE course_name = '$course_name'";
    $res = mysqli_query($con, $sql);

    while($row = mysqli_fetch_row($res)) {
        $course_id = $row[0];
    }

    $stmt = mysqli_prepare($con, "INSERT INTO cregist VALUES (NULL,?,?,?)");
    mysqli_stmt_bind_param($stmt, "sss", $std_id, $course_id, $course_name);
    mysqli_stmt_execute($stmt);

    $response = array();
    $response["success"] = true;

    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
    ?>