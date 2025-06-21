package mx.edu.utez.MiProyectoServlet.service.auth;

import mx.edu.utez.MiProyectoServlet.model.auth.BeanAuth;
import mx.edu.utez.MiProyectoServlet.model.auth.DaoAuth;

public class ServiceAuth {
    DaoAuth daoAuth = new DaoAuth();

    public BeanAuth login(String username, String password){
        return daoAuth.login(username, password);
    }
}
