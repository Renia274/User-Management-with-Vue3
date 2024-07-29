package com.example.vue.controllers;

import com.example.vue.FillingAndValidationException;
import com.example.vue.service.UserService;
import com.example.vue.data.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v")
// @CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    // Automatically injects an instance of UserService into this controller.
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        try {
            Users savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (FillingAndValidationException e) {
            // If there is a validation exception, returns HTTP 400 Bad Request with the exception message.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Handles GET requests to "/api/v/users" to retrieve a paginated list of users.
    @GetMapping("/users")
    public ResponseEntity<Page<Users>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Users> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        // Calls the service to get a user by ID.
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build(); // Returns HTTP 204 No Content to indicate successful deletion.
    }
}
