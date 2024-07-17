package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccountController implements Controller {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("/accounts", this::getAllAccounts);
        app.post("/accounts", this::postNewAccount);
        app.get("/accounts/{accountId}", this::getAccountById);
        app.put("/accounts", this::putUpdateAccount);
        app.delete("/accounts", this::deleteAccount);
    }

    private void getAllAccounts(Context ctx) {
        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
    }

    private void postNewAccount(Context ctx) throws NullPointerException{
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.header("userId")));
        System.out.println(userId);
        Account newAccount = ctx.bodyAsClass(Account.class);
        newAccount.setOwnerId(userId);

        /*if(userId != newAccount.getAccountId()) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result("You do not have permission to perform this action.");
            return;
        }*/

        try{
            ctx.json(accountService.create(newAccount));
            ctx.status(HttpStatus.CREATED);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void getAccountById(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        Account foundAccount = accountService.findById(accountId);
        ctx.json(foundAccount);
    }

    private void putUpdateAccount(Context ctx) {
        Account updatedAccount = ctx.bodyAsClass(Account.class);

        ctx.json(accountService.update(updatedAccount));
        ctx.status(HttpStatus.ACCEPTED);
    }

    private void deleteAccount(Context ctx) {
        Account deletedAccount = ctx.bodyAsClass(Account.class);

        ctx.json(accountService.delete(deletedAccount.getAccountId()));
        ctx.status(HttpStatus.ACCEPTED);
    }
}
