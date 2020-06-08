package com.github.prerna.Controllers;

import com.github.prerna.entities.Order;
import com.github.prerna.entities.User;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.repositories.UserRepository;
import com.github.prerna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas/users")
@Validated
public class UserHateoasController {
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    UserHateoasController(UserService userService){
        this.userService = userService;
    }

    //@Autowired
    UserHateoasController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping
    public CollectionModel<User> getAllUsers() throws UserNotFoundException {
        List<User> allUsers = userService.getAllUsers();
        for(User x : allUsers){
            //selfLink
            long userId = x.getId();
            Link selfLink = linkTo(this.getClass()).slash(userId).withSelfRel();
            x.add(selfLink);

            //Relationship link with getAllOrders
            CollectionModel<Order> orders = methodOn(OrderHateoasController.class).getAllOrders(userId);
            Link orderLink = linkTo(orders).withRel("all-orders");
            x.add(orderLink);
        }
        //self Link for getAllUsers
        Link selfLinkGetAllUsers = linkTo(this.getClass()).withSelfRel();

        CollectionModel<User> finalModel = new CollectionModel<>(allUsers, selfLinkGetAllUsers);
        return finalModel;
    }

    @GetMapping("/{Id}")
    public EntityModel<User> getUserById(@PathVariable("Id") @Min(1) long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();
            Long userId = user.getId();
            Link selfLink = linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);
            EntityModel<User> finalModel = new EntityModel<>(user);
            return finalModel;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }


}
