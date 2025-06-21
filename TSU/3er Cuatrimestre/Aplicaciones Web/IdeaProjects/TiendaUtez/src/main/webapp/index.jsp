<%@ page import="java.util.List" %>
<%@ page import="utez.tienda.tiendautez.products.normal.model.ProductUserBean" %>
<%@ page import="utez.tienda.tiendautez.offers.model.BeanOffer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta http-equiv="content-type" content="text/html; utf-8">
    <%@include file="/template/header.jsp"%>

</head>
<body>
<%@include file="/productsUser/templateUser/navarUser.jsp"%>
<!--Aquí recibimos la lista de los datos consultados en el PRODUCT USER DAO-->
<%
    List<ProductUserBean> products = (List<ProductUserBean>) request.getAttribute("products");
    List<BeanOffer> offer = (List<BeanOffer>) request.getAttribute("offers");
%>

<div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">

        <div class="carousel-item active" data-bs-interval="5900">
            <a href="ProductsUserServlet?accion=banner&idBanner=<%= offer.get(0).getId_offers() %>">
                <img height="280px " src="data:banner/jpeg;base64,  <%= offer.get(0).getBanner() %>"     class="d-block w-100" alt="...">

            </a>
        </div>


        <% for (int i = 1; i < offer.size(); i++) { %>


        <div class="carousel-item" data-bs-interval="5900">
            <a href="ProductsUserServlet?accion=banner&idBanner=<%= offer.get(i).getId_offers() %>">
                <img height="280px " src="data:banner/jpeg;base64,  <%= offer.get(i).getBanner() %>"   class="d-block w-100" alt="...">
            </a>
        </div>

       <% } %>

    </div>

    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
<!-- ---------Fin carrusel ---------------- -->

<div class="p-3 pt-1 rounded rounded-3" id="fondo">
    <div id="fondo-blanco" class="rounded p-4 shadow">
        <!-- El fondo blanco -->
        <main class="container-fluid">
            <!-- Contenedor principal-->
            <h1 id="titulo" class=" text-center">Productos</h1>

            <div class="container mt-3">

                <div class="row  d-flex justify-content-lg-start justify-content-md-between ">
                    <div class="col-12 col-lg-3 col-md-5 d-flex justify-content-center my-2    ">
                        <a href="ProductServletUser?accion=getProduc&id=" class="card position-relative text-decoration-none bordes shadow" style="max-width: 277.84px;">

                            <span class="position-absolute  badge rounded-1  m-2 px-3 bg-success">Oferta</span>


                            <img width="240px" height="240px" src="https://www.appyweb.es/wp-content/uploads/2020/11/gifs-ejemplo.gif"  alt="Imagen" class="card-img-top bordes">

                            <div class="card-body">
                                <h2 class="card-title fs-5"></h2>

                                <h3 class="fs-6 my-3">En mantenimiento... </h3>

                                <p class="card-text text-secondary">

                                </p>
                            </div>
                            <!-- precio -->
                        </a>
                    </div>


<!------------------Recibios la lista y la recorremos ------------------->
                    <% for (ProductUserBean producte: products) {

                        double precio = producte.getPrice(); %>
                    <!---------------Inicilizamos con el primer precio por si hay descuento ------------------->


                    <div class="col-12 col-lg-3 col-md-5 d-flex justify-content-center my-2    ">
                        <a href="ProductsUserServlet?accion=getProduc&id=<%=producte.getId_products()%>" class="card position-relative text-decoration-none bordes shadow" style="max-width: 277.84px;">
                            <!---Entra si hay oferta -->
                            <%if (producte.getOffers_id_offers()!=0){
                                precio =producte.getPrice() - ( producte.getPrice()* producte.getOffer().getDiscount()/100 );%>
                            <span class="position-absolute  badge rounded-1  m-2 px-3 bg-success">Oferta</span>
                            <% } %>
                            <img width="240px" height="240px" src="data:banner/jpeg;base64, <%= producte.getImageToShow()%>"  alt="Imagen" class="card-img-top bordes">

                            <div class="card-body">
                                <h2 class="card-title fs-5 text-dark"><%= producte.getName() %></h2>

                                <h3 class="fs-6 my-3 ">$ <%= precio  %>...</h3>

                                <p class="card-text text-secondary">
                                    <%= producte.getDescription() %>
                                </p>
                            </div>
                            <!-- precio -->
                        </a>
                    </div>


                    <% } %>

                </div>

            </div>

        </main>

    </div>
</div>



<!-----------------Links para el funcionamiento de la página -->
<%@include file="/productsUser/templateUser/linksScript.jsp"%>
<%@include file="/template/footer.jsp"%>
</body>
</html>