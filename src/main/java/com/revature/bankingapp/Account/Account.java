package com.revature.bankingapp.Account;

public class Account {

    private int accountId;
    private int ownerId;
    private double balance;
    private AccountType accountType;

    public enum AccountType{
        CHECKING, SAVINGS
    }

    public Account() {}

    public Account(int accountId, int ownerId, double balance, AccountType accountType) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
        this.accountType = accountType;
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
        return accountType;
    }

    public void setAccountType(AccountType type) {
        this.accountType = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + ownerId +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
