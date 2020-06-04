package com.github.prerna.services;

import com.github.prerna.entities.User;
import com.github.prerna.exception.UserAlreadyExistsException;
import com.github.prerna.exception.UserNotFoundException;
import com.github.prerna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        User existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User with " + user.getUserName() + " userName already exists, Please select another userName");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with " + id + " doesn't exist");
        }
        return user;
    }

    public User updateUserById(long id, User user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(" User with Id " + id + " doesn't exist in the repository, provide the correct user details");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with Id " + id + " doesn't exist in the repository, provide the correct user details");
        }

        userRepository.deleteById(id);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
