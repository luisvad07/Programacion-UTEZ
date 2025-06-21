package mx.edu.utez.personalservice.controller.personal;

import mx.edu.utez.personalservice.model.personal.BeanPersonal;
import mx.edu.utez.personalservice.model.personal.DaoPersonal;
import mx.edu.utez.personalservice.utils.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/personal")
public class PersonalServices {
    @GET
    @Path("/")
    @Produces(value = {"application/json"})

    public List<BeanPersonal> getAll() {
        return new DaoPersonal().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response<BeanPersonal> getById(@PathParam("id") Long id){
        return new DaoPersonal().findById(id);
    }

    @POST /*Inserción*/
    @Path("/") /**/
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response<BeanPersonal> save(BeanPersonal person){
        return new DaoPersonal().save(person);
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response<BeanPersonal> update(BeanPersonal person){
        return new DaoPersonal().update(person);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response<BeanPersonal> delete(@PathParam("id") Long id){
        return new DaoPersonal().delete(id);
    }
}
