<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Registro Productos</title>
    <link
            href="css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
    />
</head>

<body>
<header>
    <!-- encabezado  -->
    <nav class="navbar  navbar-expand navbar-dark bg-success">
        <div class="container d-flex justify-content-center">
            <div class="navbar-nav ">
                <h2 class="text-uppercase fw-bold" style="color: #fffb">Bienvenido</h2>
            </div>
        </div>
    </nav>
</header>

<main>
    <div class="container mt-5">

        <div class="card shadow  mb-3" style="max-width: 100%;">
            <div class="row g-0">
                <div class="col-md-4 my-4 mt-md-0 text-center align-self-center ">
                    <img src="https://sisava2.utez.edu.mx:8443/SISAVA/img/utez/logo2016-contorno.png" class="img-fluid rounded-start" alt="...">
                </div>
                <div class="col-md-8 mt-sm-4 mt-md-0">
                    <div class="card-body  border shadow p-5">
                        <h5 class="card-title">Inicio de sesión</h5>
                        <hr>
                        <form action="Validar" method="post">

                        <div class="mb-3 row">

                            <label class="col-12 col-lg-3 col-form-label ">Email:</label>
                            <div class="col-10 col-lg-8 ">
                                <input type="text"  class="form-control"  name="correo">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="col-12 col-lg-3 col-form-label">Contraseña:</label>
                            <div class="col-10 col-lg-8 ">
                                <input type="password" class="form-control" name="txtpass">
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="d-flex justify-content-lg-center">
                                <input type="submit" class="btn btn-primary col-12 col-lg-8" value="Ingresar" name="accion" >
                            </div>
                        </div>

                    </div>

                </div>
                </form>

                <div class="card-footer text-muted text-center">
                    <a href="#" class="card-link">Olvidaste tu contraseña</a>
                </div>
            </div>
        </div>

    </div>


</main>
<!-- el main lleva articles -->
<aside>
    <!-- para poner datos a un lado del articulo -->
</aside>

<!-- tablas para recaudar los mismo datos por columnas  -->
<div class="container my-4">
    <footer class="blockquote-footer ">
        Proyecto Integradora , Universidad Tecnologica Emiliano Zapata
        <!-- es el pie d pagina -->
    </footer>
</div>
<script
        src="js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"
></script>
</body>
</html>