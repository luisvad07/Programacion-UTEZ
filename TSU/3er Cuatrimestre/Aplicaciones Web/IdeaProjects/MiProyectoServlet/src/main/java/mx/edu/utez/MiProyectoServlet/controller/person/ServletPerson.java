package mx.edu.utez.MiProyectoServlet.controller.person;

import mx.edu.utez.MiProyectoServlet.model.person.BeanPerson;
import mx.edu.utez.MiProyectoServlet.service.person.ServicePerson;
import mx.edu.utez.MiProyectoServlet.utils.ResultAction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ServletPerson",
        urlPatterns = {
                "/getPersons",
                "/createPerson",
                "/savePerson",
                "/getPerson",
                "/updatePerson",
                "/deletePerson"
        })
@MultipartConfig(maxFileSize = 1024*1024*100)

public class ServletPerson extends HttpServlet {

    String action;
    String urlRedirect = "/getPersons";
    ServicePerson servicePerson = new ServicePerson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
       action = request.getServletPath();

       switch (action){
           case "/getPersons":
               List<BeanPerson> personList = servicePerson.showPersons();
               //System.out.println(personList.size());
               request.setAttribute("personList", personList);
               urlRedirect="/WEB-INF/view/person/indexPerson.jsp";
               break;
           case "/createPerson":
               urlRedirect="/WEB-INF/view/person/createPerson.jsp";
               break;
           case "/getPerson":
               String id = request.getParameter("id");
               id = (id == null) ? "0" : id;
               //System.out.println("ID PERSON -> "+id);
               try {
                   BeanPerson person = servicePerson.findPerson(Long.parseLong(id));
                   System.out.println(person.getName());
                   request.setAttribute("person", person);
                   urlRedirect="/WEB-INF/view/person/updatePerson.jsp";
               }catch (Exception e){
                   urlRedirect="/getPersons";
               }
               break;
           default:
               urlRedirect="/getPersons";
       }
       request.getRequestDispatcher(urlRedirect).forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();

        switch (action){
            case "/savePerson":
                String name = request.getParameter("name");
                String lastname = request.getParameter("lastname");
                String age = request.getParameter("age");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                String birthday = request.getParameter("birthday");
                Part filePart = request.getPart("image");
                InputStream image = filePart.getInputStream();

                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String role = request.getParameter("role");
                System.out.println(username+" "+password+" "+role);

                try {
                BeanPerson person = new BeanPerson();
                person.setName(name);
                person.setLastname(lastname);
                person.setAge(Integer.parseInt(age));
                person.setEmail(email);
                person.setPhone(phone);
                Date birthdaySDF = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
                person.setBirthday(birthdaySDF);

                person.setUsername(username);
                person.setPassword(password);
                person.setRole(role);

                    ResultAction result = servicePerson.savePerson(person, image);
                    urlRedirect="/getPersons?result="+
                            result.isResult() + "&message=" + result.getMessage()
                            + "&status=" +result.getStatus();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "/updatePerson":
                String name2 = request.getParameter("name");
                String lastname2 = request.getParameter("lastname");
                String age2 = request.getParameter("age");
                String email2 = request.getParameter("email");
                String phone2 = request.getParameter("phone");
                String id = request.getParameter("id");

                BeanPerson person2 = new BeanPerson();
                person2.setName(name2);
                person2.setLastname(lastname2);
                person2.setAge(Integer.parseInt(age2));
                person2.setEmail(email2);
                person2.setPhone(phone2);
                person2.setId(Long.parseLong(id));

                ResultAction result2 = servicePerson.updatePerson(person2);
                urlRedirect="/getPersons?result="+
                        result2.isResult() + "&message=" + result2.getMessage()
                        + "&status=" +result2.getStatus();
                break;
            case "/deletePerson":
                String id2 = request.getParameter("idP");
                id2 = (id2==null)?"0":id2;
                //System.out.println("ID PERSONA -> "+id2);
                ResultAction result3 = servicePerson.deletePerson(Long.parseLong(id2));
                urlRedirect="/getPersons?result="+
                        result3.isResult() + "&message=" + result3.getMessage()
                        + "&status=" +result3.getStatus();
                break;
            default:
                urlRedirect="/getPersons";
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
        //http://localhost:8080/MiProyectoServlet_war_exploded/getPersons.....
    }
}
