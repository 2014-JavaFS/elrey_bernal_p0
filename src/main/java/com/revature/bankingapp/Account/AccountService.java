package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
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
    public Account create(Account newObject) {
        return null;
    }

    @Override
    public Account findById(int number) {
        return null;
    }

    @Override
    public boolean update(Account updatedObject) {
        return false;
    }

    @Override
    public boolean delete(int number) {
        return false;
    }
}
