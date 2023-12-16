package app.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository<T, IdType> {
    T save(T entity);
    T update(T entity);
    T findById(IdType id);
    T findByKey(String key);
    List<T> findAll();
    boolean delete(T entity);
}
