<?php

$servidor = "localhost";    
$usuario = "root";
$contrasena = "";  
$database = "sistemaencuesta";
    
$conexion = mysqli_connect($servidor , $usuario , $contrasena, $database);

if($conexion === false){
    die("ERROR: Base no Conectada. " . mysqli_connect_error());
        
} else {
   echo "Conexion Exitosa uwu";
}

// Taking all 5 values from the form data(input)

	$tituloencuesta = $_POST['tit_encuesta'];
    $nombreescuela = $_POST['nom_escuela'];
    $nombreencuestado = $_POST['nom_encuestado'];
    $promedio= $_POST['promedio'];
          
        // Performing insert query execution
        // here our table name is college
        $sql = "INSERT INTO encuestas(id, titulo_encuesta, nombre_escuela, nombre_encuestado, promedio) VALUES ('', '$tituloencuesta', '$nombreescuela', '$nombrencuestado', '$promedio')";
          
        mysqli_query ($conexion,$sql);
          
        // Close connection
        mysqli_close($conexion);   
?>