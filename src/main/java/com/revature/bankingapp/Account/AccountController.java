package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AccountController implements Controller {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("/accounts", this::getAllAccounts);
    }

    private void getAllAccounts(Context ctx) {
        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
    }
}
