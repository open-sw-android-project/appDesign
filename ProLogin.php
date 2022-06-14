<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $pro_id = $_POST["pro_id"];
    $pro_password = $_POST["pro_password"];

    $statement = mysqli_prepare($con, "SELECT * FROM professor WHERE pro_id = ? AND pro_password = ?");
    mysqli_stmt_bind_param($statement, "ss", $pro_id, $pro_password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $pro_id, $pro_password, $pro_name, $pro_major);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["pro_id"] = $pro_id;
        $response["pro_password"] = $pro_password;
        $response["pro_name"] = $pro_name;
        $response["pro_major"] = $pro_major;
    }

    echo json_encode($response);
?>