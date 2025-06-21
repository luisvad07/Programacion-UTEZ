package mx.edu.utez.servletjavascript.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name= "PeliculaServlet", value = "/PeliculaServlet")
public class PeliculasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vista/peliculaForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titulo = req.getParameter("titulo");
        String sinopsis = req.getParameter("sinopsis");

        req.setAttribute("tituloX", titulo);
        req.setAttribute("sinopsisX", sinopsis);

        req.getRequestDispatcher("/WEB-INF/vista/peliculaResultado.jsp").forward(req, resp);
    }
}
