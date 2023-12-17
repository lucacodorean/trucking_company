package app.services.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import app.models.User;
import app.services.UserService;
import app.repositories.UserRepository;
import app.single_point_access.RepositorySinglePointAccess;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = RepositorySinglePointAccess.getUserRepository();

    @Override public User save(User user)           { return userRepository.save(user);     }
    @Override public List<User> findAll()           { return userRepository.findAll();      }
    @Override public User findByKey(String key)     { return userRepository.findByKey(key); }
    @Override public User findById(Integer id)      { return userRepository.findById(id);   }
    @Override public User update(User user)         { return userRepository.update(user);   }
    @Override public boolean delete(User user)      { return userRepository.delete(user);   }
}
