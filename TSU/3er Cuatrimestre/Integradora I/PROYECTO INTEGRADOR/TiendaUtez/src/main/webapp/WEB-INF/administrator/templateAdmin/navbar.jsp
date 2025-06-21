<%--
  Created by IntelliJ IDEA.
  User: joelh
  Date: 06/08/2022
  Time: 01:53 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- //////////////////NAVBAR -->
<!-- //////////////////NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-light" id="menu">
    <div class="container-fluid">
        <a class="navbar-brand" href="ProductServlet?accion=getProducts" id="nav-imagen">
            <img src="${pageContext.request.contextPath}/assets/images/logo.svg" alt="" />
        </a>
        <!-- Botón desplegable -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" >
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-1 mb-lg-0">
                <li class="nav-item">
                    <!-- Empieza lista -->
                    <a class="nav-link" aria-current="page" href="getOffers">Ofertas</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="ProductServlet?accion=getProducts">Inventario</a>
                </li>

                <%-- if (rol==1){  --%>
                <li class="nav-item">
                    <a class="nav-link active" href="AdminServlet?accion=admins">Administradores</a>
                </li>
                <%--}--%>

                <li class="nav-item">
                    <a class="nav-link" href="#"
                    >Cambiar contraseña</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="close" href="javascript:void(0)">Cerrar sesión</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script type="text/javascript">
    $(document).ready(()=>{
        console.log("Jquery")
        //Cerrar sesión
        $("#close").click(()=>{
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Sesión cerrada',
                showConfirmButton: false,
                timer: 1300
            }).then(()=>{
                window.location.href = "AdminServlet?accion=cerrarSesion"
            })
        })
    })
</script>
