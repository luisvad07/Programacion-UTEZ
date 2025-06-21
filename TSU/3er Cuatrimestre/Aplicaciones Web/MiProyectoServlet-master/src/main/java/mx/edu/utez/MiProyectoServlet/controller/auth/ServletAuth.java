package mx.edu.utez.MiProyectoServlet.controller.auth;

import mx.edu.utez.MiProyectoServlet.model.auth.BeanAuth;
import mx.edu.utez.MiProyectoServlet.service.auth.ServiceAuth;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ServletAuth",
    urlPatterns = {"/login", "/logout", "/singin"})
public class ServletAuth extends HttpServlet {
    String action;
    String urlRedirect = "/getPersons";
    HttpSession session;
    ServiceAuth serviceAuth =  new ServiceAuth();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        action = request.getServletPath();
        switch (action){
            case "/singin":
                urlRedirect="/index.jsp";
                break;
            default:
                urlRedirect="/index.jsp";
                break;
        }
        request.getRequestDispatcher(urlRedirect).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        action = request.getServletPath();
        switch (action){
            case "/login":
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                System.out.println(username+" "+password);
                BeanAuth login = serviceAuth.login(username, password);
                if (login!=null){
                    System.out.println(login);
                    System.out.println("LOGIN -> "+login.getUsername());
                    session = request.getSession();
                    session.setAttribute("login", login);
                    //System.out.println(session.getAttribute("login"));
                    //login.getRole()=="admin";
                        urlRedirect = "/getPersons";
                }else {
                    urlRedirect = "/?message="+ URLEncoder.encode(
                            "Usuario y/o contraseña incorrecto",
                            StandardCharsets.UTF_8.name());
                }
                break;
            default:
                session = request.getSession();
                session.invalidate();
                urlRedirect ="/";
                break;
        }
        response.sendRedirect(request.getContextPath()+urlRedirect);
    }
}
