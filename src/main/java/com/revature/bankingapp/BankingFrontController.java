package com.revature.bankingapp;

import com.revature.bankingapp.User.UserController;
import com.revature.bankingapp.User.UserRepository;
import com.revature.bankingapp.User.UserService;
import io.javalin.Javalin;

import java.util.Scanner;

public class BankingFrontController {
    public static void main(String[] args) {

        System.out.println("Banking System is up and running.....");


        Javalin app = Javalin.create();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        userController.registerPaths(app);

        app.start(8080);

    }
}