package com.revature.bankingapp.util.auth;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserService;

import javax.naming.AuthenticationException;

public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmailAndPassword(email, password);
        if(user == null) throw new AuthenticationException("Invalid user credentials, please try again.");
        return user;
    }
}
