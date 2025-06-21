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
    <meta http-equiv="content-type" content="text/html; utf-8">

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
        <section>
            <form  name="registerAdmin" class="needs-validation" novalidate action="saveAdmin" id="form"  method="post">
                <div class="row ">
                    <div class="col col-lg-12 col-md-12 col-sm-12 col-12  p-2">
                        <h1 id="titulo">Crear administrador</h1>

                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-4 col-md-6 col-sm-12 col-12 mt-2" id="grupo_usuario">
                        <label for="username" class="form-label fs-3">Nombre de usuario</label>
                        <input type="text" class="form-control" id="username" name="username"  placeholder="Halconcín " required>
                        <div class="invalid-feedback">
                            Campo obligatorio
                        </div>
                    </div>
                    <!-- aqui preguntar y recordar porque este ya no me pide a fuerza el arroba -->
                    <div class="col col-lg-4 col-md-6 col-sm-12 col-12 mt-2">
                        <label for="email" class="form-label fs-3">Correo</label>
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="halcon@utez.edu.mx" required>
                        <div class="invalid-feedback">
                            Campo obligatorio
                        </div>
                    </div>

                    <!-- aqui preguntar y recordar como funciona esto de la confirmacion para saber o ver cual mando -->
                    <div class="col col-lg-4 col-md-6 col-sm-12 col-12 mt-2">
                        <label for="psw1" class="form-label fs-3">Contraseña</label>
                        <input type="password" class="form-control" id="psw1" name="psw1" required>
                        <div class="invalid-feedback">
                            Campo obligatorio
                        </div>
                    </div>
                    <!-- aqui preguntar y recordar como hacer que funcione la confirmacion si pongo la otra validacion -->
                    <div class="col col-lg-4 col-md-6 col-sm-12 col-12 mt-2">
                        <label for="psw" class="form-label fs-3">Confirmar contraseña</label>
                        <input type="password" class="form-control " id="psw" name="psw"  required>
                        <div id="user" class="invalid-tooltip">
                            Ambas contraseñas deben ser iguales
                        </div>
                        <div id="msg" class="invalid-tooltip">
                            Ambas contraseñas deben ser iguales
                        </div>
                    </div>

                    <div class="col col-lg-4 col-md-6 col-sm-12 col-12 mt-2">
                        <label for="roles_id_roles" class="form-label fs-3">Tipo de usuario</label>


                        <select class="form-select" aria-label="Default select example" name="roles_id_roles" id="roles_id_roles">
                            <option selected>Selecciona un tipo de rol</option>
                            <option value="1">Root</option>
                            <option value="2">Admin</option>
                        </select>


                        <div class="invalid-feedback">
                            Campo obligatorio
                        </div>
                    </div>

                </div>
                <div class="row m-3 justify-content-md-center  text-center">
                    <div class="col col-lg-4 col-12">
                        <button class="btn  m-2" id="boton-azul" type="submit">Guardar</button>
                        <button class="btn m-2 text-light" style="background-color: #20c997"
                                type="button">Cancelar</button>

                        <!-- aqui preguntar y recordar para que sirve este parrafo -->
                        <p class="warnings" id=""></p>
                    </div>

                </div>

            </form>
        </section>

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
