package utez.edu.mx.foodster.services.tarjetas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.tarjetas.Tarjetas;
import utez.edu.mx.foodster.entities.tarjetas.TarjetasRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.utils.CurrentUserDetails;
import utez.edu.mx.foodster.utils.Encrypt;
import utez.edu.mx.foodster.utils.Response;

import java.security.GeneralSecurityException;
import java.sql.SQLDataException;
import java.util.List;

@Service
public class TarjetasService {
    private final TarjetasRepository repository;
    private final UsuariosRepository usuariosRepository;
    private final CurrentUserDetails currentUserDetails;

    public TarjetasService(TarjetasRepository repository, UsuariosRepository usuariosRepository, CurrentUserDetails currentUserDetails) {
        this.repository = repository;
        this.usuariosRepository = usuariosRepository;
        this.currentUserDetails = currentUserDetails;
    }

    @Transactional(readOnly = true)
    public Response<List<Tarjetas>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Tarjetas>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Tarjetas> getById(String id) {
        return new Response<>(this.repository.findByIdTarjetaAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Tarjetas>> getAllByIdUsuario() throws GeneralSecurityException {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario != null) {
            List<Tarjetas> tarjetas = this.repository.findByIdUsuarioAndActive(usuario.getIdUsuario(), true);
            for (Tarjetas tarjeta : tarjetas) {
                tarjeta.setNumeroTarjeta(Encrypt.decrypt(tarjeta.getNumeroTarjeta()));
                tarjeta.setCvv(Encrypt.decrypt(tarjeta.getCvv()));
                tarjeta.setNombreTarjeta(Encrypt.decrypt(tarjeta.getNombreTarjeta()));
            }
            return new Response<>(tarjetas, false, 200, "OK");
        } else {
            return new Response<>(null, true, 404, "Usuario no encontrado");
        }

    }

    @Transactional(readOnly = true)
    public Response<List<Tarjetas>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }


    @Transactional(readOnly = true)
    public Response<Page<Tarjetas>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Tarjetas> save(Tarjetas tarjetas) throws GeneralSecurityException {
        tarjetas.setActive(true);
        tarjetas.setNumeroTarjeta(Encrypt.encrypt(tarjetas.getNumeroTarjeta()));
        tarjetas.setCvv(Encrypt.encrypt(tarjetas.getCvv()));
        tarjetas.setNombreTarjeta(Encrypt.encrypt(tarjetas.getNombreTarjeta()));
        return new Response<>(this.repository.save(tarjetas), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Tarjetas> update(Tarjetas tarjetas) throws GeneralSecurityException {
        tarjetas.setNumeroTarjeta(Encrypt.encrypt(tarjetas.getNumeroTarjeta()));
        tarjetas.setCvv(Encrypt.encrypt(tarjetas.getCvv()));
        tarjetas.setNombreTarjeta(Encrypt.encrypt(tarjetas.getNombreTarjeta()));
        return new Response<>(this.repository.save(tarjetas), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Tarjetas> delete(String id) {
        Tarjetas tarjetas = this.repository.findByIdTarjetaAndActive(id, true);
        tarjetas.setActive(!tarjetas.getActive());
        return new Response<>(this.repository.save(tarjetas), false, 200, "OK");
    }


}
