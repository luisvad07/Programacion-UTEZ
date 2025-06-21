<%@ page import="utez.tienda.tiendautez.products.gestion.model.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="utez.tienda.tiendautez.products.gestion.model.CombinationPDBean" %><%--
  Created by IntelliJ IDEA.
  User: joelh
  Date: 14/08/2022
  Time: 02:41 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
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
    String username=(String)(session.getAttribute("username"));
    String email=(String)(session.getAttribute("email"));
    Integer rol=(Integer)(session.getAttribute("rol"));
    ///Sesiones para ver quien entra a esta interfaz

    if(email != null && username !=null ){ //Verificamos
        System.out.println(" pass: " +username + " rol "+ rol);

        //Listamos todos los productos
        List<ProductBean> products= (List<ProductBean>) request.getAttribute("productos");
        int id_product=0;
%>


<html>
<head>
    <title>GESTION PRODUCTOS</title>
    <meta http-equiv="content-type" content="text/html; utf-8">
    <%@include file="/template/header.jsp"%>
    <link href="${pageContext.request.contextPath}/assets/dataTables/datatables.min.css" rel="stylesheet" >


</head>
<body>
<%@include file="/WEB-INF/administrator/templateAdmin/navbar.jsp"%>


<div class="p-3 rounded rounded-3" id="fondo">
    <div id="fondo-blanco" class="rounded p-4 shadow">
        <main class="container-fluid">

            <div class="row ">
                <div class="col col-lg-10 col-md-8 col-sm-12 col-12  p-2">
                    <h1 id="titulo">Inventario Halcón</h1>
                </div>

                <div class="col col-lg-2 col-md-4 col-sm-12 col-12 p-2">
                    <a class="btn btn-secondary" href="ProductServlet?accion=createProducts" role="button">Nuevo producto</a>
                </div>
            </div>

            <!--------------------------------------------------------------------------------------------------->

            <div class="row">
                <div class="col col-12">
                    <table class="table" id="tabla">
                        <thead>
                        <tr class="text-light">
                            <th scope="col">Nombre</th>
                            <th scope="col">Categoría </th>
                            <th scope="col"> Oferta</th>
                            <th scope="col">Estado </th>
                            <th scope="col">Acciones </th>
                        </tr>

                        </thead>
                        <tbody>
                        <% for (ProductBean produc: products) {  //Lista general de los productos
                            String offer = produc.getOffers_id_offers()!=0?produc.getOffer().getName():"No aplica";
                            String status = produc.getStatus()==0?"No publicado":"Publicado";


                            String a = !offer.isEmpty()?"No aplica":"No vacio";
                            System.out.println(a);

                            if (produc.getDelete()==0){
                        %>
                        <tr>
                            <td><%= produc.getName()%></td>
                            <td><%= produc.getCategory().getName()%></td>
                            <td><%= offer%></td>
                            <td><%= status%></td>

                            <td>
                                <a class="btn btn-outline-primary" data-bs-toggle="modal" href="#exampleModalToggle<%=produc.getId_products()%>" role="button"><i class="fa-solid fa-eye"></i></a>
                                <a class="btn btn-primary " href="ProductServlet?accion=findProductUpdate&id=<%=produc.getId_products()%>" role="button"><i class="fa-solid fa-file-pen"></i></a>
                                <!--<a class="btn btn-dark" href=""   role="button"> <i class="fa-solid fa-image"></i></a>-->


                                <% if (produc.getStatus()==0){ %>
                                <a class="btn btn-success" href="ProductServlet?accion=updateStatus&id=<%=produc.getId_products()%>&status=1"   role="button"><i class="fa-solid fa-circle-arrow-up"></i></a>
                                <%}else{%>
                                <a class="btn btn-danger" href="ProductServlet?accion=updateStatus&id=<%=produc.getId_products()%>&status=0"   role="button"><i class="fa-solid fa-circle-arrow-down"></i></a>
                                <%}%>

                                <button class="btn btn-outline-danger delete"   value="<%= produc.getId_products() %>"  role="button"><i class="fa-solid fa-trash"></i></button>
                            </td>
                        </tr>

                        <% }
                        }%>

                        </tbody>
                    </table>

                </div>
            </div>

            <!--------------------------------------------------------------------------------------------------->
        </main>
    </div>
</div>


<!--....................................Modal para visualizar ------------------------------->
<%

    for (ProductBean produc: products) {  //Lista general de los productos

%>
<div class="modal fade" id="exampleModalToggle<%=produc.getId_products()%>" aria-labelledby="exampleModalToggleLabel" tabindex="-1" aria-hidden="true" style="display: none;">
    <div class=" modal-dialog modal-dialog-scrollable modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalToggleLabel">Combinaciones</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <% List<CombinationPDBean> combination = produc.getCombinations(); //Lista de las combinaciones de productos
                    String color="";
                    for (CombinationPDBean combina:combination) { //Recorro las combination

                        if (!combina.getColor().equalsIgnoreCase(color)){
                            color=combina.getColor();
               %>

                <div class="row">
                    <div class="col-12">
                        <p class="text-center h5 alert alert-success m-1 p-1"><%=combina.getColor().toUpperCase()%></p>
                    </div>
                </div>

                <div class="row">
                    <div class="col-4">
                        <p class="text-center  border-bottom border-primary  m-1 mb-0">TAMAÑO</p>
                    </div>
                    <div class="col-4">
                        <p class="text-center border-bottom  border-primary  m-1 mb-0">PRECIO</p>
                    </div>
                    <div class="col-4">
                        <p class="text-center border-bottom border-primary  m-1 mb-0">PIEZAS</p>
                    </div>
                </div>

                <% }%>

                <div class="row">
                    <div class="col-4">
                        <p class="text-center m-0 p-0"><%= combina.getSize().toUpperCase() %></p>
                    </div>
                    <div class="col-4">
                        <p class="text-center m-0 p-0 "><%= combina.getPrice() %></p>
                    </div>
                    <div class="col-4">
                        <p class="text-center  m-0 p-0"><%= combina.getPieces() %></p>
                    </div>
                </div>

                <%

                    } %>

            </div>
            <div class="modal-footer p-1">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<%

    }
%>

<%@include file="/WEB-INF/administrator/templateAdmin/linksScripts.jsp"%>
<%@include file="/template/footer.jsp"%>
<script>
    $(()=> {
        $('.delete').click(function(e){

            Swal.fire({
                icon:'question',
                text: '¿Seguro quieres eliminar temporalmente?',
                showCancelButton: true,
                confirmButtonText: 'Seguro',
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        icon: 'success',
                        text: 'Eliminado correctamente',
                        showConfirmButton: false,
                        timer: 1000
                    }).then(() => {
                        window.location.href="ProductServlet?accion=dropRowLogic&id="+ $(this).val()+"&delete=1";
                    })
                }

            })
        });


    })
</script>
<script src="${pageContext.request.contextPath}/assets/dataTables/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#tabla').DataTable({
            language: {
                url: '${pageContext.request.contextPath}/assets/dataTables/spanish-datatables.json'
            }
        });
    });
</script>
</body>
</html>

<% }else{
    //  System.out.println("No hay sesión iniciada!");
    request.getRequestDispatcher("AdminServlet?accion=login").forward(request,response);
}%>


