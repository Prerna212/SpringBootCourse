package com.github.prerna.Controllers;

import com.github.prerna.entities.Order;
import com.github.prerna.entities.User;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.repositories.OrderRepository;
import com.github.prerna.repositories.UserRepository;
import com.github.prerna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent())
            throw new UserNotFoundException("User not found");

        return user.get().getOrders();
    }

    @PostMapping("/{userId}/orders")
    public Order createOrder(@PathVariable Long userId, @RequestBody Order order) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        User user = optionalUser.get();
        order.setUser(user);
        return orderRepository.save(order);

    }

    @GetMapping("/{userId}/orders/{orderId}")
    public Order getOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        User user = optionalUser.get();
        List<Order> order = user.getOrders();

        Order result = null;
        for(Order x : order){
            if(x.getOrderId() != orderId)
                System.out.println("order doesn't exist");
            else
                result = x;
        }
        return result;
    }
}
