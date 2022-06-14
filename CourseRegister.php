<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con, 'SET NAMES utf8');

    $course_id = $_POST["course_id"];
    $semester = $_POST["semester"];
    $course_type = $_POST["course_type"];
    $course_name = $_POST["course_name"];
    $pro_name = $_POST["pro_name"];
    $course_grade = $_POST["course_grade"];
    $course_day1 = $_POST["course_day1"];
    $day1_time = $_POST["day1_time"];
    $course_day2 = $_POST["course_day2"];
    $day2_time = $_POST["day2_time"];
    $course_room = $_POST["course_room"];

    $statement = mysqli_prepare($con, "INSERT INTO course VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssssssssss", $course_id, $semester, $course_type, $course_name, $pro_name, $course_grade, $course_day1, $day1_time, $course_day2, $day2_time, $course_room);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
    ?>
