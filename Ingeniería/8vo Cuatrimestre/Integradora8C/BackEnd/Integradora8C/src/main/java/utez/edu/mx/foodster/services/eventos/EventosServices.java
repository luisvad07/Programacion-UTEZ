package utez.edu.mx.foodster.services.eventos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.dtos.eventos.EventoConServicios;
import utez.edu.mx.foodster.dtos.personalevento.PersonalEventoDto;
import utez.edu.mx.foodster.dtos.serviciosevento.ServiciosEventoDto;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonal;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonalRepository;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.eventos.EventosRepository;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;
import utez.edu.mx.foodster.entities.paquetes.PaquetesRepository;
import utez.edu.mx.foodster.entities.personal.Personal;
import utez.edu.mx.foodster.entities.personal.PersonalRepository;
import utez.edu.mx.foodster.entities.personalevento.PersonalEvento;
import utez.edu.mx.foodster.entities.personalevento.PersonalEventoRepository;
import utez.edu.mx.foodster.entities.servicios.Servicios;
import utez.edu.mx.foodster.entities.servicios.ServiciosRepository;
import utez.edu.mx.foodster.entities.serviciosevento.ServiciosEventoRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.utils.CurrentUserDetails;
import utez.edu.mx.foodster.utils.EventoEstados;
import utez.edu.mx.foodster.utils.Response;
import utez.edu.mx.foodster.utils.RolesActuales;

