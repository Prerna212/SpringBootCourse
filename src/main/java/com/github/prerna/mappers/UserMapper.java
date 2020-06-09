package com.github.prerna.mappers;

import com.github.prerna.dtos.UserMsDto;
import com.github.prerna.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {


    //User to user dto

    @Mapping(source = "email", target = "emailaddress")
    UserMsDto userToUserDto(User user);

    //List<User> to List<UserMsDto>

    List<UserMsDto> usersToUserDtos(List<User> users);
}
