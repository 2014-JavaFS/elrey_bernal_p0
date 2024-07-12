package com.revature.bankingapp;

import com.revature.bankingapp.User.UserController;
import com.revature.bankingapp.User.UserRepository;
import com.revature.bankingapp.User.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Banking System is up and running.....");

        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(scanner, userService);

        do {
            System.out.println("Welcome to our Bank!");
            System.out.println("0. Quit");
            System.out.println("1. User Information");
            System.out.println("Enter your numeric choice from above: ");

            if(!scanner.hasNextInt()) {
                System.out.println("Invalid Input, Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Thank you for using our Banking services.");
                    break;
                case 1:
                    System.out.println("Viewing user information...");
                    userController.getUserInfo();
                    break;
                default:
                    System.out.println("Invalid Input, please enter a valid number.");
            }
        } while (choice != 0);



    }
}