import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventosServices {
    private final EventosRepository repository;
    private final PaquetesRepository paquetesRepository;
    private final ServiciosEventoRepository serviciosEventoRepository;

    private final ServiciosRepository serviciosRepository;

    private final PersonalEventoRepository personalEventoRepository;

    private final CategoriasPersonalRepository categoriasPersonalRepository;

    private final PersonalRepository personalRepository;

    private final UsuariosRepository usuariosRepository;

    private final CurrentUserDetails currentUserDetails;

    public EventosServices(EventosRepository repository, PaquetesRepository paquetesRepository, ServiciosEventoRepository serviciosEventoRepository, ServiciosRepository serviciosRepository, PersonalEventoRepository personalEventoRepository, CategoriasPersonalRepository categoriasPersonalRepository, PersonalRepository personalRepository, UsuariosRepository usuariosRepository, CurrentUserDetails currentUserDetails) {
        this.repository = repository;
        this.paquetesRepository = paquetesRepository;
        this.serviciosEventoRepository = serviciosEventoRepository;
        this.serviciosRepository = serviciosRepository;
        this.personalEventoRepository = personalEventoRepository;
        this.categoriasPersonalRepository = categoriasPersonalRepository;
        this.personalRepository = personalRepository;
        this.usuariosRepository = usuariosRepository;
        this.currentUserDetails = currentUserDetails;
    }

    @Transactional(readOnly = true)
    public Response<List<Eventos>> getAll() {
        return new Response<>(this.repository.findAll(), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Eventos>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Eventos> getById(String id) {
        return new Response<>(this.repository.findByIdEventoAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Eventos>> getAllByIdUsuario(String idUsuario) {
        return new Response<>(this.repository.findAllByIdUsuarioAndActive(idUsuario, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Eventos>> getAllByIdUsuario() {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario != null) {
            return new Response<>(this.repository.findAllByIdUsuarioAndActive(usuario.getIdUsuario(), true), false, 200, "OK");
        } else {
            return new Response<>(null, true, 404, "Los eventos asociados al usuario no se encontraron");
        }
    }

    @Transactional(readOnly = true)
    public Response<List<Eventos>> getAllByPersonalIdUsuario(String idUsuario) {
        return new Response<>(this.repository.findAllByPersonalIdUsuarioAndActive(idUsuario, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Eventos>> getAllByPersonalIdUsuario() {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario != null) {
            return new Response<>(this.repository.findAllByPersonalIdUsuarioAndActive(usuario.getIdUsuario(), true), false, 200, "OK");
        } else {
            return new Response<>(null, true, 404, "Usuario no encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Response<List<Eventos>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Eventos>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Eventos> insert(EventoConServicios evento) {
        try {
            Eventos eventoDetalles = evento.getEvento().toEntity();
            eventoDetalles.setActive(true);
            eventoDetalles.setPersonalizado(true);
            if (evento.getIdPaquete() != null) {
                Paquetes paquete = this.paquetesRepository.findByIdPaqueteAndActive(evento.getIdPaquete(), true);
                paquete.setNumeroPedidos(paquete.getNumeroPedidos() + 1);
                eventoDetalles.setPersonalizado(false);
                this.paquetesRepository.save(paquete);
            }
            eventoDetalles.setEstado(EventoEstados.EN_PROCESO);
            Eventos eventoGuardado = this.repository.save(eventoDetalles);
            evento.getServicios().forEach(servicio -> {
                Servicios servicioActual = this.serviciosRepository.findByIdServicioAndActive(servicio.getIdServicio(), true);
                ServiciosEventoDto serviciosEvento = new ServiciosEventoDto();
                serviciosEvento.setEvento(eventoGuardado);
                serviciosEvento.setServicio(servicioActual);
                serviciosEvento.setCantidad(servicio.getCantidad());
                serviciosEvento.setActive(true);
                this.serviciosEventoRepository.save(serviciosEvento.toEntity());
                if (servicioActual.getExistencias() != null && servicioActual.getExistencias() > 0) {
                    servicioActual.setExistencias(servicioActual.getExistencias() - servicio.getCantidad());
                    this.serviciosRepository.save(servicioActual);
                }
            });
            CategoriasPersonal mesero = this.categoriasPersonalRepository.findByNombreAndActive("Mesero", true);
            CategoriasPersonal chef = this.categoriasPersonalRepository.findByNombreAndActive("Chef", true);
            Long numeroPersonas = eventoDetalles.getNumeroPersonas();
            Long numeroMeseros = numeroPersonas < 20 ? 1 : numeroPersonas / 20;
            Long numeroChef = numeroPersonas < 150 ? 1 : numeroPersonas / 150;
            Long chefDisponibles = this.personalRepository.countRandomPersonalByCategoriaAndEventos(eventoDetalles.getFechaHoraInicio(), eventoDetalles.getFechaHoraFin(), chef.getIdCategoria(), true);
            Long meseroDisponibles = this.personalRepository.countRandomPersonalByCategoriaAndEventos(eventoDetalles.getFechaHoraInicio(), eventoDetalles.getFechaHoraFin(), mesero.getIdCategoria(), true);
            if (chefDisponibles < numeroChef || meseroDisponibles < numeroMeseros) {
                eventoGuardado.setEstado(EventoEstados.PENDIENTE_PERSONAL);
                return new Response<>(this.repository.saveAndFlush(eventoGuardado), false, 200, "Evento creado, pero no hay personal suficiente, quedo pendiente");
            }
            for (int i = 0; i < numeroMeseros; i++) {
                Personal personal = this.personalRepository.findRandomPersonalByCategoriaAndEventos(eventoDetalles.getFechaHoraInicio(), eventoDetalles.getFechaHoraFin(), mesero.getIdCategoria(), true);
                PersonalEventoDto personalEventoDto = new PersonalEventoDto();
                personalEventoDto.setEventos(eventoGuardado);
                personalEventoDto.setPersonal(personal);
                personalEventoDto.setActive(true);
                this.personalEventoRepository.save(personalEventoDto.toEntity());

            }
            for (int i = 0; i < numeroChef; i++) {
                Personal personal = this.personalRepository.findRandomPersonalByCategoriaAndEventos(eventoDetalles.getFechaHoraInicio(), eventoDetalles.getFechaHoraFin(), chef.getIdCategoria(), true);
                PersonalEventoDto personalEventoDto = new PersonalEventoDto();
                personalEventoDto.setEventos(eventoGuardado);
                personalEventoDto.setPersonal(personal);
                personalEventoDto.setActive(true);
                this.personalEventoRepository.save(personalEventoDto.toEntity());
            }
            Double costoTotal = this.serviciosEventoRepository.costoTotal(eventoGuardado.getIdEvento(), true);
            eventoGuardado.setCostoTotal(costoTotal);
            return new Response<>(this.repository.saveAndFlush(eventoGuardado), false, 200, "OK");
        } catch (Exception e) {
            return new Response<>(null, true, 500, e.getMessage());
        }

    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Eventos> update(Eventos eventos) {
        Optional<Eventos> update = this.repository.findById(eventos.getIdEvento());
        if (update.isPresent()) {
            return new Response<>(this.repository.saveAndFlush(eventos), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para actualizar");
    }


    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Eventos> setFinalizado(String id) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        Personal personal = this.personalRepository.findByIdUsuarioAndActive(usuario.getIdUsuario(), true);
        List<PersonalEvento> personalEvento = this.personalEventoRepository.findByIdEventoAndActive(id, true);
        Set<String> authorities = this.currentUserDetails.getCurrentUserAuthorities();
        Eventos evento = this.repository.findByIdEventoAndActive(id, true);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (evento != null && evento.getFechaHoraFin().before(now) && evento.getEstado().equals(EventoEstados.EN_PROCESO)) {
            for (PersonalEvento personalEventoEv : personalEvento) {
                String idPersonal = personalEventoEv.getPersonal().getIdPersonal();
                Boolean isEnEvento = idPersonal.equals(personal.getIdPersonal());
                Boolean isChef = personalEventoEv.getPersonal().getCategoria().getNombre().equals(RolesActuales.CHEF);
                if (isEnEvento && isChef || authorities.contains(RolesActuales.ADMIN)) {
                    evento.setEstado(EventoEstados.FINALIZADO);
                    return new Response<>(this.repository.saveAndFlush(evento), false, 200, "OK");
                }
            }
        }
        return new Response<>(null, true, 400, "No encontrado para finalizar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Eventos> setCancelado(String id) {
        UserDetails userDetails = this.currentUserDetails.getCurrentUserDetails();
        if (userDetails == null) {
            return new Response<>(null, true, 400, "User details not found");
        }

        Usuarios usuario = this.usuariosRepository.findByCorreoAndActive(userDetails.getUsername(), true);
        if (usuario == null) {
            return new Response<>(null, true, 400, "Usuario no encontrado");
        }

        Set<String> authorities = this.currentUserDetails.getCurrentUserAuthorities();
        if (authorities == null) {
            return new Response<>(null, true, 400, "No tiene permisos");
        }

        Eventos evento = this.repository.findByIdEventoAndActive(id, true);
        if (evento == null) {
            return new Response<>(null, true, 400, "Evento no encontrado");
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (evento.getFechaHoraInicio().after(now) && evento.getEstado().equals(EventoEstados.EN_PROCESO) && evento.getUsuario() != null && evento.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()) || authorities.contains(RolesActuales.ADMIN)) {
            evento.setEstado(EventoEstados.CANCELADO);
            return new Response<>(this.repository.saveAndFlush(evento), false, 200, "OK");
        }

        return new Response<>(null, true, 400, "Event not found for cancellation");
    }


    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> delete(String id) {
        Optional<Eventos> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            this.repository.save(entity.get());
            return new Response<>(true, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }
}
