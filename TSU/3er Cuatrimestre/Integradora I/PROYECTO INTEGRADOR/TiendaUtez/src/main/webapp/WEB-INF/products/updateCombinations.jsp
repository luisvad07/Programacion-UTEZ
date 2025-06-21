<%@ page import="utez.tienda.tiendautez.products.gestion.model.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="utez.tienda.tiendautez.products.gestion.model.CombinationPDBean" %>
<%@ page import="utez.tienda.tiendautez.images.model.ImagesBean" %><%--
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
    ProductBean product = ( ProductBean ) request.getAttribute("product");

    //System.out.println(    product.toString());
%>


<html>
<head>

    <title>GESTION PRODUCTOS</title>
    <meta http-equiv="content-type" content="text/html; utf-8">
    <%@include file="/template/header.jsp"%>

</head>
<body>
<%@include file="/WEB-INF/administrator/templateAdmin/navbar.jsp"%>
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


<div class="p-4 mt-1 rounded rounded-3 " id="fondo">

    <div id="fondo-blanco" class="rounded p-4 shadow">
        <!-- El fondo blanco -->
        <main class="container-fluid   ">
            <!-- Contenedor principal-->

            <!----------- ----------------------------Inica formulario-------------------------------------------------- -->

            <form class="row"  method="post" enctype="multipart/form-data">
                <!---------------------- Lado izquierdo ----------------------------->
                <div class="col col-lg-5 col-md-6 col-sm-12 col-12 ">

                    <div class="mt-2 sticky-top">
                        <div class="row  ">
                            <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-1">
                                <div class="input-group input-group-sm ">
                                    <span class="input-group-text alert-success alert fw-semibold" id="inputGroup-sizing-sm">Nombre del producto</span>
                                    <input type="text" class="form-control alert " aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm" value="<%= product.getName()%>" readonly >
                                </div>
                            </div>

                            <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-1">
                                <div class="input-group input-group-sm ">
                                    <span class="input-group-text alert-success alert fw-semibold">Categoría</span>
                                    <input type="text" class="form-control alert " aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm" value="<%= product.getCategory().getName()%>" readonly >
                                </div>
                            </div>


                            <div class="col-12 col-lg-11 col-md-12 p-0 px-md-2 mb-3">
                                <span class="input-group-text alert-success alert fw-semibold p-1 m-0">Descripción breve del producto</span>
                                <textarea class="form-control col-12 col-lg-11 col-md-12" rows="3" disabled><%= product.getDescription()%></textarea>
                            </div>



                            <div class="col-12 col-lg-11 col-md-12 p-0 px-md-2 mb-3 ">
                                <span class="input-group-text alert-success alert fw-semibold p-1 m-0 ">Imagenes</span>

                                <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner">


                                        <div class="carousel-item active" data-bs-interval="3000">
                                            <img src="data:image/jpeg;base64, <%= product.getImageToShow()%> " class="img-fluid" alt="Imagen<%= product.getName()%>">
                                            <div class="carousel-caption d-none d-md-block">
                                                <h5>Imagen Principal</h5>
                                            </div>
                                        </div>

                                        <% for (ImagesBean images: product.getImagesSecondaries()){
                                            //System.out.println(images.getImages());
                                        %>
                                        <div class="carousel-item" data-bs-interval="1900">
                                            <img src="data:image/jpeg;base64,<%= images.getImages()%> "  class="d-block w-100" alt="ImagesSecondary<%= images.getImages()%>">
                                            <div class="carousel-caption d-none d-md-block">
                                                <h5>Imagen secundaria</h5>
                                            </div>
                                        </div>
                                        <%}%>

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
                            </div>



                            <div class="col-12 col-lg-11 col-md-12 p-0 px-md-2 ">
                                <span class="input-group-text alert-success alert fw-semibold p-1 m-0 ">Descripción larga</span>
                                <div class="card alert alert-dark">
                                    <%= product.getDescriptionLong()%>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
                <!-- ------------------ Lado derecho ------------------------  -->
                <div class="col col-lg-7 col-md-6 col-sm-12 col-12 px-lg-3 ">

                    <div class="row" >
                        <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >
                            <div class="d-flex">
                                <button type="button" class="btn btn-outline-primary  mt-3 btn-block  vw-100" data-bs-toggle="modal"  data-bs-target="#imagenesSecondary"><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-plus mr-2" viewBox="0 0 16 16"> <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                </svg>Imagen Secundaria</button>
                            </div>
                        </div>
                    </div>



                    <!-- --------------------Productios generale s -------------- -->
                    <div class="row">
                        <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 " >
                            <label class="input-text h5 rounded px-3 pb-1 mb-3 border-bottom border-info text-uppercase" for="tipoProducto">Selecciona el tipo de producto</label>
                            <input type="text" class="form-control border-info" id="tipoProducto" disabled value="<%= product.getType()%>">
                        </div>
                    </div>


                    <section id="compuesto">
                        <div id="formCompu">

                            <% List<CombinationPDBean> combination = product.getCombinations(); //Lista de las combinaciones de productos
                                String color="";
                                for (CombinationPDBean combina:combination) { //Recorro las combination

                                    if (!combina.getColor().equalsIgnoreCase(color)){
                                        color=combina.getColor();
                            %>
                            <div class="row" id="newRow<%= product.getId_products()%>">
                                <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >
                                    <label class="input-text h5 text-uppercase" >Color</label>
                                    <select class="form-select "  name="colorProductos" >
                                        <option value="<%=combina.getColor()%>"><%=combina.getColor()%></option>
                                        <option value="Negro">Negro</option>
                                        <option value="Blanco">Blanco</option>
                                        <option value="Azul">Azul oscuro</option>
                                        <option value="Gris">Gris</option>
                                        <option value="Café">Cafe</option>
                                        <option value="Verde oscuro">Verde oscuro</option>
                                        <option value="Verde">Verde claro</option>
                                        <option value="Amarillo">Amarillo</option>
                                        <option value="Naranja">Naranja</option>
                                        <option value="Rojo">Rojo</option>
                                        <option value="Rosa">Rosa</option>
                                        <option value="Morado">Morado</option>
                                        <option value="Azul claro">Azul claro</option>
                                        <option value="Plateado">Plateado</option>
                                        <option value="Dorado">Dorado</option>
                                    </select>
                                </div>

                                <h6>Unidades por talla</h6>

                                <div class=" col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 rounded-bottom bg-light p-2">
                                    <div class="row">
                                        <!-- ----------------------cHICA---------------- -->
                                        <%for (CombinationPDBean combinadiv:combination) {%>
                                            <% if (combinadiv.getSize().equalsIgnoreCase("CH") && combinadiv.getColor().equalsIgnoreCase(color)){%>

                                                <div class="col-6 col-lg-3">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <label class="form-label">CH</label>
                                                            <input type="number" class="form-control" name="chica" value="<%= combinadiv.getPieces()%>">

                                                        </div>

                                                        <div class="col-12">
                                                            <label  class="form-label">Precio</label>
                                                            <div class="input-group mb-3 ">
                                                                <span class="input-group-text">$</span>
                                                                <input type="number" class="form-control" name="chicaPrecio" value="<%= combinadiv.getPrice()%>">

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- ----------------Fin chica--------------------------- -->
                                                <% } else if (combinadiv.getSize().equalsIgnoreCase("MD") && combinadiv.getColor().equalsIgnoreCase(color)) {%>

                                                <!-- ----------------Mediana -------------------------- -->
                                                <div class="col-6 col-lg-3">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <label  class="form-label">MD</label>
                                                            <input type="number" class="form-control" name="mediana" value="<%= combinadiv.getPieces()%>">

                                                        </div>

                                                        <div class="col-12">
                                                            <label  class="form-label">Precio</label>
                                                            <div class="input-group mb-3 ">
                                                                <span class="input-group-text">$</span>
                                                                <input type="number" class="form-control" name="medianaPrecio" value="<%= combinadiv.getPrice()%>">

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- ----------------Fin mediana --------------------------- -->
                                                <%} else if (combinadiv.getSize().equalsIgnoreCase("G") && combinadiv.getColor().equalsIgnoreCase(color)) {%>

                                               <!-- ----------------Grande--------------------------- -->
                                                <div class="col-6 col-lg-3">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <label  class="form-label">G</label>
                                                            <input type="number" class="form-control" name="grande" value="<%= combinadiv.getPieces()%>" >
        
                                                        </div>
        
                                                        <div class="col-12">
                                                            <label  class="form-label">Precio</label>
                                                            <div class="input-group mb-3 ">
                                                                <span class="input-group-text">$</span>
                                                                <input type="number" class="form-control" name="grandePrecio"  value="<%= combinadiv.getPrice()%>">
        
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- ----------------Fin Grande--------------------------- -->
                                                <%} else if (combinadiv.getSize().equalsIgnoreCase("XG") && combinadiv.getColor().equalsIgnoreCase(color)) {%>

                                                <!-- ----------------Extra grande-------------------------- -->
                                                <div class="col-6 col-lg-3">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <label class="form-label">XG</label>
                                                            <input type="number" class="form-control" name="xgrande" value="<%= combinadiv.getPieces()%>">

                                                        </div>

                                                        <div class="col-12">
                                                            <label class="form-label">Precio</label>
                                                            <div class="input-group mb-3 ">
                                                                <span class="input-group-text">$</span>
                                                                <input type="number" class="form-control" name="xgrandePrecio" value="<%= combinadiv.getPrice()%>" >

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- ----------------Fin ExtraGrande--------------------------- -->
                                            <% } %>
                                        <% } %>
                                    </div>
                                </div>

                                <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >

                                    <div class="d-flex ">

                                        <a class=" mt-3 btn btn-outline-danger vw-100 remove-color" id="<%= product.getId_products()%>">Eliminar</a>


                                    </div>
                                </div>


                            </div>



                            <%}

                            }%>
                        </div>
                        <div class="row" >
                            <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >

                                <div class="d-flex">

                                    <a class="btn mt-3 btn-block  text-light vw-100" type="button" style="background-color: #20c997" id="addColor">Nuevo color</a>


                                </div>
                            </div>
                        </div>
                    </section>

                    <!-- --------------------Fin Productios generale s -------------- -->



                    <!-- ---------------- Inicio producto simple-------------------- -->


                    <section  id="simple">
                        <div class="row" >
                            <%for (CombinationPDBean combinadiv:combination) {

                                for (CombinationPDBean combina:combination) { //Recorro las combination

                            %>




                            <div class="col-12 col-lg-11 col-md-12  p-lg-3  px-md-4 p-2  mb-3 rounded bg-light  " >
                                <label class="input-text h5 text-uppercase" >Color</label>
                                <select class="form-select " id="colorSimple"  >
                                    <option value="<%= combinadiv.getColor()%>"><%= combinadiv.getColor()%></option>
                                    <option value="Negro">Negro</option>
                                    <option value="Blanco">Blanco</option>
                                    <option value="Azul">Azul oscuro</option>
                                    <option value="Gris">Gris</option>
                                    <option value="Café">Café</option>
                                    <option value="Verde oscuro">Verde oscuro</option>
                                    <option value="Verde">Verde claro</option>
                                    <option value="Amarillo">Amarillo</option>
                                    <option value="Naranja">Naranja</option>
                                    <option value="Rojo">Rojo</option>
                                    <option value="Rosa">Rosa</option>
                                    <option value="Morado">Morado</option>
                                    <option value="Azul claro">Azul claro</option>
                                    <option value="Plateado">Plateado</option>
                                    <option value="Dorado">Dorado</option>
                                </select>

                                <label class="input-text h5 text-uppercase" >Cantidad</label>
                                <input type="text" class="form-control"  id="cantidadSimple"  value="<%= combinadiv.getPieces()%>" >

                                <label class="input-text h5 text-uppercase" >Precio</label>
                                <input type="text" class="form-control" id="precioSimple" value="<%= combinadiv.getPrice()%>"  >
                            </div>
                            <%}}%>
                        </div>
                    </section>

                    <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 " >
                        <div class="d-flex justify-content-end">

                            <button type="button"  class="btn   btn-block  m-2 btn-primary" id="boton-azul" >Regresar</button>

                            <button type="button"  class="btn   btn-block  m-2 btn-primary enviar" value="guardar" id="boton-azul" >Actualizar</button>

                            <button type="button"  class="btn  btn-block  m-2  btn-primary enviar" value="guardaryenviar" id="boton-azul" >Actualizar y publicar</button>


                        </div>
                    </div>


                    <!-- ---------------------------------- Fin producto simple --------------------------- -->

                </div>



    </div>

    </form>

    <!-- Modal -->
    <div class="modal fade" id="imagenesSecondary" tabindex="-1"  data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Imagenes Secundarias</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="ProductServlet?action=savePhotoSecundary" method="post" enctype="multipart/form-data">
                    <div class="modal-body">

                        <div class="container mb-4 d-flex justify-content-center">
                            <img src="#" class="img-fluid" alt="#" id="img_photo" width="350" height="175" >
                        </div>

                        <div class="input-group mb-3">
                            <input type="file" class="form-control" id="secondary" name="secondary" onchange="vistaPrelimina(event)">
                        </div>

                        <input type="hidden" value="<%= product.getId_products()%>" name="id">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!----------- ----------------------------Termina formulario-------------------------------------------------- -->


    </main>
