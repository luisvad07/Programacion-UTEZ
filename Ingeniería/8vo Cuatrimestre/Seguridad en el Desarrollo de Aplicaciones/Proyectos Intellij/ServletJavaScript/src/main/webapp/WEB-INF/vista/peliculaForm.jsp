<%--
  Created by IntelliJ IDEA.
  User: CA2-Enero
  Date: 20/02/2024
  Time: 06:27 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <form action="<%=request.getContextPath()%>/PeliculaServlet" method="post">
        <p>
            <label for="titulo">Titulo:</label>
            <input type="text" name="titulo" id="titulo">
        </p>
        <p>
            <label for="sinopsis">Sinopsis:</label>
            <textarea name="sinopsis" id="sinopsis" cols="30" rows="10"></textarea>
        </p>
        <p><button type="submit">Enviar</button></p>
    </form>
</head>
<body>

</body>
</html>
