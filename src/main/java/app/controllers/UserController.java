package app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import app.dto.UserStoreRequest;
import app.dto.UserUpdateRequest;
import app.models.User;
import app.services.AddressService;
import app.services.RoleService;
import app.services.UserService;
import app.single_point_access.ServiceSinglePointAccess;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService         = ServiceSinglePointAccess.getUserService();
    private RoleService roleService         = ServiceSinglePointAccess.getRoleService();
    private AddressService addressService   = ServiceSinglePointAccess.getAddressService();
    private ModelMapper modelMapper         = new ModelMapper();

    @GetMapping("/")
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userKey}")
    public ResponseEntity<User> show(@PathVariable String userKey) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByKey(userKey));
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody UserStoreRequest request) {

        boolean ok = request.getPhoneNumber().length() == 10;

        return ok ? ResponseEntity.status(HttpStatus.OK).body(
            userService.save(convertDtoToEntity(request)
        )) : null;
    }

    @PatchMapping("/{userKey}")
    ResponseEntity<User>update(@PathVariable String userKey, @RequestBody UserUpdateRequest request) {
        User user = userService.findByKey(userKey);
        if(user == null) return null;

        if(request.getName()     != null)   user.setName(request.getName());
        if(request.getEmail()    != null)   user.setEmail(request.getEmail());
        if(request.getPassword() != null)   user.setPassword(request.getPassword());
        if(request.getPhoneNumber() != null) {
            boolean ok = request.getPhoneNumber().length() == 10;
            for(Character currentCharacter : request.getPhoneNumber().toCharArray()) {
                if(!Character.isDigit(currentCharacter)) {
                    ok = false;
                    break;
                }
           }

           if(ok) user.setPhoneNumber(request.getPhoneNumber());
        }

        if(request.getAddressKey() != null) 
            user.setAddress(addressService.findByKey(request.getAddressKey()));

        if(request.getRoleKey() != null) 
            user.setAddress(addressService.findByKey(request.getRoleKey()));

        User userUpdated = userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @DeleteMapping("/{userKey}")
    public boolean delete(@PathVariable String userKey) {
        return userService.delete(userService.findByKey(userKey));
    }

    private User convertDtoToEntity(UserStoreRequest request) {
        if(request == null || roleService.findByKey(request.getRoleKey()) == null) return null;
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(request, User.class);
        user.setRole(roleService.findByKey(request.getRoleKey()));
        user.setAddress(addressService.findByKey(request.getAddressKey()));
        return user;
    }
}
