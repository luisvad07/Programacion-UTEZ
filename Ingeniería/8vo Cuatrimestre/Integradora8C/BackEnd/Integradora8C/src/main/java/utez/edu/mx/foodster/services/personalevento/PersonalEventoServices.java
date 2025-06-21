package utez.edu.mx.foodster.services.personalevento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.personalevento.PersonalEvento;
import utez.edu.mx.foodster.entities.personalevento.PersonalEventoRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.utils.CurrentUserDetails;
import utez.edu.mx.foodster.utils.Response;
import utez.edu.mx.foodster.utils.RolesActuales;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PersonalEventoServices {
    private final PersonalEventoRepository repository;
    private final CurrentUserDetails currentUserDetails;
    private final UsuariosRepository usuariosRepository;

    public PersonalEventoServices(PersonalEventoRepository repository, CurrentUserDetails currentUserDetails, UsuariosRepository usuariosRepository) {
        this.repository = repository;
        this.currentUserDetails = currentUserDetails;
        this.usuariosRepository = usuariosRepository;
    }

    @Transactional(readOnly = true)
    public Response<List<PersonalEvento>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<PersonalEvento>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<PersonalEvento> getById(String id) {
        return new Response<>(this.repository.findByIdPersonalEventoAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<PersonalEvento>> getAllByIdEvento(String idEvento) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        Set<String> authorities = currentUserDetails.getCurrentUserAuthorities();
        if (usuario == null) {
            return new Response<>(null, true, 400, "Usuario no encontrado");
        }
        List<PersonalEvento> personalEventos = this.repository.findByIdEventoAndActive(idEvento, true);
        if (personalEventos.isEmpty()) {
            return new Response<>(null, true, 400, "No hay personal asignado");
        }
        Eventos evento = personalEventos.get(0).getEventos();
        Boolean esComensal = evento.getUsuario().getIdUsuario().equals(usuario.getIdUsuario());
        Boolean esPersonalAsignado = false;
        for (PersonalEvento personalEvento : personalEventos) {
            if (personalEvento.getPersonal().getUsuarios().getIdUsuario().equals(usuario.getIdUsuario())) {
                esPersonalAsignado = true;
                break;
            }
        }
        if (esComensal || esPersonalAsignado || authorities.contains(RolesActuales.ADMIN)) {
            return new Response<>(personalEventos, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No autorizado");
    }

    @Transactional(readOnly = true)
    public Response<List<PersonalEvento>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<PersonalEvento>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<PersonalEvento> insert(PersonalEvento personalEvento) {
        return new Response<>(this.repository.save(personalEvento), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<PersonalEvento> update(PersonalEvento personalEvento) {
        Optional<PersonalEvento> entityUpdate = this.repository.findById(personalEvento.getIdPersonalEvento());
        if (entityUpdate.isPresent()) {
            return new Response<>(this.repository.saveAndFlush(personalEvento), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> delete(String id) {
        Optional<PersonalEvento> personalEvento = this.repository.findById(id);
        if (personalEvento.isPresent()) {
            personalEvento.get().setActive(!personalEvento.get().getActive());
            return new Response<>(this.repository.saveAndFlush(personalEvento.get()).getActive(), false, 200, "Eliminado correctamente");
        }
        return new Response<>(null, true, 400, "No encontrado");
    }
}
