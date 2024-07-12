package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UserService implements Crudable<User>{

    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new DataNotFoundException("No user information available");
        } else {
            return users;
        }
    }

    @Override
    public User create(User newObject) {
        return null;
    }

    @Override
    public User findById(int number) {
        return null;
    }

    @Override
    public boolean update(User updatedObject) {
        return false;
    }

    @Override
    public boolean delete(int number) {
        return false;
    }

    public void validateMinUser(User user) throws InvalidInputException {
        if (user == null) {
            throw new InvalidInputException("User is null as it has not been instantiated in memory");
        }


    }

}
