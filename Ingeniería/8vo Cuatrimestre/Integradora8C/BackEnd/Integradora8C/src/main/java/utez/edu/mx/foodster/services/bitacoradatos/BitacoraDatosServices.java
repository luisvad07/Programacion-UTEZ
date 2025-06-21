package utez.edu.mx.foodster.services.bitacoradatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.bitacoradatos.BitacoraDatos;
import utez.edu.mx.foodster.entities.bitacoradatos.BitacoraDatosRepository;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@Service
@Transactional
public class BitacoraDatosServices {
    private final BitacoraDatosRepository repository;

    public BitacoraDatosServices(BitacoraDatosRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<BitacoraDatos>> getAll() {
        return new Response<>(
                this.repository.findAllByOrderByCreadoEnDesc(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<Page<BitacoraDatos>> getAll(Pageable pageable) {
        return new Response<>(
                this.repository.findAllByOrderByCreadoEnDesc(pageable),
                false,
                200,
                "OK"
        );
    }
}
