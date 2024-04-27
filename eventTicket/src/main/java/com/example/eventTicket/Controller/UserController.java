package com.example.eventTicket.Controller;

import com.example.eventTicket.Entitiy.User;
import com.example.eventTicket.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Post user",
            description = "Post new user")
    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @Operation(summary = "Get all users",
            description = "Get all users")
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.fetchAllUsers();
    }

    @Operation(summary = "Get by Id",
            description = "Get user by their Id")
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String id){
        return userService.getUserById(id);
    }

    @Operation(summary = "Get by name",
            description = "Get user by first name")
    @GetMapping("/users/byName/{firstName}")
    public User getUserByName(@PathVariable("firstName") String firstName) {
        return userService.getUserByName(firstName);
    }

    @Operation(summary = "Get by email",
            description = "Get user by email")
    @GetMapping("/users/byEmail/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @Operation(summary = "Update user",
            description = "Update an existing user.")
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @Operation(summary = "Delete user",
            description = "Delete user by id")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") String id){
        return userService.deleteUser(id);
    }
}
