package com.revature.bankingapp.Transaction;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private int accountId;
    private LocalDateTime transactionTime;
    private double balanceChange;

    public Transaction() {}

    public Transaction(int transactionId, int accountId, LocalDateTime transactionTime, double balanceChange) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionTime = transactionTime;
        this.balanceChange = balanceChange;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(double balanceChange) {
        this.balanceChange = balanceChange;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", transactionTime=" + transactionTime +
                ", balanceChange=" + balanceChange +
                '}';
    }
}
