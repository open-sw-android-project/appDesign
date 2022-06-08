<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $pro_id = $_POST["pro_id"];
    $pro_password = $_POST["pro_password"];

    $statement = mysqli_prepare($con, "SELECT * FROM professor WHERE pro_id = ? AND pro_password = ?");
    mysqli_stmt_bind_param($statement, "ss", $pro_id, $pro_password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $pro_name);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["pro_name"] = $pro_name;
    }

    echo json_encode($response);
?>