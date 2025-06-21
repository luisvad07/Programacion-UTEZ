<%@ page import="java.util.List" %>
<%@ page import="utez.tienda.tiendautez.products.normal.model.ProductUserBean" %>
<%@ page import="utez.tienda.tiendautez.products.gestion.model.ProductBean" %>
<%@ page import="utez.tienda.tiendautez.images.model.ImagesBean" %>
<%@ page import="utez.tienda.tiendautez.products.gestion.model.CombinationPDBean" %>
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
    ProductBean product = (ProductBean) request.getAttribute("product");
    int offer = product.getOffers_id_offers()==0?0:product.getOffers_id_offers();
    double disc =0;
%>


<div class="p-3 pt-1 rounded rounded-3" id="fondo">
    <div id="fondo-blanco" class="rounded p-4 shadow">
        <!-- El fondo blanco -->
        <main class="container-fluid">
            <!-- Contenedor principal-->

            <div class="container">
                <div class="card my-3 shadow">
                    <div class="card border-2 m-md-3">
                        <div class="card-body">
                            <div class="row">
                                <!-- Parte izquierda -->
                                <div class="col-lg-5 col-sm-12 pt-3 p-3 ">
                                    <!-- comienzo con contenido -->



                                    <!------------Carrusel de imagenes ------------------------------------------->
                                    <div class=" col-12 col-lg-11 col-md-12 p-0 px-md-2 mb-3 mt-5 shadow sticky-top">

                                        <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
                                            <div class="carousel-inner">


                                                <div class="carousel-item active " data-bs-interval="3000">

                                                    <%if (product.getOffers_id_offers()!=0){

                                                    %>
                                                    <span class="position-absolute  badge rounded-1  m-2 px-3 bg-success">Oferta</span>
                                                    <% } %>


                                                    <img src="data:image/jpeg;base64, <%= product.getImageToShow()%> " class="img-fluid " alt="Imagen<%= product.getName()%>">
                                                    <div class="carousel-caption d-none d-md-block">

                                                    </div>
                                                </div>

                                                <%for (ImagesBean images: product.getImagesSecondaries()){
                                                    //System.out.println(images.getImages());
                                                %>
                                                <div class="carousel-item " data-bs-interval="1900">
                                                    <%if (product.getOffers_id_offers()!=0){    %>
                                                    <span class="position-absolute  badge rounded-1  m-1 p-4  bg-success">Oferta</span>
                                                    <% } %>

                                                    <img src="data:image/jpeg;base64,<%= images.getImages()%> "  class="d-block w-100" alt="ImagesSecondary<%= images.getImages()%>">
                                                    <div class="carousel-caption d-none d-md-block">

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


                                </div>


                                <!-- //---------------------------------------------------- -->
                                <!-- parte derecha -->
                                <div class="col-lg-7 col-sm-12 ">

                                    <div class="row">
                                        <h3 class="display-4 text-start"><%= product.getName()%></h3>
                                        <input type="hidden" class="form-control border-info" id="tipoProducto" disabled value="<%= product.getType()%>">
                                    </div>

                                    <div class="row">
                                        <div class="col-12">
                                            <div class="my-2"><%= product.getDescription()%></div>
                                        </div>
                                    </div>

                                    <section id="simple">
                                        <div class="row mt-1 ">
                                            <div class="col-8">
                                                <label>Precio</label>
                                                <input type="text" class="form-control disabled border border-success" id="sPrecio" readonly>

                                            </div>

                                            <div class="col-8">
                                                <label>Piezas</label>
                                                <input type="text" class="form-control disabled border border-success" id="sPiezas" readonly>

                                            </div>

                                            <div class="col-8">
                                                <label>Color</label>
                                                <input type="text" class="form-control disabled border border-success" id="sColor" readonly>

                                            </div>
                                        </div>
                                    </section>


                                    <div class="row my-2">
                                        <div class="col-12">
                                            <section id="compuesto">
                                                <div id="formCompu">


                                                    <div class="row" id="newRow">

                                                        <% List<CombinationPDBean> combination = product.getCombinations(); //Lista de las combinaciones de productos
                                                            String color="";    //Recorro las combination

                                                        %>
                                                        <div class="col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3  " >
                                                            <label class="input-text h5 text-uppercase" >Color</label>
                                                            <select class="form-select "  id="colorProductos" >
                                                                <%for (CombinationPDBean combina:combination) {
                                                                    if (!combina.getColor().equalsIgnoreCase(color)){
                                                                    color=combina.getColor();%>

                                                                <option value="<%=combina.getColor()%>"><%=combina.getColor().toUpperCase()%></option>

                                                                    <% }
                                                                }%>

                                                            </select>
                                                        </div>


                                                        <h6>Unidades por talla</h6>

                                                        <div class=" col-12 col-lg-11 col-md-12  p-0 px-md-2  mb-3 rounded-bottom  p-2">
                                                            <div class="row">
                                                                <!-- ----------------------cHICA---------------- -->

                                                                <div class="col-6 col-lg-3">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <label class="form-label">CH</label>
                                                                            <input type="text" class="form-control disabled border border-success" id="chica" readonly>

                                                                        </div>

                                                                        <div class="col-12">
                                                                            <label  class="form-label">Precio</label>
                                                                            <div class="input-group mb-3 ">
                                                                                <input type="text" class="form-control disabled border border-success" id="precioChica" readonly>

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-6 col-lg-3">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <label  class="form-label">MD</label>
                                                                            <input type="text" class="form-control disabled border border-success" id="mediana" readonly>

                                                                        </div>

                                                                        <div class="col-12">
                                                                            <label  class="form-label">Precio</label>
                                                                            <div class="input-group mb-3 ">
                                                                                <input type="text" class="form-control disabled border border-success" id="precioMediana" readonly>

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ----------------Fin mediana --------------------------- -->


                                                                <!-- ----------------Grande--------------------------- -->
                                                                <div class="col-6 col-lg-3">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <label  class="form-label">G</label>
                                                                            <input type="text" class="form-control disabled border border-success" id="grande" readonly>

                                                                        </div>

                                                                        <div class="col-12">
                                                                            <label  class="form-label">Precio</label>
                                                                            <div class="input-group mb-3 ">

                                                                                <input type="text" class="form-control disabled border border-success" id="precioGrande"  readonly>

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ----------------Fin Grande--------------------------- -->


                                                                <!-- ----------------Extra grande-------------------------- -->
                                                                <div class="col-6 col-lg-3">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <label class="form-label">XG</label>
                                                                            <input type="text" class="form-control disabled border border-success" id="xGrande" readonly>

                                                                        </div>

                                                                        <div class="col-12">
                                                                            <label class="form-label">Precio</label>
                                                                            <div class="input-group mb-3 ">

                                                                                <input type="text" class="form-control disabled border border-success" id="precioXGrande" readonly >

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ----------------Fin ExtraGrande--------------------------- -->

                                                            </div>
                                                        </div>

                                                        <hr>


                                                    </div>



                                                </div>

                                            </section>

                                         </div>
                                     </div>
                                  </div>

                            </div>
                        </div>
                        <!-- fin de fila  -->
                    </div>
                </div>
                <hr >


                <div class="col-12 col-lg-12col-md-12 p-0 px-md-2 ">
                    <h3 class="text-center fst-italic mb-4">Descripción larga</h3>
                    <div class="card alert ">
                        <%= product.getDescriptionLong()%>
                    </div>

                </div>


            </div>
         </main>

    </div>
</div>


<script>
     $(()=> {

        //Inicio query
        var listTipoProduct = document.querySelector("#tipoProducto");
        var formSimple = document.querySelector("#simple");
        var formCompuesto = document.querySelector("#compuesto");
        var formCompuestoMas = document.querySelector("#new");
        var formCompuColores = document.querySelector("#formCompu");
        var filaColors = document.querySelector("#filaColores");
        var btnAddColor = document.querySelector("#addColor");
        //formSimple.style.display = 'none';
        formCompuesto.style.display = 'none';



         var listTipoProductValor = document.querySelector("#tipoProducto").value;


         var coloreslist = document.getElementById("colorProductos")
         var color = document.getElementById("colorProductos").value
         //console.log(color)
         //console.log(listTipoProductValor);
            if (listTipoProductValor==='Compuesto') {
                formSimple.style.display = 'none';
                formCompuesto.style.display = 'block';
                combi(color)

          } else {
              formCompuesto.style.display = 'none';
              formSimple.style.display = 'block';

                <%for (CombinationPDBean combina:combination) {%>

                document.getElementById("sColor").value = "<%=combina.getColor()%>"
                document.getElementById("sPiezas").value = "<%=combina.getPieces()%>"
                document.getElementById("sPrecio").value = "$ <%=combina.getPrice()%>"

                <%} %>

          }

         coloreslist.addEventListener("change", ()=>{
             var color = document.getElementById("colorProductos").value
             //console.log(color)
             combi(color)

         })
      //Obtencion de formulario (iteasm)


  })






 function combi(color){

    <%for (CombinationPDBean combina:combination) {%>

    if ("<%=combina.getSize()%>" === "CH" && "<%=combina.getColor()%>" === color){

        document.getElementById("chica").value = "<%=combina.getPieces()%>"
        document.getElementById("precioChica").value = "$<%=combina.getPrice()%>"

    }else if("<%=combina.getSize()%>" === "MD" && "<%=combina.getColor()%>" === color){

        document.getElementById("mediana").value = "<%=combina.getPieces()%>"
        document.getElementById("precioMediana").value = "$<%=combina.getPrice()%>"

    }else if ("<%=combina.getSize()%>" === "G" && "<%=combina.getColor()%>" === color){

        document.getElementById("grande").value = "<%=combina.getPieces()%>"
        document.getElementById("precioGrande").value = "$<%=combina.getPrice()%>"

     }else if("<%=combina.getSize()%>" === "XG" && "<%=combina.getColor()%>" === color){

        document.getElementById("xGrande").value = "<%=combina.getPieces()%>"
        document.getElementById("precioXGrande").value = "$<%=combina.getPrice()%>"

     }else{

     }

    <% } %>


 }





</script>
<!-----------------Links para el funcionamiento de la página -->
<%@include file="/productsUser/templateUser/linksScript.jsp"%>
<%@include file="/template/footer.jsp"%>
</body>
</html>