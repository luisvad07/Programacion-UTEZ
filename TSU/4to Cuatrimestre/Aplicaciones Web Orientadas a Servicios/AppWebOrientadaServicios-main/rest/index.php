<?php
header('Content-Type: application/json');
$metodo = $_SERVER['REQUEST_METHOD'];
switch ($metodo) {
    case 'GET':
        //Consulta
        if ($_GET['accion'] == 'personal') {
            try {
                $conexion = new PDO("mysql:host=localhost;dbname=utez;charset=utf8","root","");
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
            if (isset($_GET['id'])) { //mostrar registros con id
                $pstm = $conexion->prepare('SELECT * from personal WHERE id = :n');
                $pstm->bindParam(':n', $_GET['id']);
                $pstm->execute();
                $rs = $pstm->fetchAll(PDO::FETCH_ASSOC);
                if ($rs != null) {
                    echo json_encode($rs[0], JSON_PRETTY_PRINT);
                } else {
                    echo "No se encontraron coincidencias";
                }
            } else {
                $pstm = $conexion->prepare('SELECT P.*,po.position,po.description 
                FROM personal p INNER JOIN positions po ON p.position_id = po.id;');
                $pstm->execute();
                $rs = $pstm->fetchAll(PDO::FETCH_ASSOC);
                echo json_encode($rs, JSON_PRETTY_PRINT);
            }
        }
        if ($_GET["accion"] == "position") {
            try {
                $conexion = new PDO("mysql:host=localhost;dbname=utez;charset=utf8","root","");
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
            $pstm = $conexion->prepare('SELECT * FROM positions;');
            $pstm->execute();
            $rs = $pstm->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($rs, JSON_PRETTY_PRINT);
        }
        exit();
        break;
    case 'POST':
        if ($_GET['accion'] == 'personal') {
            $jsonData = json_decode(file_get_contents("php://input"));
            try {
                $conn = new PDO("mysql:host=localhost;dbname=utez;charset=utf8", "root", "");
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
            $query = $conn->prepare('INSERT INTO personal (name, surname, lastname, salary, birthday, position_id) 
            VALUES (:name, :surname, :lastname, :salary, :birthday, :position)');
            $query->bindParam(":name", $jsonData->name);
            $query->bindParam(":surname", $jsonData->surname);
            $query->bindParam(":lastname", $jsonData->lastname);
            $query->bindParam(":salary", $jsonData->salary);
            $query->bindParam(":birthday", $jsonData->birthday);
            $query->bindParam(":position", $jsonData->position);
            $result = $query->execute();
            if ($result) {
                $_POST["error"] = false;
                $_POST["message"] = "Registrado correctamente";
                $_POST["status"] = 200;
            } else {
                $_POST["error"] = true;
                $_POST["message"] = "Error al registrar";
                $_POST["status"] = 400;
            }
            echo json_encode(($_POST));
        }
        break;
    case 'PUT':
        if ($_GET['accion'] == 'personal') {
            $jsonData = json_decode(file_get_contents("php://input"));
            try {
                $conn = new PDO("mysql:host=localhost;dbname=utez;charset=utf8", "root", "");
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
            $query = $conn->prepare("UPDATE `personal` SET `name` = :name, `surname` = :surname, 
            `lastname` = :lastname, `salary` = :salary, `birthday` = :birthday, `position_id` = :position WHERE `id` = :id;");
            $query->bindParam(":name", $jsonData->name);
            $query->bindParam(":surname", $jsonData->surname);
            $query->bindParam(":lastname", $jsonData->lastname);
            $query->bindParam(":salary", $jsonData->salary);
            $query->bindParam(":birthday", $jsonData->birthday);
            $query->bindParam(":position", $jsonData->position);
            $query->bindParam(":id", $jsonData->id);
            $result = $query->execute();
            if ($result) {
                echo ("Personal registrado correctamente. Code $result");
            } else {
                echo ("Error al registrar. Code $result");
            }
        }
        break;
    case 'DELETE':
        if ($_GET['accion'] == 'personal') {
            $id = $_GET['id'];
            try {
                $conn = new PDO("mysql:host=localhost;dbname=utez;charset=utf8", "root", "");
            } catch (PDOException $e) {
                echo $e->getMessage();
            }
            $query = "DELETE FROM personal WHERE id = :id";
            $pstm = $conn->prepare($query);
            $pstm->bindParam(":id", $id);
            $rs = $pstm->execute();
            if ($rs) {
                echo ("Personal eliminado correctamente. Code $rs");
            } else {
                echo ("Error al eliminar a este personal. Code $rs");
            }
        }
        break;
    default:
        echo "Método no soportado.";
        break;
}