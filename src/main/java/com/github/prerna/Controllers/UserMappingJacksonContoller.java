package com.github.prerna.Controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.prerna.entities.User;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/jacksonFilter/users")
@Validated
public class UserMappingJacksonContoller {

    @Autowired
    private UserService userService;

    //getUserById -- fields with hashset
    @GetMapping("/{Id}")
    public MappingJacksonValue getUserById(@PathVariable("Id") @Min(1) long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            Set<String> fields = new HashSet<>();
            fields.add("Id");
            fields.add("userName");
            fields.add("ssn");

            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);
            mapper.setFilters(filterProvider);

            return mapper;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    //getUserById -- fields with @RequestParam
    @GetMapping("params/{Id}")
    public MappingJacksonValue getUserById2(@PathVariable("Id") @Min(1) long id, @RequestParam Set<String> fields) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);
            mapper.setFilters(filterProvider);

            return mapper;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }
}
