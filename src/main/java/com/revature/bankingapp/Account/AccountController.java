package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public class AccountController implements Controller {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("/accounts", this::getAllAccounts);
        app.post("/accounts", this::postNewAccount);
    }

    private void getAllAccounts(Context ctx) {
        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
    }

    private void postNewAccount(Context ctx) {
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.header("userId")));
        System.out.println(userId);
        Account newAccount = ctx.bodyAsClass(Account.class);
        if(userId != newAccount.getOwnerId()) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result("You do not have permission to perform this action.");
            return;
        }

        ctx.json(accountService.create(newAccount));
        ctx.status(HttpStatus.CREATED);
    }
}
