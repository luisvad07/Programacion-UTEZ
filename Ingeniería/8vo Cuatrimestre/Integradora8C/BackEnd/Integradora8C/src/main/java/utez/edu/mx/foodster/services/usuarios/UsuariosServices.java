package utez.edu.mx.foodster.services.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.dtos.usuarios.CambiarContraDto;
import utez.edu.mx.foodster.dtos.usuarios.UsuariosPublicDto;
import utez.edu.mx.foodster.entities.roles.RolesRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.services.captcha.CaptchaService;
import utez.edu.mx.foodster.utils.CurrentUserDetails;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuariosServices {
    private final UsuariosRepository repository;

    private final RolesRepository rolesRepository;


    private final PasswordEncoder passwordEncoder;

    private final CaptchaService captchaService;

    private final CurrentUserDetails currentUserDetails;

    public UsuariosServices(UsuariosRepository repository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, CaptchaService captchaService, CurrentUserDetails currentUserDetails) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
        this.currentUserDetails = currentUserDetails;
    }

    @Transactional(readOnly = true)
    public Response<List<Usuarios>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Usuarios>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Usuarios>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Usuarios>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Usuarios getByCorreo(String correo) {
        return this.repository.findByCorreoAndActive(correo, true);
    }

    @Transactional(readOnly = true)
    public Response<Usuarios> getById(String id) {
        return new Response<>(this.repository.findByIdUsuarioAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Usuarios> getById() {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        return new Response<>(this.repository.findByCorreoAndActive(userDetails.getUsername(), true), false, 200, "OK");
    }


    @Transactional(rollbackFor = {SQLException.class})
    public Response<Usuarios> insert(Usuarios usuarios) {
        Usuarios existe = this.repository.findByCorreoAndActive(usuarios.getCorreo(), true);
        if (existe != null) {
            return new Response<>(null, true, 400, "Correo ya registrado");
        }
        usuarios.setContrasena(this.passwordEncoder.encode(usuarios.getContrasena()));
        return new Response<>(this.repository.save(usuarios), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Usuarios> publicInsert(UsuariosPublicDto usuarios) {
        Usuarios existe = this.repository.findByCorreoAndActive(usuarios.getCorreo(), true);
        if (existe != null) {
            return new Response<>(null, true, 400, "Correo ya registrado");
        }
        Boolean captchaVerification = this.captchaService.verificarCaptchaBoolean(usuarios.getSolucion());
        if (captchaVerification == null || !captchaVerification) {
            return new Response<>(null, true, 400, "Captcha invalido");
        }
        Usuarios usuario = usuarios.toEntity();
        usuario.setRoles(Set.of(this.rolesRepository.findByNombreAndActive("CLIENTE", true)));
        usuario.setActive(true);
        usuario.setContrasena(this.passwordEncoder.encode(usuario.getContrasena()));
        return new Response<>(this.repository.save(usuario), false, 200, "OK");
    }


    @Transactional(rollbackFor = {SQLException.class})
    public Response<Usuarios> update(Usuarios usuarios) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Set<String> authorities = this.currentUserDetails.getCurrentUserAuthorities();
        Usuarios usuario = this.repository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario != null) {
            if (usuarios.getIdUsuario().equals(usuario.getIdUsuario()) || authorities.contains("ADMIN")) {
                return new Response<>(this.repository.saveAndFlush(usuarios), false, 200, "OK");
            }
            return new Response<>(null, true, 400, "No autorizado para actualizar");
        }
        return new Response<>(null, true, 400, "No encontrado para actualizar");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> delete(String id) {
        Optional<Usuarios> usuarios = this.repository.findById(id);
        if (usuarios.isPresent()) {
            usuarios.get().setActive(!usuarios.get().getActive());
            return new Response<>(true, false, 200, "Eliminado correctamente");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> changeStatus(String id) {
        Optional<Usuarios> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(true, false, 200, "Estatus cambiado!");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar estatus");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> changePassword(CambiarContraDto cambiarContraDto) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Set<String> authorities = this.currentUserDetails.getCurrentUserAuthorities();
        Usuarios usuario = this.repository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario != null) {
            if (cambiarContraDto.getCorreo().equals(usuario.getCorreo()) || authorities.contains("ADMIN")) {
                if (this.passwordEncoder.matches(cambiarContraDto.getContrasena(), usuario.getContrasena())) {
                    usuario.setContrasena(this.passwordEncoder.encode(cambiarContraDto.getNuevaContrasena()));
                    this.repository.saveAndFlush(usuario);
                    return new Response<>(true, false, 200, "Contraseña cambiada correctamente");
                }
                return new Response<>(null, true, 400, "Contraseña incorrecta");
            }
            return new Response<>(null, true, 400, "No autorizado para cambiar contraseña");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar contraseña");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> changePasswordAdmin(String id, String contrasena) {
        Optional<Usuarios> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setContrasena(this.passwordEncoder.encode(contrasena));
            return new Response<>(true, false, 200, "Contraseña cambiada correctamente");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar contraseña");
    }

    public long count() {
        return this.repository.count();
    }


}
