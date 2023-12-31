package app.controllers;

import app.dto.RoleStoreRequest;
import app.dto.RoleUpdateRequest;
import app.dto.UserDTO;
import app.models.Role;
import app.models.User;
import app.services.RoleService;
import app.services.UserService;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.convention.MatchingStrategies;
import app.single_point_access.ServiceSinglePointAccess;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    
    private RoleService roleService = ServiceSinglePointAccess.getRoleService();
    private UserService userService = ServiceSinglePointAccess.getUserService();
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/")
    public ResponseEntity<List<Role>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }

    @GetMapping("/{roleKey}")
    public ResponseEntity<Role> show(@PathVariable String roleKey) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findByKey(roleKey));
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody RoleStoreRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
            roleService.save(convertDtoToEntity(request))
        );
    }

    @PatchMapping("/{roleKey}")
    public ResponseEntity<Role> update(
        @PathVariable String roleKey, 
        @RequestBody RoleUpdateRequest request) {
        
            Role role = roleService.findByKey(roleKey);
            if(role == null) return null;

            if(request.getSalary() != null) role.setSalary(request.getSalary());
            if(request.getPosition() != null) role.setPosition(request.getPosition());

            Role updatedRole = roleService.update(role);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRole);
    }

    @DeleteMapping("/{roleKey}")
    public boolean delete(@PathVariable String roleKey) {
        return roleService.delete(roleService.findByKey(roleKey));
    }

    @GetMapping("/{roleKey}/getUsers")
    public ResponseEntity<ArrayList<UserDTO>> getAllUsers(@PathVariable String roleKey) {
        List<User> users = userService.findAll();
        ArrayList<UserDTO> result = new ArrayList<UserDTO>();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for(User currentUser : users) {
            if(currentUser.getRole().getModelKey().compareTo(roleKey) == 0) {
                result.add(modelMapper.map(currentUser, UserDTO.class));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private Role convertDtoToEntity(RoleStoreRequest request) {
        if(request == null) return null;
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(request, Role.class);
    }
}
