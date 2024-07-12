package com.revature.bankingapp.User;

import com.revature.bankingapp.util.ScannerValidator;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class UserController implements Controller {

    private UserService userService;
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    public UserController(UserService userService) {
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

    public void addUser() {
        User userToAdd;



    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("/users", this::getAllUsers);
        app.post("/users", this::postNewUser);
        app.get("/users/{userId}", this::getFlightById); //Path Parameter
        app.put("/users", this::putUpdateUser);

    }

    public void getAllUsers(Context ctx) {
        List<User> user = userService.findAll();
        ctx.json(user);
    }

    public void postNewUser(Context ctx){
        User user = ctx.bodyAsClass(User.class); //request body & mapping from JSON to Java Object

        ctx.json(userService.create(user)); //Respond with the created flight;
        ctx.status(HttpStatus.CREATED); //Responded with a successful status code
    }

    private void getFlightById(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        User foundUser = userService.findById(userId);

        ctx.json(foundUser);
    }

    private void putUpdateUser(Context ctx) {
        User updatedUser = ctx.bodyAsClass(User.class);


        ctx.json(userService.update(updatedUser));
        ctx.status(HttpStatus.ACCEPTED);
    }

}