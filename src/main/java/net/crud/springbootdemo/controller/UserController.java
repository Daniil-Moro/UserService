package net.crud.springbootdemo.controller;


import net.crud.springbootdemo.model.Role;
import net.crud.springbootdemo.model.User;
import net.crud.springbootdemo.service.RoleService;
import net.crud.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@authComponent.hasPermission(#username)")
    public ResponseEntity<User> find(@PathVariable String username) throws Exception {
        return new ResponseEntity<>(userService.findByName(username), HttpStatus.OK);
    }

    @PostMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @DeleteMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PreAuthorize("@authComponent.hasPermission(#username)")
    @PatchMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }
}
