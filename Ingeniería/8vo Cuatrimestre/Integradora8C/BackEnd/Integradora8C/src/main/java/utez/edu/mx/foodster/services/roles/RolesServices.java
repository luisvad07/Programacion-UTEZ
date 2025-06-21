package utez.edu.mx.foodster.services.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.roles.Roles;
import utez.edu.mx.foodster.entities.roles.RolesRepository;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolesServices {
    private final RolesRepository repository;

    @Autowired
    public RolesServices(RolesRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Roles>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Roles> getById(String id) {
        return new Response<>(this.repository.findByIdRolAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Roles> getByNombre(String nombre) {
        return new Response<>(this.repository.findByNombreAndActive(nombre, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Roles getByNombreAndActive(String nombre, Boolean active) {
        return this.repository.findByNombreAndActive(nombre, active);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Roles> insert(Roles roles) {
        return new Response<>(this.repository.save(roles), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Roles> update(Roles roles) {
        Optional<Roles> entityUpdate = this.repository.findById(roles.getIdRol());
        if (entityUpdate.isPresent()) {
            return new Response<>(this.repository.saveAndFlush(roles), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> delete(String id) {
        Optional<Roles> roles = this.repository.findById(id);
        if (roles.isPresent()) {
            roles.get().setActive(!roles.get().getActive());
            return new Response<>(this.repository.saveAndFlush(roles.get()).getActive(), false, 200, "Eliminado correctamente");
        }
        return new Response<>(null, true, 400, "No encontrado");
    }
}
