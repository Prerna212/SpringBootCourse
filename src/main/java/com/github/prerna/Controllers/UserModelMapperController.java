package com.github.prerna.Controllers;

import com.github.prerna.dtos.UserMmDto;
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
@RequestMapping(value = "modelmappers/users")
public class UserModelMapperController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{Id}")
    public UserMmDto getUserById(@PathVariable("Id") @Min(1) long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()){
            throw new UserNotFoundException("User doesn't exist");
        }

        User user = userOptional.get();

        UserMmDto userMmDto = modelMapper.map(user, UserMmDto.class);

        return userMmDto;
    }
}
