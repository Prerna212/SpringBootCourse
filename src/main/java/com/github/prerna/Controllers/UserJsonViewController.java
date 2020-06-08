package com.github.prerna.Controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.prerna.entities.User;
import com.github.prerna.entities.Views;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {

    @Autowired
    private UserService userService;

    @JsonView(Views.External.class)
    @GetMapping("external/{Id}")
    public Optional<User> getUserById(@PathVariable("Id") @Min(1) long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @JsonView(Views.Internal.class)
    @GetMapping("internal/{Id}")
    public Optional<User> getUserById2(@PathVariable("Id") @Min(1) long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }
}
