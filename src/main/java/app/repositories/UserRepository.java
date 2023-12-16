package app.repositories;

import app.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ModelRepository<User, Integer>{
    User findByKey(String key);
}
