<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header >
    <nav class="navbar navbar-expand-lg navbar-light " id="menu">
        <div class="container-fluid ">
            <a class="navbar-brand" href="ProductsUserServlet?accion=inicio" id="nav-imagen">
                <img src="${pageContext.request.contextPath}/assets/images/Logo-utez.webp" alt="">
            </a>

            <form class="d-flex w-50" role="search" action="ProductsUserServlet" method="get">
                <input autocomplete="off" class="form-control me-2" type="search" placeholder="Buscar Producto" aria-label="Search" name="buscar">
                <button class="btn btn-secondary" type="submit" name="accion" value="search">Buscar</button>
            </form>

<!--
            <a class="nav-link dropdown-toggle text-light col" href="#" id="enlace" role="button"
               data-bs-toggle="dropdown" aria-expanded="false">
                |Categorias
            </a>

            <ul class="dropdown-menu" aria-labelledby="enlace">
                <li><a class="dropdown-item" href="#">Categoria</a></li>
                <li><a class="dropdown-item" href="#">Categoria</a></li>
                <li><a class="dropdown-item" href="#">Categoria</a></li>
            </ul>

            -->
            <div class="d-flex justify-content-end">
                <a href="http://www.utez.edu.mx/" class="nav-link mx-3" id="enlace">Universidad</a>
                <a href="AdminServlet?accion=login" class="nav-link  text-end mx-2" id="enlace">Iniciar sesión</a>
            </div>


        </div>
    </nav>
</header>




