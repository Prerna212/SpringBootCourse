package com.github.prerna.Controllers;

import com.github.prerna.dtos.UserMsDto;
import com.github.prerna.entities.User;
import com.github.prerna.mappers.UserMapper;
import com.github.prerna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/mapstruct/users")
public class UserMapStructController {

    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper;

    @GetMapping
    public List<UserMsDto> getAllUserDtos(){
        return userMapper.usersToUserDtos(userRepository.findAll());
    }
}
