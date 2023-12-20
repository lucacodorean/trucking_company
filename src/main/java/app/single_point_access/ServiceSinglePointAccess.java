package app.single_point_access;


import app.services.implementation.AddressServiceImpl;
import app.services.implementation.RoleServiceImpl;
import app.services.implementation.UserServiceImpl;
import app.services.AddressService;
import app.services.RoleService;
import app.services.UserService;

public class ServiceSinglePointAccess {

    private ServiceSinglePointAccess() { }

    private static UserService       userService;
    private static RoleService       roleService;
    private static AddressService addressService;

    static {
        userService    = new UserServiceImpl();
        roleService    = new RoleServiceImpl();
        addressService = new AddressServiceImpl();
    }

    public static UserService    getUserService()       { return userService; }
    public static RoleService    getRoleService()       { return roleService; }
    public static AddressService getAddressService()    { return addressService; }
}

