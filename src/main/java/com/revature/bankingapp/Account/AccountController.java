package com.revature.bankingapp.Account;

import com.revature.bankingapp.User.User;
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
        app.get("/owned-accounts", this::getAllAccountsByOwnerId);
        app.patch("/accounts/{accountId}", this::patchAccountBalance);

    }

    private void getAllAccounts(Context ctx) {
        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
    }

    private void postNewAccount(Context ctx) throws NullPointerException{
        int userId = loggedInCheck(ctx);
        System.out.println(userId);
        Account newAccount = ctx.bodyAsClass(Account.class);
        newAccount.setOwnerId(userId);


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
        int userId = loggedInCheck(ctx);
        if(userId == -1) return;
        if(!ownerCheck(ctx, userId)) return;

        Account updatedAccount = ctx.bodyAsClass(Account.class);
        updatedAccount.setOwnerId(userId);

        ctx.json(accountService.update(updatedAccount));
        ctx.status(HttpStatus.ACCEPTED);
    }

    private void patchAccountBalance(Context ctx) {
        int userId = loggedInCheck(ctx);
        if(userId == -1) return;

        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        Account foundAccount = accountService.findById(accountId);

        if(!ownerCheck(ctx, foundAccount, userId)) return;
        double amount = amountCheck(ctx);
        if(amount == -1) return;
        String transaction = transactionCheck(ctx);

        if(transaction.equals("deposit")) {
            ctx.json(accountService.deposit(foundAccount, amount));
            ctx.status(HttpStatus.ACCEPTED);
        } else if (transaction.equals("withdraw")) {
            ctx.json(accountService.withdraw(foundAccount, amount));
            ctx.status(HttpStatus.ACCEPTED);
        } else if(transaction.isEmpty()) return;

    }

    private void deleteAccount(Context ctx) {
        int userId = loggedInCheck(ctx);
        if(userId == -1) return;
        if(!ownerCheck(ctx, userId)) return;

        Account deletedAccount = ctx.bodyAsClass(Account.class);
        deletedAccount.setOwnerId(userId);
        if(userId != deletedAccount.getOwnerId()) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result("You are unauthorized to access this account.");
            return;
        }
        ctx.json(accountService.delete(deletedAccount.getAccountId()));
        ctx.status(HttpStatus.ACCEPTED);
    }

    private void getAllAccountsByOwnerId(Context ctx) {
        int userId = loggedInCheck(ctx);
        if(userId == -1) return;
        List<Account> accounts = accountService.findAllByOwnerId(userId);
        ctx.json(accounts);
    }



    /**
     * Checks if a user is logged in before making any changes in the database.
     * @param ctx contains the userId in the header
     * @return returns true if a user is logged in, false if there is not
     */
    private int loggedInCheck(Context ctx) {
        String headerUserId = ctx.header("userId");
        if(headerUserId == null) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result("You are not logged in.");
            return -1;
        }
        return Integer.parseInt(headerUserId);
    }

    /**
     * Checks if the user (userId) trying to access the account is the owner of account by checking if the userId matches the ownerId of the account from the database.
     * @param ctx - contains the account, specifically its account id, which will be used to retrieve the account from the database.
     * @param userId - contains the userId of the user trying to access the account.
     * @return - returns true if the user is the owner of the account, false if they are not.
     */
    private boolean ownerCheck(Context ctx, int userId) {
        Account account = ctx.bodyAsClass(Account.class);
        Account retrievedAccount = accountService.findById(account.getAccountId());
        if(retrievedAccount.getOwnerId() != userId) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result("You are unauthorized to access account with account ID: " + retrievedAccount.getAccountId());
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the user (using userId) trying to access the passed account is the owner of the account by checking if the userId and ownerId from the account matches.
     * @param ctx
     * @param account
     * @param userId
     * @return
     */
    private boolean ownerCheck(Context ctx, Account account, int userId) {
        if(account.getOwnerId() != userId) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result("You are unauthorized to access account with account ID: " + account.getAccountId());
            return false;
        } else {
            return true;
        }
    }

    private double amountCheck(Context ctx) {
        String headerAmount = ctx.header("amount");
        if(headerAmount == null) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("You have not specified the amount to deposit/withdraw.");
            return -1;
        }
        return Double.parseDouble(headerAmount);
    }

    private String transactionCheck(Context ctx) {
        String transaction = ctx.header("transaction");
        if(Objects.equals(transaction, "deposit") || Objects.equals(transaction, "withdraw")) {
            return transaction;
        } else {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("Bad transaction request");
            return "";
        }
    }
}
