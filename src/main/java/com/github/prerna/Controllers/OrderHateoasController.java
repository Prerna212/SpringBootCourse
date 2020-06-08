package com.github.prerna.Controllers;

import com.github.prerna.entities.Order;
import com.github.prerna.entities.User;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.repositories.UserRepository;
import com.github.prerna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/orders")
public class OrderHateoasController  {
    private UserRepository userRepository;
    private UserService userService;

    //@Autowired
    OrderHateoasController(UserService userService){
        this.userService = userService;
    }
    @Autowired
    OrderHateoasController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent())
            throw new UserNotFoundException("User not found");

        List<Order> allOrders = user.get().getOrders();
        CollectionModel<Order> result = new CollectionModel<>(allOrders);

        return result;
    }

}
