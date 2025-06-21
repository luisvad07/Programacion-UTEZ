<?php
if ($_POST) {
    $id = $_POST["id"];
    $nombre = $_POST["nombre"];
    $apellidoP = $_POST["apellidoP"];
    $apellidoM = $_POST["apellidoM"];
    $sueldo = $_POST["sueldo"];
    $puesto = $_POST["puesto"];
    $fechaNac = $_POST["fechaNac"];

    if ($nombre != "" && $apellidoP != "" && $apellidoM != "" 
    && $sueldo != "" && $puesto != "" && $fechaNac != "" && $id != "") {
        try {
            $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
            $resultado = $pdo -> prepare('UPDATE personal SET nombre = :a,
            apellidoP = :b,apellidoM = :c,sueldo = :d, puesto = :e,fechaNac = :f 
            WHERE id = :g');
            $resultado->bindParam(":a", $nombre, PDO::PARAM_STR);
            $resultado->bindParam(":b", $apellidoP, PDO::PARAM_STR);
            $resultado->bindParam(":c", $apellidoM, PDO::PARAM_STR);
            $resultado->bindParam(":d", $sueldo, PDO::PARAM_INT);
            $resultado->bindParam(":e", $puesto, PDO::PARAM_INT);
            $resultado->bindParam(":f", $fechaNac, PDO::PARAM_STR);
            $resultado->bindParam(":g", $id, PDO::PARAM_INT);
            $resultado -> execute();
            echo "Actualizado con exito.";
        } catch (PDOException $e) {
            echo $e -> getMessage();
        }
    }else{
        echo "-1";
    }
}
?>
