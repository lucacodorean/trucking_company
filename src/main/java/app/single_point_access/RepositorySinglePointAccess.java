package app.single_point_access;

import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.repositories.implementation.RoleRepositoryImpl;
import app.repositories.implementation.UserRepositoryImpl;

public class RepositorySinglePointAccess {

    private RepositorySinglePointAccess() { }

    private static UserRepository userRepository;
    private static RoleRepository roleRepository;

    static {
        userRepository = new UserRepositoryImpl();
        roleRepository = new RoleRepositoryImpl();
    }

    public static UserRepository getUserRepository() { return userRepository; }
    public static RoleRepository getRoleRepository() { return roleRepository; }
}
