<?php
if ($_POST) {
    $id = $_POST["id"];
    $puesto = $_POST["puesto"];
    $descripcion = $_POST["descripcion"];

    if ($puesto != "" && $descripcion != "" && $id != "") {
        try {
            $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
            $resultado = $pdo -> prepare('UPDATE puestos SET puesto = :a, descripcion = :b WHERE id = :c');
            $resultado->bindParam(":a", $puesto, PDO::PARAM_STR);
            $resultado->bindParam(":b", $descripcion, PDO::PARAM_STR);
            $resultado->bindParam(":c",$id, PDO::PARAM_INT);
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
