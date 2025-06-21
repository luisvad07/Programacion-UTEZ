package mx.edu.utez.sda.springmvc.control;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/peliculas")
public class ControlPeliculas {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @Secured({"ROLE_RECE", "ROLE_ADULTO", "ROLE_INF"})
    public String index(){
        return "peliculas";
    };

    @RequestMapping(value = { "/accion", "/terror", "/romanticos"}, method = RequestMethod.GET)
    @Secured({"ROLE_ADULTO"})
    public String peliculasAdultos() {
        return "peliculas";
    }

    @RequestMapping(value = {"/aventura", "/comedia", "/animados"}, method = RequestMethod.GET)
    @Secured({"ROLE_INF", "ROLE_ADULTO"})
    public String peliculasNinos() {
        return "peliculas";
    }
}