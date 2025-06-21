<?php
//Conexion  a la base de datos

/*Necesitamos 4 parametros , Servidor , Usuario
canotraseña y nombre de la DB*/

//Variable de lso datos

$Ser= "LocalHost";
$user= "root";
$pass = "alejandro";
$db = "sege";

//Variable a usar para conexion

$conex = null;
$conex= mysqli_connect($Ser,$user,$pass,$db);


?>