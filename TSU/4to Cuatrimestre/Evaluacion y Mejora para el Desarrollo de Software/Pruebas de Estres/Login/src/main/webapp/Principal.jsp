<%--
  Created by IntelliJ IDEA.
  User: joelh
  Date: 21/07/2022
  Time: 05:19 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String pass=(String)(session.getAttribute("pass"));
    String correo=(String)(session.getAttribute("correo"));
    if(correo != null && pass !=null){
        System.out.println("sesión: Correo: " + correo + " pass: " +pass);

%>
<html>
<head>
    <title>Inicio</title>
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
                        <h5 class="card-title">Cerarr Sesión</h5>
                        <hr>
                            <div class="row mt-2">
                                <form action="Validar" method="post">
                                <div class="d-flex justify-content-lg-center">
                                    <input type="submit" class="btn btn-primary col-12 col-lg-8" value="Cerrar Sesion" name="accion" >
                                </div>
                                </form>
                            </div>

                    </div>

                </div>
                <

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
<% }else{
    System.out.println("No hay sesión iniciada!");
    request.getRequestDispatcher("index.jsp").forward(request,response);

}
%>
