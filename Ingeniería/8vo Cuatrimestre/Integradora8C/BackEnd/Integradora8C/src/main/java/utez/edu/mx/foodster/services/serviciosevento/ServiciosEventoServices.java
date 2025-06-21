package utez.edu.mx.foodster.services.serviciosevento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.personalevento.PersonalEvento;
import utez.edu.mx.foodster.entities.personalevento.PersonalEventoRepository;
import utez.edu.mx.foodster.entities.serviciosevento.ServiciosEvento;
import utez.edu.mx.foodster.entities.serviciosevento.ServiciosEventoRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.utils.CurrentUserDetails;
import utez.edu.mx.foodster.utils.Response;
import utez.edu.mx.foodster.utils.RolesActuales;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiciosEventoServices {
    private final ServiciosEventoRepository repository;
    private final PersonalEventoRepository personalEventoRepository;
    private final CurrentUserDetails currentUserDetails;
    private final UsuariosRepository usuariosRepository;

    public ServiciosEventoServices(ServiciosEventoRepository repository, PersonalEventoRepository personalEventoRepository, CurrentUserDetails currentUserDetails, UsuariosRepository usuariosRepository) {
        this.repository = repository;
        this.personalEventoRepository = personalEventoRepository;
        this.currentUserDetails = currentUserDetails;
        this.usuariosRepository = usuariosRepository;
    }

    @Transactional(readOnly = true)
    public Response<List<ServiciosEvento>> getAll() {
        return new Response<>(this.repository.findAll(), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<ServiciosEvento>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)

    public Response<ServiciosEvento> getById(String id) {
        return new Response<>(this.repository.findByIdServicioEventoAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<ServiciosEvento>> getAllByIdEvento(String idEvento) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        Set<String> authorities = currentUserDetails.getCurrentUserAuthorities();
        if (usuario == null) {
            return new Response<>(null, true, 400, "Usuario no encontrado");
        }
        List<ServiciosEvento> serviciosEventos = this.repository.findAllByEventoAndActiveOrderByUltimaModificacionDesc(idEvento, true);
        if (serviciosEventos.isEmpty()) {
            return new Response<>(null, true, 400, "No hay servicios asignados");
        }
        Eventos evento = serviciosEventos.get(0).getEvento();
        Boolean esComensal = evento.getUsuario().getIdUsuario().equals(usuario.getIdUsuario());
        Boolean esPersonalAsignado = false;
        List<PersonalEvento> personalEventos = this.personalEventoRepository.findByIdEventoAndActive(idEvento, true);
        for (PersonalEvento personalEvento : personalEventos) {
            if (personalEvento.getPersonal().getUsuarios().getIdUsuario().equals(usuario.getIdUsuario())) {
                esPersonalAsignado = true;
                break;
            }
        }
        if (esComensal || esPersonalAsignado || authorities.contains(RolesActuales.ADMIN)) {
            return new Response<>(serviciosEventos, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No autorizado");
    }


    @Transactional(readOnly = true)
    public Response<List<ServiciosEvento>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<ServiciosEvento>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<ServiciosEvento> insert(ServiciosEvento serviciosEvento) {
        return new Response<>(this.repository.save(serviciosEvento), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<ServiciosEvento> update(ServiciosEvento serviciosEvento) {
        Optional<ServiciosEvento> update = this.repository.findById(serviciosEvento.getIdServicioEvento());
        if (update.isPresent()) {
            return new Response<>(this.repository.saveAndFlush(serviciosEvento), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para actualizar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> delete(String id) {
        Optional<ServiciosEvento> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(this.repository.saveAndFlush(entity.get()).getActive(), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<ServiciosEvento> changeStatus(String id) {
        Optional<ServiciosEvento> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(this.repository.saveAndFlush(entity.get()), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar estado");
    }
}
