package controller;

import modelo.Administrador;
import modelo.AdministradorDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Validar", value = "/Validar")
public class Validar extends HttpServlet {

    AdministradorDAO adminDao = new AdministradorDAO();//Instanciamos donde se hacen las consultas
    Administrador admin= null; //Instanciamos
    HttpSession sesionLogin; //SEGURIDAD

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");//Recibimos el valor del baton que tiene como nombre name

        if (accion.equalsIgnoreCase("Ingresar")){

            sesionLogin = request.getSession(true); //Creamos el incicio de sesion
            String correo = request.getParameter("correo"); //Obtener el valor del input
            String pass = request.getParameter("txtpass"); //Obtener el valor del input

            // System.out.println(correo + " "+ pass);

            admin = adminDao.validar(correo,pass); //Verificamos

            //System.out.println(admin.getNombre());
            if (admin.getCorreo()!=null){

                sesionLogin.setAttribute("nombre", admin.getNombre()); //Guardamos en la sesion estos datos
                sesionLogin.setAttribute("correo", admin.getCorreo());
                sesionLogin.setAttribute("pass", admin.getPassword());
                request.getRequestDispatcher("Controlador?accion=Principal").forward(request,response);
                //Lo redireccionamos a controlador con una variable de nombre accion con valor Principal

            }else{
                request.getRequestDispatcher("index.jsp").forward(request,response);

            }
        } else if (accion.equalsIgnoreCase("Cerrar Sesion")) {
            HttpSession sessionLogin=request.getSession(false);//Quitamos la sesion
            RequestDispatcher dispatcher=null; //?

            System.out.println("Sesion Cerrada");
            sessionLogin.removeAttribute("user");//Removemos los datos
            sessionLogin.removeAttribute("pass");
            sessionLogin.removeAttribute("id");
            sessionLogin.invalidate();
            request.getRequestDispatcher("Controlador?accion=cerrar").forward(request,response);

        }else {
            request.getRequestDispatcher("index.jsp").forward(request,response);

        }
    }
}
