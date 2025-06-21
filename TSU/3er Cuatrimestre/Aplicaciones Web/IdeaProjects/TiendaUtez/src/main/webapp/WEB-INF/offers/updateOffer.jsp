<%--
  Created by IntelliJ IDEA.
  User: HORUS
  Date: 10/08/2022
  Time: 11:31 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GESTION ADMINISTRADORES</title>
    <%@include file="/template/header.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/administrator/templateAdmin/navbar.jsp"%>
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
            <div class="col col-lg-12 col-md-12 col-sm-12 col-12  p-2">
                <h1 id="titulo">Crear Oferta</h1>

            </div>


        </div>
        <form name="registerOffer" class="needs-validation" novalidate action="updateOffer" id="form" method="post" enctype="multipart/form-data">

            <div class="row">
                <div class="col col-lg-4 col-md-6 col-sm-12 col-12  ">
                    <label for="name" class="form-label fs-4">Nombre de la oferta</label>
                    <input type="text" class="form-control p-3" value="${offer.name}" id="name" name="name" placeholder=" Gran oferta "
                           required>
                    <div class="invalid-feedback">
                        Ingresa el nombre.
                    </div>
                    <div class="valid-feedback">
                        ¡Buen nombre!
                    </div>

                </div>
                <input type="hidden" name="id_offers" value="${offer.id_offers}"/>

                <!--
                <div class="col col-lg-8 col-md-6 col-sm-12 col-12 mt-3">
                    <div class="">
                        <label for="banner" class="form-label">Small file input example</label>
                        <input class="form-control form-control-lg" id="banner" name="banner" type="file" value="${offer.banner}">
                        <div class="invalid-feedback">
                            Ingresa el nombre.
                        </div>
                        <div class="valid-feed ack">
                            ¡Buen nombre!
                        </div>
                    </div>
                </div>
                -->




                <!-- aqui esta el imput de imagen pero se hace hasta que le sepa
                <div class="col col-lg-8 col-md-6 col-sm-12 col-12 mt-3">
                    <div class="">
                        <label for="formFileSm" class="form-label">Small file input example</label>
                        <input class="form-control form-control-lg" id="formFileSm" type="file">
                        <div class="invalid-feedback">
                            Ingresa el nombre.
                        </div>
                        <div class="valid-feed ack">
                            ¡Buen nombre!
                        </div>
                    </div>
                </div>
                -->


            </div>
            <div class="row mt-2">
                <div class="col col-lg-3 col-md-6 col-sm-6 col-6  ">
                    <label for="start_date" class="form-label ">Fecha inicio</label>
                    <input type="date" class="form-control p-3" value="${offer.start_date}" id="start_date" name="start_date" required>
                    <div class="invalid-feedback">
                        Ingresa el nombre.
                    </div>
                    <div class="valid-feedback">
                        ¡Buen nombre!
                    </div>

                </div>
                <div class="col col-lg-3 col-md-6 col-sm-6 col-6  ">
                    <label for="end_date" class="form-label ">Fecha fin</label>
                    <input type="date" class="form-control p-3" value="${offer.end_date}" id="end_date" name="end_date" required>
                    <div class="invalid-feedback">
                        Ingresa el nombre.
                    </div>
                    <div class="valid-feedback">
                        ¡Buen nombre!
                    </div>

                </div>
                <div class="col col-lg-6 col-md-8 col-sm-6 col-6">
                    <label for="discount" class="form-label">Descuento</label>
                    <div class="input-group ">
                        <span class="input-group-text">%</span>
                        <input type="number" class="form-control form-control-lg" value="${offer.discount}" id="discount" name="discount" required>
                        <div class="invalid-feedback">
                            Ingresa el costo.
                        </div>
                        <div class="valid-feedback">
                            Suena bien.
                        </div>
                    </div>
                </div>

            </div>



            <div class="row m-3 justify-content-md-center  text-center">
                <div class="col col-lg-4 col-12">
                    <button class="btn btn-lg m-3 text-light" id="boton-azul" type="submit" style="background-color: #20c997;">Guardar</button>
                    <a class="btn btn-lg m-3 text-light" type="submbit" role="button"
                       style="background-color: #dc3545;">Cancelar</a>
                </div>

            </div>

        </form>




    </main>




</div>



</div>


<!-- **************************************************************AQUI TERMINA LA VISTA***********************************************************  -->


<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
<%@include file="/WEB-INF/administrator/templateAdmin/linksScripts.jsp"%>
<%@include file="/template/footer.jsp"%>
</body>
</html>
