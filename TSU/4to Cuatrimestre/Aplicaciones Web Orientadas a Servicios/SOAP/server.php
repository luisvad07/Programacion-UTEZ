<?php
    include_once('lib/nusoap.php');
    $servicio = new soap_server();

    $servicio->configureWSDL("MiServicio");
    $servicio->register("devolverMensaje",
                        array('mensaje' => 'xsd:string'),
                        array('return' => 'xsd:string'));

    function devolverMensaje ($mensaje){
        $resultado = "El mensaje recibido fue ".$mensaje;
        return $resultado;
    }
    $servicio->service(file_get_contents("php://input"));
?>