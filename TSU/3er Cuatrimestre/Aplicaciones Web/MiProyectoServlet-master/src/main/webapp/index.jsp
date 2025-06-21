<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <%@include file="WEB-INF/template/head.jsp"%>
    <style>
        .rounded-t-5 {
            border-top-left-radius: 0.5rem;
            border-top-right-radius: 0.5rem;
        }

        @media (min-width: 992px) {
            .rounded-tr-lg-0 {
                border-top-right-radius: 0;
            }

            .rounded-bl-lg-5 {
                border-bottom-left-radius: 0.5rem;
            }
        }
    </style>
</head>
<body>
<section class=" text-center text-lg-start">
    <div class="card mb-3">
        <div class="row g-0 d-flex align-items-center">
            <div class="col-lg-4 d-none d-lg-flex">
                <img src="https://mdbootstrap.com/img/new/ecommerce/vertical/004.jpg" alt="Trendy Pants and Shoes"
                     class="w-100 rounded-t-5 rounded-tr-lg-0 rounded-bl-lg-5" />
            </div>
            <div class="col-lg-8">
                <div class="card-body py-5 px-md-5">
                    <form action="login" method="post">
                        <!-- Email input -->
                        <div class="form-outline mb-4">
                            <input type="username" id="username" name="username" class="form-control" />
                            <label class="form-label" for="username">Usuario</label>
                        </div>
                        <!-- Password input -->
                        <div class="form-outline mb-4">
                            <input type="password" id="password" name="password" class="form-control" />
                            <label class="form-label" for="password">Contraseña</label>
                        </div>
                        <!-- 2 column grid layout for inline styling -->
                        <div class="row mb-4">
                            <div class="col d-flex justify-content-center">
                                <!-- Checkbox -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="" id="form2Example31" checked />
                                    <label class="form-check-label" for="form2Example31"> Remember me </label>
                                </div>
                            </div>

                            <div class="col">
                                <!-- Simple link -->
                                <a href="#!">Forgot password?</a>
                            </div>
                        </div>
                        <!-- Submit button -->
                        <button type="submit" class="btn btn-primary btn-block mb-4">Iniciar Sesión</button>
                    </form>

                </div>
            </div>
        </div>
    </div>

</section>
<!-- Section: Design Block -->
<jsp:include page="WEB-INF/template/footer.jsp"/>

<!--<h1><%= "Mi primer Proyecto SERVLET" %>
</h1>
<br/>
<a href="hello-servlet">Este link manda a HelloServlet.java</a><br><br>
<a href="getPersons">IR AL SERVLET DE PERSONAS</a>-->
</body>
</html>