package utez.tienda.tiendautez.administrator.controller;

import utez.tienda.tiendautez.administrator.model.AdminBean;
import utez.tienda.tiendautez.service.ServiceAdmin;
import utez.tienda.tiendautez.utils.ResultAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AdminServlet", value = "/AdminServlet" )
public class AdminServlet extends HttpServlet {

    Logger logger = Logger.getLogger("AdminServlet");
    ServiceAdmin service = new ServiceAdmin();
    String action;
    String view;
    HttpSession sesion;
    String urlRedirect;

    //------------------------------------------Redireccionamiento a las vistas
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        view = req.getParameter("accion");  //----------Tomaremos la accion para ver que hacer esto va hacer mediante la url
       // logger.log(Level.INFO,"GET: "+view);



        switch (view) {
            case "login":
               // System.out.println("va al login ");
                urlRedirect = "WEB-INF/administrator/login.jsp";
                break;

            case "acceder":
                System.out.println(req.getParameter("rol"));
                if (req.getParameter("rol").equalsIgnoreCase("root")) {
                    urlRedirect = "/getAdmins";
                }else {
                    urlRedirect = "ProductServlet?accion=getProducts";
                }
                break;

            case "admins":
                    urlRedirect = "/getAdmins";
                break;

            case "cerrarSesion":
                //System.out.println("Cerrar sesion");
                sesion = req.getSession(false);
                sesion.removeAttribute("username");
                sesion.removeAttribute("email");
                sesion.removeAttribute("rol");
                sesion.invalidate();
                urlRedirect = "WEB-INF/administrator/login.jsp";

                break;

            case "rememberPsw":

                urlRedirect = "WEB-INF/administrator/viewAdmin/rememberContra.jsp";

                break;

            default:
                urlRedirect = "WEB-INF/administrator/login.jsp";
        }

        req.getRequestDispatcher(urlRedirect).forward(req,res);
    }

    //---------------------------------------------Funciones con el Dao
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accionPost = req.getParameter("accionPost");
        AdminBean admin;
        RequestDispatcher dispatcher=null; //Para mandar variables en la vista
        String url="AdminServlet?accion=login";

       switch (accionPost){
           case "validarAdmin":
               sesion = req.getSession(true);
               admin = new AdminBean();
               admin.setEmail( req.getParameter("email"));
               admin.setPsw( req.getParameter("password"));
               admin = service.loginAdmin(admin) ;
                if (admin.getIdAdmin()!=0){
                    //Crea la sesión
                    sesion.setAttribute("username", admin.getUsername());
                    sesion.setAttribute("email",admin.getEmail());
                    sesion.setAttribute("rol",admin.getId_rol());
                    //Redirecciona a las vistas
                    url="AdminServlet?accion=login&icon=success&name="+admin.getUsername()+"&rol="+admin.getRol();
                }else {
                    url="AdminServlet?accion=login&fail=Datos incorrectos";
                }
                res.sendRedirect(url);
               break;

           case "sendEmail":

               admin = new AdminBean();
               admin.setEmail( req.getParameter("email"));
               ResultAction result = service.sendEmail2(admin) ;
               url="AdminServlet?accion=rememberPsw&result="+ result.isResult()+"&message="+result.getMessage();
               //el & concatena variables dentro de la url
               res.sendRedirect(url);
               break;

           default:
             //  res.sendRedirect(url);
       }

        //res.sendRedirect(url);

    }
}
