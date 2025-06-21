package utez.tienda.tiendautez.administrator.controller;



import utez.tienda.tiendautez.administrator.model.BeanAdmin;
import utez.tienda.tiendautez.service.ServiceAdmin;
import utez.tienda.tiendautez.utils.ResultAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ServletAdmin",
urlPatterns = {
        "/getAdmins",
        "/saveAdmin",
        "/createAdmin",
        "/getAdmin",
        "/updateAdmin",
        "/deleteAdmin"
})
public class ServletAdmin extends HttpServlet {
    String action;
    String urlRedirect = "/getAdmins";
    ServiceAdmin serviceAdmin = new ServiceAdmin();

    //hacer consultas
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
       action = request.getServletPath(); //variable que se llena con las url que  hacen las peticiones
       switch (action){
           case "/getAdmins":
               List<BeanAdmin> adminList = serviceAdmin.showAdmins();
               //System.out.println(adminList.size()); aqui puede ver si me esta trayendo los registros
               request.setAttribute("adminsList", adminList);
               urlRedirect="/WEB-INF/administrator/viewAdmin/indexAdmin.jsp";
               break;
           case "/createAdmin":
               urlRedirect="/WEB-INF/administrator/viewAdmin/createAdmin.jsp";
               break;
           case "/getAdmin":
               String id_admins = request.getParameter("id_admins");
               id_admins = (id_admins == null) ? "0" : id_admins;
               //System.out.println("ID ADMIN -> "+id_admins);
               try {
                   BeanAdmin admin = serviceAdmin.findAdmin(Long.parseLong(id_admins));
                   //System.out.println(admin.getUsername());
                   request.setAttribute("admin",admin);
                   urlRedirect="/WEB-INF/administrator/viewAdmin/updateAdmin.jsp";
               }catch (Exception e){
                   urlRedirect="/getAdmins";
               }

               break;
           default:
               urlRedirect="/getAdmins";
               break;
       }
       request.getRequestDispatcher(urlRedirect).forward(request,response);

    }

    //hacer inserciones
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath(); //variable que se llena con las url que  hacen las peticiones
        switch (action){
            case "/saveAdmin":
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String psw = request.getParameter("psw");
                String roles_id_roles = request.getParameter("roles_id_roles");

                BeanAdmin admin = new BeanAdmin();
                admin.setUsername(username);
                admin.setEmail(email);
                admin.setPsw(psw);
                admin.setRoles_id_roles(Long.parseLong(roles_id_roles));

                ResultAction result = serviceAdmin.saveAdmin(admin);
                urlRedirect="/getAdmins?result="+
                result.isResult() + "&message=" + result.getMessage()
                + "&status=" +result.getStatus();
                break;
            case "/updateAdmin":
                String username2 = request.getParameter("username");
                String email2 = request.getParameter("email");
                String psw2 = request.getParameter("psw");
                String roles_id_roles2 = request.getParameter("roles_id_roles");
                String id_admins = request.getParameter("id_admins");

                BeanAdmin admin2 = new BeanAdmin();
                admin2.setUsername(username2);
                admin2.setEmail(email2);
                admin2.setPsw(psw2);
                admin2.setRoles_id_roles(Long.parseLong(roles_id_roles2));
                admin2.setId_admins(Long.parseLong(id_admins));

                ResultAction result2 = serviceAdmin.updateAdmin(admin2);
                urlRedirect="/getAdmins?result="+
                        result2.isResult() + "&message=" + result2.getMessage()
                        + "&status=" + result2.getStatus();
                break;
            case "/deleteAdmin":
                String id_admins2 = request.getParameter("id_admins");
                id_admins2 = (id_admins2 == null)?"0":id_admins2;
                //System.out.println("ID PERSONA -> "+id_admins2);
                ResultAction result3 = serviceAdmin.deleteAdmin(Long.parseLong(id_admins2));
                urlRedirect="/getAdmins?result="+
                        result3.isResult() + "&message=" + result3.getMessage()
                        + "&status=" +result3.getStatus();
                break;
            default:
                urlRedirect="/getAdmins";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }
}
