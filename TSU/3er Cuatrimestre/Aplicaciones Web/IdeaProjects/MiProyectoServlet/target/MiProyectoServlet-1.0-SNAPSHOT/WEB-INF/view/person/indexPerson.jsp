<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
    <title>Gestión Personas</title>
    <%@include file="../../template/head.jsp"%>
</head>
<body>
<jsp:include page="../../template/navbar.jsp"/>
<h1>INDEX DE PERSONA</h1>

<div class="container mt-5">
    <div class="row">
        <div class="col-12">
            <c:if test="${param['result']}">
                <p><c:out value="${param['message']}"></c:out></p>
            </c:if>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col-6">PERSONAS</div>
                        <div class="col-6 text-end">
                            <a href="createPerson" class="btn btn-outline-success btn-sm">
                                Registrar Personas
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-sm table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Perfil</th>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>Usuario</th>
                            <th>Rol</th>
                            <th>Edad</th>
                            <th>Fecha Nacimiento</th>
                            <th>Correo</th>
                            <th>Telefono</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personList}" var="person" varStatus="status">
                            <tr>
                                <th><c:out value="${status.count}"></c:out></th>
                                <td><img src="data:image/jpeg;base64, ${person.image}" class="rounded-circle" style="width: 80px;"
                                         alt="Avatar" /></td>
                                <td><c:out value="${person.name}"></c:out></td>
                                <td><c:out value="${person.lastname}"></c:out></td>
                                <td><c:out value="${person.username}"></c:out></td>
                                <td><c:out value="${person.role}"></c:out></td>
                                <td><c:out value="${person.age}"></c:out></td>
                                <td><fmt:formatDate value="${person.birthday}" pattern="dd/MM/yyyy"/></td>
                                <td><c:out value="${person.email}"></c:out></td>
                                <td><c:out value="${person.phone}"></c:out></td>
                                <td>
                                    <a href="getPerson?id=${person.id}" class="btn btn-warning btn-small">EDITAR</a>
                                    <a class="btn btn-danger btn-small"
                                       data-bs-toggle="modal"
                                       data-bs-target="#deletePerson-${person.id}"
                                    >ELIMINAR</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<c:forEach items="${personList}" var="person" varStatus="status">
    <!-- Modal -->
    <div class="modal fade" id="deletePerson-${person.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="post" action="deletePerson">
                    <input type="hidden" name="idP" id="idP" value="${person.id}"/>
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Confirmar Eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    ¿Deseas eliminar a:
                    <c:out value="${person.name}"></c:out> <c:out value="${person.lastname}"></c:out>?
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

<jsp:include page="../../template/footer.jsp"/>
</body>
</html>
