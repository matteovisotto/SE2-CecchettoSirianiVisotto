<?php
$fileds = ["name", "surname", "mail", "birthdate", "areaOfResidence", "password", "password_conf"];
foreach ($fileds as $value){
    if(!checkAttributes($_POST[$value])){
        header("Location: /register?error");
        exit();
    }
}
if($_POST["password"] != $_POST["password_conf"]){
    header("Location: /register?passError");
    exit();
}
$name = $_POST['name'];
$surname = $_POST['surname'];
$mail = $_POST['mail'];
$birthdate = $_POST['birthdate'];
$areaOfResidence = $_POST['areaOfResidence'];
$password = $_POST['password'];
$policyMakerID = (isset($_POST['policyMakerID']) && !empty($_POST['policyMakerID'])) ? $_POST['policyMakerID'] : null;

//INSERT IN TEMPORARY DB AND SEND EMAIL

header("Location: /confirm");



function checkAttributes($attr){
    if(isset($attr) && !empty($attr)){
        return true;
    }
    return false;
}