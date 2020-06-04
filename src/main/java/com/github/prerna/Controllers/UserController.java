package com.github.prerna.Controllers;

import com.github.prerna.entities.User;
import com.github.prerna.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/users/{Id}")
    public Optional<User> getUserById(@PathVariable("Id") long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{Id}")
    public User updateUserById(@PathVariable("Id") long id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/users/{Id}")
    public void deleteUserById(@PathVariable("Id") long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/users/byusername/{userName}")
    public User getUserByUserName(@PathVariable("userName") String userName) {
        return userService.getUserByUserName(userName);
    }
}
