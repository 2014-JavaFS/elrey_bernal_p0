package com.revature.bankingapp.Account;

public class Account {

    private int accountId;
    private int ownerId;
    private double balance;
    private AccountType type;

    public enum AccountType{
        CHECKING, SAVINGS
    }

    public Account() {}

    public Account(int accountId, int ownerId, double balance, AccountType type) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
        this.type = type;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return type;
    }

    public void setAccountType(AccountType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + ownerId +
                ", balance=" + balance +
                ", accountType='" + type + '\'' +
                '}';
    }
}
