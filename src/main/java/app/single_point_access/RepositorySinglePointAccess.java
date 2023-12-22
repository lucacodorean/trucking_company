package app.single_point_access;

import app.repositories.AddressRepository;
import app.repositories.RoleRepository;
import app.repositories.TruckRepository;
import app.repositories.UserRepository;
import app.repositories.implementation.AddressRepositoryImpl;
import app.repositories.implementation.RoleRepositoryImpl;
import app.repositories.implementation.TruckRepositoryImpl;
import app.repositories.implementation.UserRepositoryImpl;

public class RepositorySinglePointAccess {

    private RepositorySinglePointAccess() { }

    private static UserRepository       userRepository;
    private static RoleRepository       roleRepository;
    private static AddressRepository addressRepository;
    private static TruckRepository     truckRepository;

    static {
        userRepository    = new UserRepositoryImpl();
        roleRepository    = new RoleRepositoryImpl();
        addressRepository = new AddressRepositoryImpl();
        truckRepository   = new TruckRepositoryImpl();
    }

    public static UserRepository    getUserRepository()         { return userRepository;    }
    public static RoleRepository    getRoleRepository()         { return roleRepository;    }
    public static TruckRepository   getTruckRepository()        { return truckRepository;   }
    public static AddressRepository getAddressRepository()      { return addressRepository; }
}
