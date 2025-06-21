package mx.edu.utez.PersonalServices.controller.personal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import mx.edu.utez.PersonalServices.model.personal.BeanPersonal;
import mx.edu.utez.PersonalServices.model.personal.DaoPersonal;

import java.util.List;

@Path("/api/personal")

public class PersonalServices {
    @GET
    @Path("/")
    @Produces(value = {"application/json"})
    public List<BeanPersonal> getAll(){
        return new DaoPersonal().findAll();
    }
}
