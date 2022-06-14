<?php
    $con = mysqli_connect("localhost", "root", "991112", "qrcheck");
    mysqli_query($con,'SET NAMES utf8');

    $pro_name = $_POST["pro_name"];
    $course_name = $_POST["course_name"];

    $sql = "DELETE FROM course WHERE pro_name = '$pro_name' AND course_name = '$course_name'";
    $res = mysqli_query($con, $sql);

    mysqli_close($con);
?>