</div>
</div>

<script>
    let vistaPrelimina = (e)=>{
        let leer_img = new FileReader();
        id_img = document.getElementById('img_photo');

        leer_img.onload = ()=>{
            if (leer_img.readyState === 2){
                id_img.style.display = 'block';

                id_img.src = leer_img.result;

            }
        }
        leer_img.readAsDataURL(event.target.files[0])
    }


    //Inicio Jquery
    $(()=> {


        let id_img = document.getElementById('img_photo');
        id_img.style.display = 'none';
        //Visualizar imagen



        $('.content').richText();



        //Inicio query

        var listTipoProduct = document.querySelector("#tipoProducto");

        var formSimple = document.querySelector("#simple");
        var formCompuesto = document.querySelector("#compuesto");
        var formCompuestoMas = document.querySelector("#new");


        var formCompuColores = document.querySelector("#formCompu");
        var filaColors = document.querySelector("#filaColores");
        var btnAddColor = document.querySelector("#addColor");

        //Obtencion de formulario (iteasm)


            var listTipoProductValor = document.querySelector("#tipoProducto").value;
            //console.log(listTipoProductValor);
            if (listTipoProductValor==='Compuesto') {
                formSimple.style.display = 'none';
                formCompuesto.style.display = 'block';
            } else {
                formCompuesto.style.display = 'none';
                formSimple.style.display = 'block';
            }




        var j =1000; //Saber cuantas veces se va usar el teamplate
        $('#addColor').click(function(){

            j++;


            var template = `

              <div class="row" id="newRow${j}">
                    <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >
                        <label class="input-text h5 text-uppercase" for="categorias">Color</label>
                        <select class="form-select "   name="colorProductos" >
                            <option value="Negro">Negro</option>
                            <option value="Blanco">Blanco</option>
                            <option value="Azul">Azul oscuro</option>
                            <option value="Gris">Gris</option>
                            <option value="Café">Cafe</option>
                            <option value="Verde oscuro">Verde oscuro</option>
                            <option value="Verde">Verde claro</option>
                            <option value="Amarillo">Amarillo</option>
                            <option value="Naranja">Naranja</option>
                            <option value="Rojo">Rojo</option>
                            <option value="Rosa">Rosa</option>
                            <option value="Morado">Morado</option>
                            <option value="Azul claro">Azul claro</option>
                            <option value="Plateado">Plateado</option>
                            <option value="Dorado">Dorado</option>
                        </select>
                    </div>

                    <h6>Unidades por talla</h6>

                    <div class=" col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 rounded-bottom bg-light p-2">
                        <div class="row">
                            <!-- ----------------------cHICA---------------- -->
                            <div class="col-6 col-lg-3">
                                <div class="row">
                                    <div class="col-12">
                                        <label for="cantidad0" class="form-label">CH</label>
                                        <input type="number" class="form-control" name="chica" value="0" >

                                    </div>

                                    <div class="col-12">
                                        <label for="precio0" class="form-label">Precio</label>
                                        <div class="input-group mb-3 ">
                                            <span class="input-group-text">$</span>
                                            <input type="number" class="form-control" name="chicaPrecio" value="0" >

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- ----------------Fin chica--------------------------- -->
                            <!-- ----------------Mediana -------------------------- -->
                            <div class="col-6 col-lg-3">
                                <div class="row">
                                    <div class="col-12">
                                        <label for="cantidad0" class="form-label">MD</label>
                                        <input type="number" class="form-control" name="mediana" value="0" >

                                    </div>

                                    <div class="col-12">
                                        <label for="precio0" class="form-label">Precio</label>
                                        <div class="input-group mb-3 ">
                                            <span class="input-group-text">$</span>
                                            <input type="number" class="form-control" name="medianaPrecio" value="0">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- ----------------Fin mediana --------------------------- -->
                            <!-- ----------------Grande--------------------------- -->
                            <div class="col-6 col-lg-3">
                                <div class="row">
                                    <div class="col-12">
                                        <label for="cantidad0" class="form-label">G</label>
                                        <input type="number" class="form-control" name="grande" value="0" >

                                    </div>

                                    <div class="col-12">
                                        <label for="precio0" class="form-label">Precio</label>
                                        <div class="input-group mb-3 ">
                                            <span class="input-group-text">$</span>
                                            <input type="number" class="form-control" name="grandePrecio" value="0" >

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- ----------------Fin Grande--------------------------- -->
                            <!-- ----------------Extra grande-------------------------- -->
                            <div class="col-6 col-lg-3">
                                <div class="row">
                                    <div class="col-12">
                                        <label for="cantidad0" class="form-label">XG</label>
                                        <input type="number" class="form-control" name="xgrande" value="0" >

                                    </div>

                                    <div class="col-12">
                                        <label for="precio0" class="form-label">Precio</label>
                                        <div class="input-group mb-3 ">
                                            <span class="input-group-text">$</span>
                                            <input type="number" class="form-control" name="xgrandePrecio" value="0" >

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- ----------------Fin ExtraGrande--------------------------- -->

                        </div>
                    </div>


                            <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >

                                <div class="d-flex ">

                                    <a class=" mt-3 btn btn-outline-danger vw-100 remove-color" id="${j}">Eliminar</a>


                                </div>
                            </div>

                </div>

                `


            $('#formCompu').append(template) //Se le egraga al div todo el contenido html


        });


        $(document).on('click', '.remove-color', function() {
            var id = $(this).attr("id");

            Swal.fire({
                icon:'question',
                text: '¿Seguro quieres borrar la combinación?',
                showCancelButton: true,
                confirmButtonText: 'Seguro',
            }).then((result) => {
                if (result.isConfirmed) {
                    $('#newRow'+id).remove();
                    Swal.fire({
                        icon: 'success',
                        showConfirmButton: false,
                        timer:600 ,
                    })
                }
            })

        }); //Si le da eliminar al boton removera la templeate de donde lo contienee


        $(document).on('click', '.enviar', function() {
            var status=$(this).val()==="guardar"?0:1;
            var typeProduct = $('#tipoProducto').val();

            console.log(status , typeProduct)

            //Vemos el tipo de producto

            if (typeProduct === "Simple") {
                console.log("------------------Simple---------------------")
                var simple = new Array(1); //Creacion de arreglo bidimensional

                var simpleobj={
                    color: $('#colorSimple').val(),
                    pieces:$('#cantidadSimple').val(),
                    price:$('#precioSimple').val(),
                    size: "NA"
                }//Hacer Json para mandarlo
                simple[0] = JSON.stringify(simpleobj)

                console.log(simple[0]);//Json listo para enviar y hacer objeto

                $.post("ProductServlet?action=updateCombinations",{
                    'id': "<%= product.getId_products()%>",
                    'simple[]': simple,
                    'status': status,
                    'type': typeProduct }) //Enviamos





            } else if (typeProduct=== "Compuesto") {
                console.log("------------------Compuesto---------------------")

                var colorProductos = document.querySelectorAll('select[name="colorProductos"]');
                var cantidadChica = document.querySelectorAll('input[name="chica"]');
                var precioChica = document.querySelectorAll('input[name="chicaPrecio"]');
                var cantidadMediana = document.querySelectorAll('input[name="mediana"]');
                var precioMediana = document.querySelectorAll('input[name="medianaPrecio"]');
                var cantidadGrande = document.querySelectorAll('input[name="grande"]');
                var precioGrande = document.querySelectorAll('input[name="grandePrecio"]');
                var cantidadExtraGrande = document.querySelectorAll('input[name="xgrande"]');
                var precioExtraGrande = document.querySelectorAll('input[name="xgrandePrecio"]');

                //Extraemos los datos de producto(s) compuestos
                console.log(colorProductos.length + " -> numero de colores ")

                var chica = new Array(colorProductos.length); //Creacion de arreglo bidimensional
                var mediana = new Array(colorProductos.length); //Creacion de arreglo bidimensional
                var grande = new Array(colorProductos.length); //Creacion de arreglo bidimensional
                var Xgrande = new Array(colorProductos.length); //Creacion de arreglo bidimensional
                //Sabemos que por cada color es un resgitro


                for (var i = 0; i < colorProductos.length; i++) {//Dependiendo colores itera

                    var compuestoChica = {
                        color:colorProductos[i].value,
                        size:"CH",
                        pieces:cantidadChica[i].value===0?0:cantidadChica[i].value,
                        price: precioChica[i].value===0?0:precioChica[i].value
                    }

                    var compuestoMediana = {
                        color:colorProductos[i].value,
                        size:"MD",
                        pieces: cantidadMediana[i].value===0?0:cantidadMediana[i].value,
                        price: precioMediana[i].value===0?0:precioMediana[i].value
                    }
                    var compuestoGrande = {
                        color:colorProductos[i].value,
                        size:"G",
                        pieces: cantidadGrande[i].value===0?0:cantidadGrande[i].value,
                        price: precioGrande[i].value===0?0:precioGrande[i].value

                    }

                    var compuestoXGrande = {
                        color:colorProductos[i].value,
                        size:"XG",
                        pieces: cantidadExtraGrande[i].value===0?0:cantidadExtraGrande[i].value,
                        price: precioExtraGrande[i].value===0?0:precioExtraGrande[i].value
                    }


                    chica[i] =  JSON.stringify(compuestoChica)
                    mediana[i] =  JSON.stringify(compuestoMediana)
                    grande[i] =  JSON.stringify(compuestoGrande)
                    Xgrande[i] =  JSON.stringify(compuestoXGrande)

                }

                //Mostrar datos del arreglo que contiene los json----------------------------

                for (var i = 0; i < colorProductos.length; i++) {
                    console.log("Json Chica --->" + chica[i])
                    console.log("Json Medi--->" + mediana[i])
                    console.log("Json Grand--->" + grande[i])
                    console.log("Json Xtra--->" + Xgrande[i])
                }

                // $.post("ServletAdmin",{ 'combination[]': nuevoArray,'infoGeneral[]' : datosGenerales})

                $.post("ProductServlet?action=updateCombinations",{
                    'id': "<%= product.getId_products()%>",
                    'ch[]': chica,
                    'md[]': mediana,
                    'gr[]': grande,
                    'xgr[]': Xgrande,
                    'status': status,
                    'type': typeProduct }) //Enviamos


                //Mandar por &post

            }else{
                console.log("Error")

            }

            Swal.fire({
                icon:'success',
                title: 'Registro Exitoso...',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                window.location.href = "ProductServlet?accion=getProducts"
            })

            //--------------------------------------------------------------


        }); //Almacenamos datos del formulario


        //-----------------------------------------------

        //----------------------Fin jquery------------------------------------------------
    });
</script>

<%@include file="/template/footer.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/typewriter-effect@latest/dist/core.js"></script>



</body>
</html>


