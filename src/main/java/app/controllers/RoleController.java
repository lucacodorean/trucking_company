package app.controllers;

import app.models.Role;
import java.util.List;
import app.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import app.single_point_access.ServiceSinglePointAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    
    private RoleService roleService = ServiceSinglePointAccess.getRoleService();

    @GetMapping("/")
    public ResponseEntity<List<Role>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> show(@PathVariable Integer roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findById(roleId));
    }
}
