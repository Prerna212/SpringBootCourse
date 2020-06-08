package com.github.prerna.Controllers;

import com.github.prerna.entities.User;
import com.github.prerna.exception.UserAlreadyExistsException;
import com.github.prerna.exception.UserNameNotFoundException;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{Id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/{Id}")
    public Optional<User> getUserById(@PathVariable("Id") @Min(1) long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PutMapping("/{Id}")
    public User updateUserById(@PathVariable("Id") long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{Id}")
    public void deleteUserById(@PathVariable("Id") long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/byusername/{userName}")
    public User getUserByUserName(@PathVariable("userName") String userName) throws UserNameNotFoundException {
        User user = userService.getUserByUserName(userName);
        if (user == null)
            throw new UserNameNotFoundException("UserName "+userName+"  doesn't exist");
        return user;
    }
}

