package app.single_point_access;

import app.repositories.AddressRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.repositories.implementation.AddressRepositoryImpl;
import app.repositories.implementation.RoleRepositoryImpl;
import app.repositories.implementation.UserRepositoryImpl;

public class RepositorySinglePointAccess {

    private RepositorySinglePointAccess() { }

    private static UserRepository       userRepository;
    private static RoleRepository       roleRepository;
    private static AddressRepository addressRepository;

    static {
        userRepository    = new UserRepositoryImpl();
        roleRepository    = new RoleRepositoryImpl();
        addressRepository = new AddressRepositoryImpl();
    }

    public static UserRepository    getUserRepository()         { return userRepository; }
    public static RoleRepository    getRoleRepository()         { return roleRepository; }
    public static AddressRepository getAddressRepository()      { return addressRepository; }
}
