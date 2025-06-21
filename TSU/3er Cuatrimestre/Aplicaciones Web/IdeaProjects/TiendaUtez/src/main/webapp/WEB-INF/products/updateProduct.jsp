<%@ page import="utez.tienda.tiendautez.products.gestion.model.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="utez.tienda.tiendautez.products.gestion.model.CombinationPDBean" %>
<%@ page import="utez.tienda.tiendautez.category.model.CategoryBean" %><%--
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
    ProductBean beanProduct= (ProductBean) request.getAttribute("product");
    List<CategoryBean> listCategory= (List<CategoryBean>) request.getAttribute("listCategory");
%>
<html>
<head>
    <title>GESTION PRODUCTOS</title>
    <meta http-equiv="content-type" content="text/html; utf-8">

    <%@include file="/template/header.jsp"%>
    <!-- Bootstrap CSS -->
    <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">-->
    <link href="${pageContext.request.contextPath}/assets/bootstrap-5.2.0/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/cssOriginal/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/cssOriginal/font-awesome.min.css">
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richText/richtext.min.css">
    <!--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/Jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/richText/jquery.richtext.js"></script>

</head>
<body>
<%@include file="/WEB-INF/administrator/templateAdmin/navbar.jsp"%>


<div class="p-4 mt-1 rounded rounded-3 " id="fondo">

    <div id="fondo-blanco" class="rounded p-4 shadow">
        <!-- El fondo blanco -->
        <main class="container-fluid   ">
            <!-- Contenedor principal-->

            <!----------- ----------------------------Inica formulario-------------------------------------------------- -->

            <form class="row" action="ProductServlet?action=updateNormal" method="post" enctype="multipart/form-data">
                <!---------------------- Lado izquierdo ----------------------------->

                <input type="hidden" name="id" value="<%=beanProduct.getId_products()%>">
                <div class="col col-lg-5 col-md-6 col-sm-12 col-12 ">

                    <div class="mt-2 sticky-top">
                        <div class="row  ">
                            <label  for="nameProduct" class="form-label">Nombre del producto</label>
                            <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3">
                                <input type="text" class="form-control form-control-lg fs-4"
                                       placeholder="Ingrese el nombre " id="nameProduct" name="nameProducts" value="<%=beanProduct.getName()%>">
                            </div>

                            <div class="col-12 col-lg-11 col-md-12 p-0 px-md-2 ">
                                <label  for="shortDescription" class="form-label">Descripción breve del producto</label>
                                <textarea name="shortDescription" class="form-control col-12 col-lg-11 col-md-12" rows="4" placeholder="Caracteristicas generales del producto" id="shortDescription" ><%=beanProduct.getDescription()%></textarea>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- ------------------ Lado derecho ------------------------  -->
                <div class="col col-lg-7 col-md-6 col-sm-12 col-12 px-lg-3 ">

                    <!-- --------------------Fin Datos Generales -------------- -->
                    <!-- --------------------Productios generale s -------------- -->
                    <div class="row">
                        <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 " >
                            <label class="input-text h5 text-uppercase" for="categorias">Categorías</label>
                            <select class="form-select " id="categorias"   name="category"      >
                                <option value="<%=beanProduct.getCategory().getId_category()%>"><%=beanProduct.getCategory().getName().toUpperCase()%></option>
                                <% for (CategoryBean category: listCategory) {
                                    if (beanProduct.getCategory().getId_category()!=category.getId_category()){%>
                                <option value="<%= category.getId_category()%>"><%= category.getName().toUpperCase()%></option>
                                   <%  }
                                }%>

                            </select>
                        </div>

                        <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 " >
                            <h5>Descripción Larga</h5>
                            <textarea rows="4" name="longDescription" class=" content  border border-success rounded" id="longDescription"><%=beanProduct.getDescriptionLong()%></textarea>
                        </div>


                        <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 " >
                            <div class=" d-flex justify-content-end">
                                <button type="button"  class="btn   btn-block  m-2 btn-primary" id="boton-azul" >Regresar</button>
                                <button type="submit"  class="btn   btn-block  m-2 btn-primary enviar" id="boton-azul" >Guardar y seguir</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!---------------------- Texto Eeriqueizoooooooooooo ------------------->
            </form>
            <!----------- ----------------------------Termina formulario-------------------------------------------------- -->
        </main>
    </div>
</div>
<script>
    $(()=> {
        $('.content').richText();
    })
</script>

<%@include file="/template/footer.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/typewriter-effect@latest/dist/core.js"></script>



</body>
</html>

