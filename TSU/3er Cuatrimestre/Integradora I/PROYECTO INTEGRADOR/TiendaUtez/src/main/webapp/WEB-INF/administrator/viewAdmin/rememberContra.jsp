<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="/template/header.jsp"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/fonts/icomoon/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/styles.css">    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/bootstrap.min.css">    <!-- Style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/style.css">



    <title>Recuperacion de Contraseña </title>
</head>
<body>

<%

    String result = (request.getParameter("result"));


%>


<div class="content">
    <div class="container">
        <div class="row">
            <div class="col-md-6 order-md-2">
                <img src="${pageContext.request.contextPath}/assets/images/logo.svg" alt="Image" class="img-fluid">
            </div>

            <div class="col-md-6 contents">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="mb-4">
                            <h3>Recuperacion <strong> Contraseña</strong></h3>
                            <p class="mb-4">Escribe tu correo electronico</p>
                        </div>



                        <form action="AdminServlet?accionPost=sendEmail" method="post">
                            <div class="form-group first">
                                <label for="username">email</label>
                                <input type="email" class="form-control" id="username" name="email">

                            </div>

                            <button type="submit" id="Remember" value="enviarEmail" name="accionPost" class="btn text-white btn-block btn-primary">Confirmar</button>

                        </form>
                    </div>
                </div>

            </div>

        </div>
    </div>
</div>
<script>

    $(document).ready(()=>{

        <% if ( result!=null) {
            if (result.equalsIgnoreCase("true")){ %>
        Swal.fire({
            icon: 'success',
            title: 'CORRECTO',
            text:" Correo enviado",
            showConfirmButton: false,
            timer: 1000,
        })
        <%} else if (result.equalsIgnoreCase("false")) { %>
        Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: "Verifica el dato",
            showConfirmButton: false,
            timer: 1500,
        })
        <%}}%>
    })
</script>

<script src="${pageContext.request.contextPath}/assets/login/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/login/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/login/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/login/js/main.js"></script>


</body>
</html>