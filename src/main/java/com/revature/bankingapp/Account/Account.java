package com.revature.bankingapp.Account;

public class Account {

    private int accountId;
    private int userId;
    private double balance;
    private String accountType;

    public Account() {}

    public Account(int accountId, int userId, double balance, String accountType) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.accountType = accountType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
