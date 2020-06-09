package com.github.prerna.config;

import com.github.prerna.dtos.UserMsDto;
import com.github.prerna.entities.User;
import com.github.prerna.mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
