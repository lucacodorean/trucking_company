package app.services;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ModelService<T> {
    T save(T entity);
    T update(T entity);
    T findByKey(String key);
    T findById(Integer id);
    List<T> findAll();
    boolean delete(T entity);
}