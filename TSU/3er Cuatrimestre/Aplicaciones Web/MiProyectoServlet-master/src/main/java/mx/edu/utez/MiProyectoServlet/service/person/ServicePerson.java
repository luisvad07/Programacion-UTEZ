package mx.edu.utez.MiProyectoServlet.service.person;

import mx.edu.utez.MiProyectoServlet.model.person.BeanPerson;
import mx.edu.utez.MiProyectoServlet.model.person.DaoPerson;
import mx.edu.utez.MiProyectoServlet.utils.EmailUtility;
import mx.edu.utez.MiProyectoServlet.utils.ResultAction;

import java.io.InputStream;
import java.util.List;

public class ServicePerson {
    DaoPerson daoPerson = new DaoPerson();

    public List<BeanPerson> showPersons(){
        return daoPerson.showPersons();
    }

    public ResultAction savePerson(BeanPerson person, InputStream image){
        ResultAction result = new ResultAction();
        if (daoPerson.savePerson(person, image)) {
            new EmailUtility().sendEmail(
                    person.getEmail(), person.getName(), person.getLastname());
            result.setResult(true);
            result.setMessage("Persona Registrada Correctamente");
            result.setStatus(200);
        }else {
            result.setResult(false);
            result.setMessage("Error al Registrar Persona");
            result.setStatus(400);
        }
        return result;
    }

    public BeanPerson findPerson(Long id){
        return daoPerson.findPerson(id);
    }

    public ResultAction updatePerson(BeanPerson person){
        ResultAction result = new ResultAction();
        if (daoPerson.updatePerson(person)) {
            result.setResult(true);
            result.setMessage("Persona Modificada Correctamente");
            result.setStatus(200);
        }else {
            result.setResult(false);
            result.setMessage("Error al Modificar Persona");
            result.setStatus(400);
        }
        return result;
    }

    public ResultAction deletePerson(Long id){
        ResultAction result = new ResultAction();
        if (daoPerson.deletePerson(id)) {
            result.setResult(true);
            result.setMessage("Persona Eliminada Correctamente");
            result.setStatus(200);
        }else {
            result.setResult(false);
            result.setMessage("Error al Eliminar Persona");
            result.setStatus(400);
        }
        return result;
    }
}
