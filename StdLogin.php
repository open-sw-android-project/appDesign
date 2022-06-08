<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $std_id = $_POST["std_id"];
    $std_password = $_POST["std_password"];

    $statement = mysqli_prepare($con, "SELECT * FROM student WHERE std_id = ? AND std_password = ?");
    mysqli_stmt_bind_param($statement, "ss", $std_id, $std_password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $std_name);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["std_name"] = $std_name;
    }

    echo json_encode($response);
?>