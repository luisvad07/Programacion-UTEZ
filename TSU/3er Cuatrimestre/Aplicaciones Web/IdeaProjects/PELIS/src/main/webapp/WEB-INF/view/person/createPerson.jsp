<%--
  Created by IntelliJ IDEA.
  User: Cesar
  Date: 20/07/2022
  Time: 03:05 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrar Pelicula</title>
    <%@include file="../../template/head.jsp"%>
</head>
<body>
<jsp:include page="../../template/navbar.jsp"/>
<h1>REGISTRAR PELICULA</h1>
<div class="container">
    <div class="row">
        <div class="col-12">
            <div class="card mt-5">
                <div class="card-header">REGISTRAR PELICULA</div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-12">
                            <form name="registerPerson" class="needs-validation" novalidate action="savePerson" method="post" enctype="multipart/form-data">
                                <div class="form-group mb-3">
                                    <div class="row">
                                        <div class="col">
                                            <label for="name" class="fw-bold">Nombre</label>
                                            <input type="text" class="form-control" name="name" id="name" required>
                                            <div class="invalid-feedback">
                                                Campo Obligatorio
                                            </div>
                                        </div>
                                        <div class="col">
                                            <label for="description" class="fw-bold">Descripcion</label>
                                            <input type="text" class="form-control" name="description" id="description" required>
                                            <div class="invalid-feedback">
                                                Campo Obligatorio
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group mb-3">
                                    <div class="row">
                                        <div class="col">
                                            <label for="publish_date" class="fw-bold">Fecha Publicacion</label>
                                            <input type="date" class="form-control" name="publish_date" id="publish_date" required>
                                            <div class="invalid-feedback">
                                                Campo Obligatorio
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <label for="actors" class="fw-bold">Actores</label>
                                        <input type="text" class="form-control" name="actors" id="actors" required>
                                        <div class="invalid-feedback">
                                            Campo Obligatorio
                                        </div>
                                    </div>
                                </div>
                        <div class="form-group mb-3">
                            <div class="row">
                                <div class="col">
                                    <label for="duration" class="fw-bold">Duracion</label>
                                    <input type="number" class="form-control" name="duration" id="duration" required>
                                    <div class="invalid-feedback">
                                        Campo Obligatorio
                                    </div>
                                </div>
                                <div class="col">
                                    <label for="ranking" class="fw-bold">Ranking</label>
                                    <input type="number" class="form-control" name="ranking" id="ranking" required>
                                    <div class="invalid-feedback">
                                        Campo Obligatorio
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group mb-3">
                            <div class="row">
                                <div class="col">
                                    <label for="image" class="fw-bold">Foto Pelicula</label>
                                    <input type="file" class="form-control" name="image" id="image" required>
                                    <div class="invalid-feedback">
                                        Campo Obligatorio
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr style="height:2px"; width:100%; border-width:0; color=gray; background-color:gray;>
                        <div class="form-group mb-3">
                            <div class="row">
                                <div class="col-12 text-end">
                                    <button type="submit" class="btn btn-success btn-sm">Registrar</button>
                                    <button type="button" class="btn btn-danger btn-sm">Cancelar</button>
                                </div>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>
<script>
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
<jsp:include page="../../template/footer.jsp"/>
</body>
</html>
