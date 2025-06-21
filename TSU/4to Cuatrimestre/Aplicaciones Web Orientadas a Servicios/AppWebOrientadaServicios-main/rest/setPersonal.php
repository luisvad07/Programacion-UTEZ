<?php
if ($_POST) {
    echo "Registos de personal.";
    $nombre = $_POST["nombre"];
    $apellidoP = $_POST["apellidoP"];
    $apellidoM = $_POST["apellidoM"];
    $sueldo = $_POST["sueldo"];
    $puesto = $_POST["puesto"];
    $fechaNac = $_POST["fechaNac"];

    if ($nombre != "" && $apellidoP != "" && $apellidoM != "" 
    && $sueldo != "" && $puesto != "" && $fechaNac != "") {
        try {
            $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
            $resultado = $pdo -> prepare('INSERT INTO personal(nombre,apellidoP,apellidoM,sueldo,
            puesto,fechaNac) VALUES (:a,:b,:c,:d,:e,:f)');
            $resultado->bindParam(":a", $nombre, PDO::PARAM_STR);
            $resultado->bindParam(":b", $apellidoP, PDO::PARAM_STR);
            $resultado->bindParam(":c", $apellidoM, PDO::PARAM_STR);
            $resultado->bindParam(":d", $sueldo, PDO::PARAM_INT);
            $resultado->bindParam(":e", $puesto, PDO::PARAM_INT);
            $resultado->bindParam(":f", $fechaNac, PDO::PARAM_STR);
            $resultado -> execute();
            echo "Registro exitoso.";
        } catch (PDOException $e) {
            echo $e -> getMessage();
        }
    }else{
        echo "-1";
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>setPersonal</title>
</head>
<body>
    <form action="setPersonal.php" method="POST">
        <h2>Registrar Usuario</h2>
        <br><br>
        Nombre
        <input type="text" name="nombre">
        <br><br>
        Apellido Paterno
        <input type="text" name="apellidoP">
        <br><br>
        Apellido Materno
        <input type="text" name="apellidoM">
        <br><br>
        Sueldo
        <input type="number" name="sueldo">
        <br><br>
        Puesto
        <input type="text" name="puesto">
        <br><br>
        Fecha de Nacimiento
        <input type="text" name="fechaNac">
        <br><br>
        <input type="submit" value="Enviar">
    </form>
</body>
</html>