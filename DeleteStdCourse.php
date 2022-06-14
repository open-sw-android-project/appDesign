<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $std_id = $_POST["std_id"];
    $course_name = $_POST["course_name"];

    $sql = "DELETE FROM cregist WHERE std_id = '$std_id' AND course_name = '$course_name'";
    $res = mysqli_query($con, $sql);

    mysqli_close($con);
    ?>