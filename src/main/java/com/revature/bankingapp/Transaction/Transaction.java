package com.revature.bankingapp.Transaction;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private int accountId;
    private LocalDateTime transactionTime;
    private TransactionType type;
    private double balanceChange;

    public enum TransactionType {
        DEPOSIT, WITHDRAW
    }
    public Transaction() {}

    public Transaction(int transactionId, int accountId, LocalDateTime transactionTime, TransactionType type, double balanceChange) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionTime = transactionTime;
        this.type = type;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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
                ", type=" + type +
                ", balanceChange=" + balanceChange +
                '}';
    }
}
