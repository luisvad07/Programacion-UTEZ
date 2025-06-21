<?php
try {
    $pdo = new PDO("mysql:host=localhost;dbname=utez","root","");
} catch (PDOException $e) {
    echo $e ->getMessage();
}

$sql = "SELECT * FROM personal inner join puestos on personal.puesto = puestos.id";
$resultado = $pdo->query($sql);
$datos=array();

while ($row = $resultado->fetch(PDO::FETCH_ASSOC)) {
    $datos[]=$row;
}
echo json_encode($datos);
?>   