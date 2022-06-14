<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");                // mysql 연결
    mysqli_query($con,'SET NAMES utf8');

    $std_id = $_POST["std_id"];                          // student TABLE column
    $std_password = $_POST["std_password"];
    $std_name = $_POST["std_name"];
    $std_grade = $_POST["std_grade"];
    $std_department = $_POST["std_department"];

    $statement = mysqli_prepare($con, "INSERT INTO student VALUES (?,?,?,?,?)");        // student TABLE에 회원정보 삽입
    mysqli_stmt_bind_param($statement, "sssss", $std_id, $std_password, $std_name, $std_grade, $std_department);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>