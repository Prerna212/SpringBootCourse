package com.github.prerna.Controllers;

import com.github.prerna.dtos.UserDtoV1;
import com.github.prerna.dtos.UserDtoV2;
import com.github.prerna.entities.User;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/versioning/headers/users")
public class UserCustomHeaderVersioningController {

        @Autowired
        private UserService userService;

        @Autowired
        private ModelMapper modelMapper;

        @GetMapping(value = "/{Id}", headers = "API-VERSION=9")
        public UserDtoV1 getUserById(@PathVariable("Id") @Min(1) long id) throws UserNotFoundException {
            Optional<User> userOptional = userService.getUserById(id);

            if(!userOptional.isPresent()){
                throw new UserNotFoundException("User doesn't exist");
            }

            User user = userOptional.get();

            UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);

            return userDtoV1;
        }

        @GetMapping(value = "/{Id}", headers = "API-VERSION=8")
        public UserDtoV2 getUserById2(@PathVariable("Id") @Min(1) long id) throws UserNotFoundException {
            Optional<User> userOptional = userService.getUserById(id);

            if(!userOptional.isPresent()){
                throw new UserNotFoundException("User doesn't exist");
            }

            User user = userOptional.get();

            UserDtoV2 userDtoV2 = modelMapper.map(user, UserDtoV2.class);

            return userDtoV2;
        }

    }
