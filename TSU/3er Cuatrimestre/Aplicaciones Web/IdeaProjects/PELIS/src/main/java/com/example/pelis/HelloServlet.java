package mx.edu.utez.CRUD_Peliculas;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1> Hola, acabo de modificar el Servlet </h1>");
        out.println("<table border='1'>");
        out.println("<tr>");
        out.println("<th>Nombre</th>");
        out.println("<th>Apellido</th>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}