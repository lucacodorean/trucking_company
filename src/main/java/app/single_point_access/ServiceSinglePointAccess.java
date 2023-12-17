package app.single_point_access;


import app.services.RoleService;
import app.services.UserService;
import app.services.implementation.RoleServiceImpl;
import app.services.implementation.UserServiceImpl;

public class ServiceSinglePointAccess {

    private ServiceSinglePointAccess() { }

    private static UserService userService;
    private static RoleService roleService;

    static {
        userService = new UserServiceImpl();
        roleService = new RoleServiceImpl();
    }

    public static UserService getUserService() {
        return userService;
    }
    
    public static RoleService getRoleService() {
        return roleService;
    }
}

