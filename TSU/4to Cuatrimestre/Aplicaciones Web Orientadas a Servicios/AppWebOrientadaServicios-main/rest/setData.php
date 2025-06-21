<?php
if ($_POST) {
    ini_set('display_errors',1);
    $tipo = $_POST["tipo"];
    
    if ($tipo==1) {
        echo "Registos de personal.";
        $nombre = $_POST["nombre"];
        $apellidoP = $_POST["apellidoP"];
        $apellidoM = $_POST["apellidoM"];
        $sueldo = $_POST["sueldo"];
        $puesto = $_POST["puesto"];
        $fechaNac = $_POST["fechaNac"];

        if ($nombre != "" && $apellidoP != "" && $apellidoP != "" 
        && $salario != "" && $posicion != "" && $genero != "") {
            try {
                $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
                $resultado = $pdo -> prepare('INSERT INTO personal(nombre,apellidoP,apellidoM,sueldo,fechaNac)
                VALUES (:a,:b,:c,:d,:e,:f)');
                $resultado->bindParam(":a", $nombre, PDO::PARAM_STR);
                $resultado->bindParam(":b", $apellidoP, PDO::PARAM_STR);
                $resultado->bindParam(":c", $apellidoM, PDO::PARAM_STR);
                $resultado->bindParam(":d", $sueldo, PDO::PARAM_INT);
                $resultado->bindParam(":e", $puesto, PDO::PARAM_STR);
                $resultado->bindParam(":f", $fechaNac, PDO::PARAM_STR);
                $resultado -> execute();
            } catch (PDOException $e) {
                echo $e -> getMessage();
            }
        }else{
            echo "-1";
        }
    }else if($tipo == 2){
        echo "Registros de puestos.";
        $puesto = $_POST["puesto"];
        $descripcion = $_POST["descripcion"];

        if ($puesto != "" && $descripcion) {
            try {
                $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
                $resultado = $pdo -> prepare('INSERT INTO puestos(puesto,descripcion)
                VALUES (:a,:b)');
                $resultado->bindParam(":a", $puesto, PDO::PARAM_STR);
                $resultado->bindParam(":b", $descripcion, PDO::PARAM_STR);
                $resultado -> execute();
            } catch (PDOException $e) {
                echo $e -> getMessage();
            }
        }else{
            echo "-1";
        }
    }else{
        echo "No hay coincidencias con la selección.";
    }
}
?>

