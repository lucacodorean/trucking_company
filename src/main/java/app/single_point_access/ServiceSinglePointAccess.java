package app.single_point_access;


import app.services.implementation.AddressServiceImpl;
import app.services.implementation.RoleServiceImpl;
import app.services.implementation.TruckServiceImpl;
import app.services.implementation.UserServiceImpl;
import app.services.AddressService;
import app.services.RoleService;
import app.services.TruckService;
import app.services.UserService;

public class ServiceSinglePointAccess {

    private ServiceSinglePointAccess() { }

    private static UserService       userService;
    private static RoleService       roleService;
    private static AddressService addressService;
    private static TruckService     truckService;

    static {
        userService    = new UserServiceImpl();
        roleService    = new RoleServiceImpl();
        addressService = new AddressServiceImpl();
        truckService   = new TruckServiceImpl();
    }

    public static UserService    getUserService()       { return userService;    }
    public static RoleService    getRoleService()       { return roleService;    }
    public static TruckService   getTruckService()      { return truckService;   }
    public static AddressService getAddressService()    { return addressService; }
}

