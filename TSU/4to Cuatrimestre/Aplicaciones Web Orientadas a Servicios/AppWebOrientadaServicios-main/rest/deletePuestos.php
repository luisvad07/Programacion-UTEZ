<?php
if ($_POST) {
    $id = $_POST["id"];

    if ($id != "") {
        try {
            $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
            $resultado = $pdo -> prepare('DELETE FROM puestos WHERE id = :a');
            $resultado->bindParam(":a", $id, PDO::PARAM_STR);
            $resultado -> execute();
            echo "Eliminado con exito.";
        } catch (PDOException $e) {
            echo $e -> getMessage();
        }
    }else{
        echo "-1";
    }
}
?>