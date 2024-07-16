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
    public User create(User newUser) throws InvalidInputException {
        validateUser(newUser);
        return userRepository.create(newUser);
    }

    @Override
    public User findById(int number) {
        return userRepository.findById(number);
    }

    @Override
    public boolean update(User updatedObject) throws InvalidInputException{
        validateUser(updatedObject);
        return userRepository.update(updatedObject);
    }

    @Override
    public boolean delete(int number) {
        return userRepository.delete(number);
    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    public void validateUser(User user) throws InvalidInputException {
        if (user == null) {
            throw new InvalidInputException("User is null as it has not been instantiated in memory");
        }

        if (user.getUserId() <= 0) {
            throw new InvalidInputException("User ID needs to be greater than 0.");
        }

        if(!isNotEmpty.test(user.getFirstName()) || !isNotEmpty.test(user.getLastName())) {
            throw new InvalidInputException("Values are empty.");
        }

        if(!isNotEmpty.test(user.getEmail()) || !userRepository.checkEmailAvailability(user)) {
            throw new InvalidInputException("Email is empty or already taken by another user.");
        }

        if(!isNotEmpty.test(user.getPassword()) || user.getPassword().length() < 7) {
            throw new InvalidInputException("Password is empty or less than 7 characters in length");
        }
    }

}
