package app.single_point_access;

import app.repositories.implementation.ActivityLogRepositoryImpl;
import app.repositories.implementation.ShipmentRepositoryImpl;
import app.repositories.implementation.AddressRepositoryImpl;
import app.repositories.implementation.TrailerRepositoryImpl;
import app.repositories.implementation.TruckRepositoryImpl;
import app.repositories.implementation.UserRepositoryImpl;
import app.repositories.implementation.RoleRepositoryImpl;

import app.repositories.ActivityLogRepository;
import app.repositories.ShipmentRepository;
import app.repositories.AddressRepository;
import app.repositories.TrailerRepository;
import app.repositories.TruckRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;


public class RepositorySinglePointAccess {

    private RepositorySinglePointAccess() { }

    private static UserRepository           userRepository;
    private static RoleRepository           roleRepository;
    private static TruckRepository          truckRepository;
    private static AddressRepository        addressRepository;
    private static TrailerRepository        trailerRepository;
    private static ShipmentRepository       shipmentRepository;
    private static ActivityLogRepository    activityRepository;

    static {
        userRepository      = new UserRepositoryImpl();
        roleRepository      = new RoleRepositoryImpl();
        truckRepository     = new TruckRepositoryImpl();
        addressRepository   = new AddressRepositoryImpl();
        trailerRepository   = new TrailerRepositoryImpl();
        shipmentRepository  = new ShipmentRepositoryImpl();
        activityRepository  = new ActivityLogRepositoryImpl();
    }

    public static UserRepository        getUserRepository()         { return userRepository;    }
    public static RoleRepository        getRoleRepository()         { return roleRepository;    }
    public static TruckRepository       getTruckRepository()        { return truckRepository;   }
    public static AddressRepository     getAddressRepository()      { return addressRepository; }
    public static TrailerRepository     getTrailerRepository()      { return trailerRepository; }
    public static ShipmentRepository    getShipmentRepository()     { return shipmentRepository; }
    public static ActivityLogRepository getActivityRepository()     { return activityRepository; }
}
