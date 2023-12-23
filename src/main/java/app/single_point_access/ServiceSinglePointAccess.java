package app.single_point_access;

import app.services.implementation.ActivityLogServiceImpl;
import app.services.implementation.ShipmentServiceImpl;
import app.services.implementation.AddressServiceImpl;
import app.services.implementation.TrailerServiceImpl;
import app.services.implementation.TruckServiceImpl;
import app.services.implementation.UserServiceImpl;
import app.services.implementation.RoleServiceImpl;

import app.services.ActivityLogService;
import app.services.ShipmentService;
import app.services.AddressService;
import app.services.TrailerService;
import app.services.TruckService;
import app.services.UserService;
import app.services.RoleService;

public class ServiceSinglePointAccess {

    private ServiceSinglePointAccess() { }

    private static UserService          userService;
    private static RoleService          roleService;
    private static TruckService         truckService;
    private static AddressService       addressService;
    private static TrailerService       trailerService;
    private static ShipmentService      shipmentService;
    private static ActivityLogService   activityLogService;

    static {
        userService         = new UserServiceImpl();
        roleService         = new RoleServiceImpl();
        truckService        = new TruckServiceImpl();
        addressService      = new AddressServiceImpl();
        trailerService      = new TrailerServiceImpl();
        shipmentService     = new ShipmentServiceImpl();
        activityLogService  = new ActivityLogServiceImpl();
    }

    public static UserService        getUserService()           { return userService;    }
    public static RoleService        getRoleService()           { return roleService;    }
    public static TruckService       getTruckService()          { return truckService;   }
    public static AddressService     getAddressService()        { return addressService; }
    public static TrailerService     getTrailerService()        { return trailerService; }
    public static ShipmentService    getshShipmentService()     { return shipmentService; }
    public static ActivityLogService getaActivityLogService()   { return activityLogService; }
}

