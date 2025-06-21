package mx.edu.utez.MiProyectoServlet.controller.auth;

import mx.edu.utez.MiProyectoServlet.model.auth.BeanAuth;
import mx.edu.utez.MiProyectoServlet.service.auth.ServiceAuth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ServletAuth", urlPatterns = {"/login","/logout","/singin"})
public class ServletAuth extends HttpServlet {
    String action;
    String urlRedirect = "/getPersons";
    HttpSession session;
    ServiceAuth serviceAuth = new ServiceAuth();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      action = request.getServletPath();
      switch (action){
          case "/singin":
              urlRedirect = "/index.jsp";
              break;
              default:
                  urlRedirect= "/index.jsp";
                  break;
      }
      request.getRequestDispatcher(urlRedirect).forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action = request.getServletPath();
        switch (action){
            case "/login":
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                BeanAuth login = serviceAuth.login(username, password);
                System.out.println("LOGIN -> "+login);
                if (login!=null) {
                    session = request.getSession();
                    session.setAttribute("login", login);
                    System.out.println(session.getAttribute("login"));
                    urlRedirect = "/getPersons";
                } else {
                    urlRedirect = "/?message="+ URLEncoder.encode(
                            "Usuario y/o Contraseña Incorrectos", StandardCharsets.UTF_8.name());
                }
                break;
            default:
                session = request.getSession();
                session.invalidate();
                urlRedirect = "/";
                break;
        }
        response.sendRedirect(request.getContextPath()+urlRedirect);
    }
}
