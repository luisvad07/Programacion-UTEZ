<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="../../template/header.jsp"/>

    <!-- Recursos que se van a utilizar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/cssOriginal/googleapis.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/fonts/icomoon/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/styles.css">    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/bootstrap.min.css">    <!-- Style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/login/css/style.css">


    <title>Login </title>
    <meta http-equiv="content-type" content="text/html; utf-8">

  </head>
  <body>

  <%
    //Variables para recibir el resultado
    String nombre = request.getParameter("name");
    String rol = request.getParameter("rol");
    String fallo = request.getParameter("fail");
  %>


  <div class="content">
    <div class="container">
      <div class="row">

        <div class="col-md-6 order-md-2">
          <a href="ProductsUserServlet">
          <img src="${pageContext.request.contextPath}/assets/images/logo.svg" alt="Image" class="img-fluid">
          </a>
        </div>

        <div class="col-md-6 contents">
          <div class="row justify-content-center">

            <div class="col-md-8">
              <div class="mb-4">
              <h3> <strong>Iniciar  Sesión</strong></h3>
              <p class="mb-4">Acceder a gestionar los productos</p>
            </div>

            <form action="AdminServlet" method="post">
              <div class="form-group first">
                <label for="username"><i class="fa-solid fa-envelope"></i>Correo</label>
                <input type="email" class="form-control" id="username" name="email">
              </div>

              <div class="form-group last mb-4">
                <label for="password"> <i class="fa-solid fa-key"></i> Contraseña</label>
                <input type="password" class="form-control" id="password" name="password">
              </div>

              <button type="submit" id="logoo" value="validarAdmin" name="accionPost" class="btn text-white btn-block btn-primary">Iniciar sesión</button>
              <a href="AdminServlet?accion=rememberPsw" class="btn text-white btn-block btn-primary text-decoration-none">Olvidaste tu contraseña</a>
            </form>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>

    $(document).ready(()=>{

      <% if (nombre!=null && fallo==null){  //Validamos que este null para que no muestre nada sin un resultado o accion antes%>

       Swal.fire({
          icon: 'success',
          title: 'Bienvenido',
          html :"<b class='text-secondary' h4'>Hola <%= nombre %></b> ",
          showConfirmButton: false,
          timer: 1000,
       }).then(() => {
         window.location.href = "AdminServlet?accion=acceder&rol=<%=rol%>"
       })

      <% }else if (fallo!=null && nombre==null){%>

          Swal.fire({
              icon: 'error',
              title: 'Fallo!',
              text:" <%= fallo %>",
          showConfirmButton: false,
                  timer: 1500,
        })

     <%}%>
    })
  </script>

  <script src="${pageContext.request.contextPath}/assets/login/js/jquery-3.3.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/login/js/popper.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/login/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/login/js/main.js"></script>


  </body>
</html>