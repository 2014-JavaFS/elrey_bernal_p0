package com.revature.bankingapp.User;

import com.revature.bankingapp.util.ScannerValidator;
import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class UserController {

    public Scanner scanner;
    private final UserService userService;

    ScannerValidator anyInt = (scanner, errorMessage) -> {
        if (!scanner.hasNextInt()) {
            System.out.println(errorMessage);
            scanner.next();
            return false;
        }
        return true;
    };

    ScannerValidator anyShort = (scanner, errorMessage) -> {
        if (!scanner.hasNextShort()) {
            System.out.println(errorMessage);
            scanner.next();
            return false;
        }
        return true;
    };

    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    public UserController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void getUserInfo() {
        List<User> users = userService.findAll();
        if(users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i) != null) {
                    System.out.println(users.get(i));
                }
            }
        }
    }
}
