<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");    // mysql 연결
    mysqli_query($con,'SET NAMES utf8');

    $pro_id = $_POST["pro_id"];                  // professor TABLE column
    $pro_password = $_POST["pro_password"];
    $pro_name = $_POST["pro_name"];
    $pro_department = $_POST["pro_department"];

    $statement = mysqli_prepare($con, "INSERT INTO professor VALUES (?,?,?,?)");         // professor TABLE에 회원정보 삽입
    mysqli_stmt_bind_param($statement, "ssss", $pro_id, $pro_password, $pro_name, $pro_department);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;


    echo json_encode($response);

?>