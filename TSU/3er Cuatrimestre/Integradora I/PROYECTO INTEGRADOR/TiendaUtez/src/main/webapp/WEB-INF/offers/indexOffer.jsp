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


<html>
<head>
    <title>GESTION ADMINISTRADORES</title>
    <%@include file="/template/header.jsp"%>
</head>
<body>
<%@include file="../administrator/templateAdmin/navbar.jsp"%>
<link href="${pageContext.request.contextPath}/assets/dataTables/datatables.min.css" rel="stylesheet" >
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
            <div class="col col-lg-10 col-md-8 col-sm-12 col-12  p-2">
                <h1 id="titulo">Ofertas</h1>
            </div>

            <div class="col col-lg-2 col-md-4 col-sm-12 col-12 p-2">
                <a class="btn btn-secondary" href="createOffer" role="button">  Agregar oferta</a>
            </div>

        </div>
        <!-- /*
////////////////////////////////////////////////////
              Empieza tabla
////////////////////////////////////////////////////
*/ -->

        <div class="row">
            <div class="col col-12">
                <table class="table" id="tabla">
                    <thead>
                    <tr class="text-light">
                        <th scope="col"> Id</th>
                        <th scope="col"> Nombre de la oferta</th>
                        <th scope="col"> Imagen</th>
                        <th scope="col"> Descuento %</th>
                        <th scope="col"> Fecha de inicio</th>
                        <th scope="col"> Fecha de fin</th>
                        <th scope="col"> Gestión</th>
                    </tr>

                    </thead>

                    <tbody>
                    <c:forEach items="${offerList}" var="offer" varStatus="status">
                        <tr>
                            <td><c:out value="${status.count}"></c:out></td>
                            <td><c:out value="${offer.name}"></c:out></td>
                            <td><img src="data:banner/jpeg;base64, ${offer.banner}" style="width: 80px;"> </td>
                            <td><c:out value="${offer.discount}"></c:out></td>
                            <td><c:out value="${offer.start_date}"></c:out></td>
                            <td><c:out value="${offer.end_date}"></c:out></td>
                            <td>
                                <div class="row">
                                    <div class="col-12 mb-1">
                                        <a class="btn btn-primary" href="getOffer?id_offers=${offer.id_offers}"
                                           role="button">Editar</a>
                                        <a class="btn btn-danger btn-small"
                                           data-bs-toggle="modal" data-bs-target="#deleteOffer-${offer.id_offers}"
                                        >Eliminar</a>
                                        <a class="btn btn-danger btn-small"
                                           data-bs-toggle="modal" data-bs-target="#indexBanner-${offer.id_offers}"
                                        >Gestionar imagen</a>
                                    </div>
                                    <div class="col-12">
                                        <a class="btn btn-primary" href="addOffer?id_offers=${offer.id_offers}"
                                           role="button">Agregar productos a oferta</a>
                                    </div>
                                </div>



                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- aqui empieza el modal-->
            <!-- Modal  para eliminar una oferta-->
            <c:forEach items="${offerList}" var="offer" varStatus="status">
                <div class="modal fade" id="deleteOffer-${offer.id_offers}" tabindex="-1" aria-labelledby="deleteOfferLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form method="post" action="deleteOffer">
                                <input type="hidden" name="id_offers" id="id_offers" value="${offer.id_offers}"/>
                                <div class="modal-header">
                                    <h5 class="modal-title" id="deleteOffertLabel">Confirmar Eliminación</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    ¿Deseas eliminar a la oferta?
                                    <c:out value="${offer.name}"></c:out>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <button type="submit" class="btn btn-primary">Confirmar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!-- Modal -->
            <!-- aqui termina el modal-->


            <!-- aqui empieza el modal-->
            <!-- Modal para gestionar/ver la imagen-->
            <c:forEach items="${offerList}" var="offer" varStatus="status">
                <div class="modal fade" id="indexBanner-${offer.id_offers}" tabindex="-1" aria-labelledby="indexBannerLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form method="post" action="indexBanner">
                                <input type="hidden" name="id_offers" id="id_offers" value="${offer.id_offers}"/>
                                <div class="modal-header">
                                    <h5 class="modal-title" id="indexBannerLabel">Confirmar Eliminación</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <div class="row">
                                        <div class="col-12">
                                            <img src="data:banner/jpeg;base64, ${offer.banner}" style="width: 100%;" class="shadow my-2 mb-3">
                                        </div>

                                    </div>


                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <a class="btn btn-danger btn-small"
                                       data-bs-toggle="modal" data-bs-target="#updateBanner-${offer.id_offers}"
                                    >Cambiar imagen</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!-- Modal -->
            <!-- aqui termina el modal-->




            <!-- aqui empieza el modal-->
            <!-- Modal para actualizar la imagen-->
            <c:forEach items="${offerList}" var="offer" varStatus="status">
                <div class="modal fade" id="updateBanner-${offer.id_offers}" tabindex="-1" aria-labelledby="updateBannerLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form method="post" action="updateBanner" enctype="multipart/form-data">
                            <!--    <form name="registerOffer" class="needs-validation" novalidate action="updateOffer" id="form" method="post" enctype="multipart/form-data"> -->
                                <input type="hidden" name="id_offers" id="id_offers" value="${offer.id_offers}"/>
                                <div class="modal-header">
                                    <h5 class="modal-title" id="updateBannerLabel">Confirmar Eliminación</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                        <div class="col-lg-12 col-md-6 col-sm-12 col-12 mt-3">

                                                <label for="banner" class="form-label">Ingresa nueva imagen</label>
                                                <input class="form-control form-control-md w-100" id="banner" name="banner" type="file" value="${offer.banner}">


                                        </div>



                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                    <button class="btn btn-info m-3" id="boton-azul" type="submit" style="background-color: #dc3545;">Guardar</button>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!-- Modal -->
            <!-- aqui termina el modal-->


            <!-- /*
////////////////////////////////////////////////////
                  Empieza paginacion
////////////////////////////////////////////////////
*/ -->

        </div>



    </main>




</div>















<!-- /*
////////////////////////////////////////////////////
                SERVICIOS
////////////////////////////////////////////////////
*/ -->



<!-- /*
////////////////////////////////////////////////////
                NOSOTROS
////////////////////////////////////////////////////
*/ -->


<!-- /*
////////////////////////////////////////////////////
         FONDO       FORMULARIO
////////////////////////////////////////////////////
*/ -->


<!-- /*
////////////////////////////////////////////////////
       FORMULARIO
////////////////////////////////////////////////////
*/ -->



<!--

/////////////////
-->
</div>

<!-- /*
    ////////////////////////////////////////////////////
           pie de pagina
    ////////////////////////////////////////////////////
    */ -->

<!-- **************************************************************AQUI TERMINA LA VISTA***********************************************************  -->


<%@include file="/template/footer.jsp"%>
<%@include file="../administrator/templateAdmin/linksScripts.jsp"%>
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
