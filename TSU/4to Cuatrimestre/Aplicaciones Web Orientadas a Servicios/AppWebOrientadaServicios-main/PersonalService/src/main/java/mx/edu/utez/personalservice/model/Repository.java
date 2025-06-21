package mx.edu.utez.personalservice.model;
import mx.edu.utez.personalservice.utils.Response;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    Response<T> findById(Long id);
    Response<T> save(T object);
    Response<T> update(T object);
    Response<T> delete(Long id);
}
