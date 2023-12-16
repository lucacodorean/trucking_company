package app.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.models.User;
import app.services.UserService;
import app.single_point_access.ServiceSinglePointAccess;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService = ServiceSinglePointAccess.getUserService();

    @GetMapping("/")
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> show(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId));
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(request));
    }
}
