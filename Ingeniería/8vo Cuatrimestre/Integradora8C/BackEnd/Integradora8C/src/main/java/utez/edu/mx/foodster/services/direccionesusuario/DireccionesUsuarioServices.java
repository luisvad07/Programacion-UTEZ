package utez.edu.mx.foodster.services.direccionesusuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;
import utez.edu.mx.foodster.entities.direcciones.DireccionesRepository;
import utez.edu.mx.foodster.entities.direccionesusuario.DireccionesUsuario;
import utez.edu.mx.foodster.entities.direccionesusuario.DireccionesUsuarioRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;

@Service
public class DireccionesUsuarioServices {
    private final DireccionesUsuarioRepository repository;
    private final UsuariosRepository usuariosRepository;
    private final DireccionesRepository direccionesRepository;

    public DireccionesUsuarioServices(DireccionesUsuarioRepository repository, UsuariosRepository usuariosRepository, DireccionesRepository direccionesRepository) {
        this.repository = repository;
        this.usuariosRepository = usuariosRepository;
        this.direccionesRepository = direccionesRepository;
    }

    @Transactional(readOnly = true)
    public Response<List<DireccionesUsuario>> getAll(){
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<Page<DireccionesUsuario>> getAll(Pageable pageable){
        return new Response<>(
                this.repository.findAll(pageable),
                false,
                200,
                "OK"
        );
    }


    @Transactional(readOnly = true)
    public Response<DireccionesUsuario> getById(String id){
        return new Response<>(
                this.repository.findByIdDireccionUsuarioAndActive(id, true),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<List<DireccionesUsuario>> getAllByUsuarios(String idUsuario){
        Optional<Usuarios> usuario = this.usuariosRepository.findById(idUsuario);
        if(usuario.isPresent()){
            return new Response<>(
                    this.repository.findAllByUsuarios(usuario.get()),
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "No encontramos nada con el usuario"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<DireccionesUsuario> insert(DireccionesUsuario direccionesUsuario){
        Direcciones direcciones = this.direccionesRepository.save(direccionesUsuario.getDirecciones());
        direccionesUsuario.setDirecciones(direcciones);
        return new Response<>(
                this.repository.save(direccionesUsuario),
                false,
                200,
                "OK"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<DireccionesUsuario> update(DireccionesUsuario direccionesUsuario){
        Optional<DireccionesUsuario> entity = this.repository.findById(direccionesUsuario.getIdDireccionUsuario());
        if(entity.isPresent()){
            return new Response<>(
                    this.repository.saveAndFlush(direccionesUsuario),
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "No encontrado"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> delete(String id){
        Optional<DireccionesUsuario> entity = this.repository.findById(id);
        if(entity.isPresent()){
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(
                    this.repository.saveAndFlush(entity.get()).getActive(),
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "No encontrado"
        );
    }
}
