package com.github.prerna.Controllers;

import com.github.prerna.entities.User;
import com.github.prerna.exception.UserAlreadyExistsException;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{Id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/users/{Id}")
    public Optional<User> getUserById(@PathVariable("Id") long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PutMapping("/users/{Id}")
    public User updateUserById(@PathVariable("Id") long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
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

