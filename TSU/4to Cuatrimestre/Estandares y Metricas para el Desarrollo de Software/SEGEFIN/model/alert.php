<!DOCTYPE html>
<html>
<head>
    <title>SEGE</title>
</head>
<body>
<script src='../js/sweetalert2@11.js'></script>

<?php

function success()
{
    ?>

    <script type='text/javascript'>
        Swal.fire({
            title: 'Tu registro ha sido exitoso!!',
            position: 'center',
            icon: 'success',
            confirmButtonText: 'Ok',
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href='../index.php';

            }
        })

    </script>
    <?php
}

function fail()
{
    ?>

    <script type='text/javascript'>

        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Algo salio mal!',
            confirmButtonText: 'Reintentar',
            showDenyButton: true,
            denyButtonText: 'Cancelar',
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href='../index.php';
            } else if (result.isDenied) {
                window.location.href='https://www.google.com.mx';
            }
        })

    </script>
    <?php
}



?>
</body>
</html>