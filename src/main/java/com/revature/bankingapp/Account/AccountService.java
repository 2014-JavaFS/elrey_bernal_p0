package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.util.List;

public class AccountService implements Crudable<Account> {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) { this.accountRepository = accountRepository; }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) {
            throw new DataNotFoundException("No account information available");
        } else {
            return accounts;
        }
    }

    @Override
    public Account create(Account newAccount) {
        validateAccount(newAccount);
        return accountRepository.create(newAccount);
    }

    @Override
    public Account findById(int number) {
        return accountRepository.findById(number);
    }

    @Override
    public boolean update(Account updatedAccount) {
        validateAccount(updatedAccount);
        return accountRepository.update(updatedAccount);
    }

    @Override
    public boolean delete(int number) {
        return accountRepository.delete(number);
    }

    public List<Account> findAllByOwnerId(int ownerId) {
        List<Account> accounts = accountRepository.findAllByOwnerId(ownerId);
        if(accounts.isEmpty()) {
            throw new DataNotFoundException("No account information available");
        } else {
            return accounts;
        }
    }

    public Account deposit(Account account, double amount) throws InvalidInputException{
        if(amount < 0) throw new InvalidInputException("Deposit amount cannot be negative.");
        else {
            double updatedAmount = amount + account.getBalance();
            account.setBalance(updatedAmount);
        }

        if(accountRepository.update(account))
        {
            return account;
        } else {
            return null;
        }
    }

    public Account withdraw(Account account, double amount) throws InvalidInputException {
        if(amount < 0) {
            throw new InvalidInputException("Withdraw amount cannot be negative.");
        } else if (account.getBalance() < amount) {
            throw new InvalidInputException("Balance: " + account.getBalance() + " cannot be less than amount: " + amount);
        } else {
            double updatedAmount = account.getBalance() - amount;
            account.setBalance(updatedAmount);
        }

        if(accountRepository.update(account))
        {
            return account;
        } else {
            return null;
        }
    }

    private void validateAccount(Account account) throws InvalidInputException {
        if(account == null) {
            throw new InvalidInputException("Account is null as it has not been instantiated in memory");
        }

        if(account.getOwnerId() <= 0) {
            throw new InvalidInputException("Owner ID needs to be greater than 0.");
        }

        if(account.getBalance() < 0.0) {
            throw new InvalidInputException("Balance cannot be negative.");
        }

    }
}
