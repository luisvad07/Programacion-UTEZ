<?php
    ini_set('display_errors',1);
    if ($_POST) {
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

                echo "Registro exitoso.";
            } catch (PDOException $e) {
                echo $e -> getMessage();
            }
        }else{
            echo "-1";
        }
    }
?>