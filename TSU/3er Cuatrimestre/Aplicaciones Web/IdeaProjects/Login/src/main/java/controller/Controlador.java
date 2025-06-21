package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Controlador", value = "/Controlador")
public class Controlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }




    //-----------Metodo para cuidar mas las cosas---------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion =  request.getParameter("accion"); //Recibimos el valor del baton que tiene como nombre name

        if (accion.equalsIgnoreCase("Principal")){
            request.getRequestDispatcher("Principal.jsp").forward(request,response); //Mandamos
        } else if (accion.equalsIgnoreCase("cerrar")) {
            request.getRequestDispatcher("index.jsp").forward(request,response); //Mandamos
        }else{
            System.out.println("Error");
        }
    }
}
