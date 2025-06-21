package mx.edu.utez.Peliculas.controller.person;

import mx.edu.utez.Peliculas.model.person.BeanPerson;
import mx.edu.utez.Peliculas.service.person.ServicePerson;
import mx.edu.utez.Peliculas.utils.ResultAction;

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
                String description = request.getParameter("description");
                String publish_date = request.getParameter("publish_date");
                String actors = request.getParameter("actors");
                String duration = request.getParameter("duration");
                String ranking = request.getParameter("ranking");
                Part filePart = request.getPart("image");
                InputStream image = filePart.getInputStream();

                try {
                    BeanPerson person = new BeanPerson();
                    person.setName(name);
                    person.setDescription(description);
                    Date FormatSDF = new SimpleDateFormat("yyyy-MM-dd").parse(publish_date);
                    person.setPublish_date(FormatSDF);
                    person.setActors(actors);
                    person.setDuration(Integer.parseInt(duration));
                    person.setRanking(Double.parseDouble(ranking));

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
                String description2 = request.getParameter("description");
                String actors2 = request.getParameter("actors");
                String duration2 = request.getParameter("duration");
                String ranking2 = request.getParameter("ranking");
                String id = request.getParameter("id");

                BeanPerson person2 = new BeanPerson();
                person2.setName(name2);
                person2.setDescription(description2);
                person2.setActors(actors2);
                person2.setDuration(Integer.parseInt(duration2));
                person2.setRanking(Double.parseDouble(ranking2));
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