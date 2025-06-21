package utez.edu.mx.foodster.services.direcciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.dtos.direcciones.DireccionesDto;
import utez.edu.mx.foodster.dtos.direccionesusuarios.DireccionesUsuariosDto;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;
import utez.edu.mx.foodster.entities.direcciones.DireccionesRepository;
import utez.edu.mx.foodster.entities.direccionesusuario.DireccionesUsuarioRepository;
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
public class DireccionesServices {
    private final DireccionesRepository repository;

    private final DireccionesUsuarioRepository direccionesUsuarioRepository;

    private final UsuariosRepository usuariosRepository;

    private final CurrentUserDetails currentUserDetails;

    public DireccionesServices(DireccionesRepository repository, DireccionesUsuarioRepository direccionesUsuarioRepository, UsuariosRepository usuariosRepository, CurrentUserDetails currentUserDetails) {
        this.repository = repository;
        this.direccionesUsuarioRepository = direccionesUsuarioRepository;
        this.usuariosRepository = usuariosRepository;
        this.currentUserDetails = currentUserDetails;
    }

    @Transactional(readOnly = true)
    public Response<List<Direcciones>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Direcciones>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Direcciones> getById(String id) {
        return new Response<>(this.repository.findByIdDireccionAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Direcciones>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Direcciones>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Direcciones>> getAllByUsuario(String id) {
        return new Response<>(this.repository.findAllByIdUsuarioAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Direcciones>> getAllByUsuario() {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario != null) {
            return new Response<>(this.repository.findAllByIdUsuarioAndActive(usuario.getIdUsuario(), true), false, 200, "OK");
        } else {
            return new Response<>(null, true, 404, "Usuario no encontrado");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Direcciones> insert(DireccionesDto direcciones) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Set<String> authorities = this.currentUserDetails.getCurrentUserAuthorities();
        Usuarios usuarios = authorities.contains(RolesActuales.ADMIN) ?
                this.usuariosRepository.findByIdUsuarioAndActive(direcciones.getIdUsuario(), true)
                : this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuarios == null) {
            return new Response<>(null, true, 400, "Usuario no encontrado");
        }
        direcciones.setActive(true);
        Direcciones direccion = this.repository.save(direcciones.toEntity());
        DireccionesUsuariosDto direccionesUsuario = new DireccionesUsuariosDto();
        direccionesUsuario.setDirecciones(direccion);
        direccionesUsuario.setUsuarios(usuarios);
        direccionesUsuario.setActive(true);
        this.direccionesUsuarioRepository.save(direccionesUsuario.toEntity());
        return new Response<>(direccion, false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Direcciones> update(Direcciones direcciones) {
        Optional<Direcciones> entity = this.repository.findById(direcciones.getIdDireccion());
        if (entity.isPresent()) {
            return new Response<>(this.repository.saveAndFlush(direcciones), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para actualizar");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> delete(String id) {
        Optional<Direcciones> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            this.repository.saveAndFlush(entity.get());
            return new Response<>(true, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> changeStatus(String id) {
        Optional<Direcciones> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            this.repository.saveAndFlush(entity.get());
            return new Response<>(true, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar status");
    }
}
