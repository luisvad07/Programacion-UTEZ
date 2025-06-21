<%@ page import="utez.tienda.tiendautez.products.gestion.model.ProductBean" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: HORUS
  Date: 10/08/2022
  Time: 08:36 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<%
        List<ProductBean> products= (List<ProductBean>) request.getAttribute("productos");
        Long idOffersAplicaty = (Long) request.getAttribute("id_offers2");
        int id_product=0;
%>
<html>
<head>
    <title>GESTION ADMINISTRADORES</title>
    <%@include file="/template/header.jsp"%>
</head>
<body>
<%@include file="../administrator/templateAdmin/navbar.jsp"%>
<!-- *********************************************************AQUI EMPIEZA LA VISTA******************************************************  -->
<!-- /*
////////////////////////////////////////////////////
              Empieza pagina
////////////////////////////////////////////////////
*/ -->
<!-- w=tamaño que abarca el texto   mx=posicion del texto -->

<div id="fondo-blanco" class="rounded p-4 shadow">
    <!-- El fondo blanco -->
    <main class="container-fluid">
        <!-- Contenedor principal-->
        <div class="row ">
            <div class="col col-lg-3 col-md-3 col-sm-12 col-12  p-2">
                <h1 id="titulo">Información de la oferta</h1>

            </div>

            <div class="col col-lg-2 col-md-4 col-sm-12 col-12 p-2">
                <a class="btn btn-secondary" href="createOffer" role="button">Nueva Oferta</a>


            </div>

        </div>
        <!-- /*
////////////////////////////////////////////////////
              Empieza tabla
////////////////////////////////////////////////////
*/ -->

        <div class="row">
            <div class="col col-12">
                <table class="table" >
                    <thead>
                    <tr class="text-dark">
                        <th scope="col"> Id</th>
                        <th scope="col"> Nombre de la oferta</th>
                        <th scope="col"> Imagen</th>
                        <th scope="col">  %</th>
                        <th scope="col"> Fecha de inicio</th>
                        <th scope="col"> Fecha de fin</th>
                    </tr>

                    </thead>

                    <tbody>
                        <tr>
                            <td><c:out value="${status.count}"></c:out></td>
                            <td><c:out value="${offer.name}"></c:out></td>
                            <td><img src="data:banner/jpeg;base64, ${offer.banner}" style="width: 80px;"> </td>
                            <td><c:out value="${offer.discount}"></c:out></td>
                            <td><c:out value="${offer.start_date}"></c:out></td>
                            <td><c:out value="${offer.end_date}"></c:out></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- tabla de productos joel-->

            <!-- tabla de productos sin agregar-->
            <div class="row">
                <div class="col col-6">
                    <table class="table" id="tabla">
                        <thead>
                        <tr class="text-light">
                            <th scope="col">Nombre</th>
                            <th scope="col">Imagen </th>
                            <th scope="col">Categoría </th>
                            <th scope="col">Acciones </th>
                        </tr>

                        </thead>
                        <tbody>
                        <% for (ProductBean produc: products) {  //Lista general de los productos
                            String offer = produc.getOffers_id_offers()==0?"No aplica":produc.getOffer().getName();
                            String status = produc.getStatus()==0?"No publicado":"Publicado";
                            if (produc.getStatus()==1 & produc.getDelete()==0){
                                if (produc.getOffers_id_offers()==0){


                        %>
                        <tr>
                            <td><%= produc.getName()%></td>
                            <td><%= produc.getCategory().getName()%></td>
                            <td><img src="data:image/jpeg;base64, <%= produc.getImageToShow()%> " class="img-fluid" alt="Imagen<%= produc.getName()%>" style="width: 80px;"></td>
                            <td>
                                <a class="btn btn-primary" href="ProductServlet?accion=addOfferProduct&id_products=<%=produc.getId_products()%>&id_offers=${offer.id_offers}">Agregar</a>
                            </td>
                        </tr>

                        <% }}
                        }%>

                        </tbody>
                    </table>

                </div>
                <!-- tabla de productos agregados-->
                <div class="col col-6">
                    <table class="table" id="tabla">
                        <thead>
                        <tr class="text-light">
                            <th scope="col">Nombre</th>
                            <th scope="col">Imagen </th>
                            <th scope="col">Categoría </th>
                            <th scope="col">Acciones </th>
                        </tr>

                        </thead>
                        <tbody>
                        <% for (ProductBean produc: products) {  //Lista general de los productos
                            String offer = produc.getOffers_id_offers()==0?"No aplica":produc.getOffer().getName();
                            String status = produc.getStatus()==0?"No publicado":"Publicado";
                            if (produc.getStatus()==1 & produc.getDelete()==0){
                                if (produc.getOffers_id_offers()== idOffersAplicaty){ //aqui como puedo comparar con el recuperado?


                        %>
                        <tr>
                            <td><%= produc.getName()%></td>
                            <td><%= produc.getCategory().getName()%></td>
                            <td><img src="data:image/jpeg;base64, <%= produc.getImageToShow()%> " class="img-fluid" alt="Imagen<%= produc.getName()%>" style="width: 80px;"></td>
                            <td>
                                <a class="btn btn-primary" href="ProductServlet?accion=deleteOfferProduct&id_products=<%=produc.getId_products()%>&id_offers=${offer.id_offers}">Quitar</a>
                            </td>
                        </tr>

                        <% }}
                        }%>

                        </tbody>
                    </table>

                </div>
            </div>



    </main>

</div>

</div>


<!-- **************************************************************AQUI TERMINA LA VISTA***********************************************************  -->

<%@include file="/template/footer.jsp"%>
<%@include file="../administrator/templateAdmin/linksScripts.jsp"%>
</body>
</html>
