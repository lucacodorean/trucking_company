package app.services;

import app.dto.UserDTO;
import app.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends ModelService<User>{ }
