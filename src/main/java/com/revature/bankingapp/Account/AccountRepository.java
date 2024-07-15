package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Crudable<Account> {

    @Override
    public List<Account> findAll() {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<Account> accounts = new ArrayList<>();

            String sql = "select * from accounts";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                accounts.add(generateAccountFromResultSet(rs));
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    private Account generateAccountFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();

        account.setAccountId(rs.getInt("account_id"));
        account.setOwnerId(rs.getInt("owner_id"));
        account.setBalance(rs.getDouble("balance"));
        account.setAccountType(Account.AccountType.valueOf(rs.getString("account_type")));

        return account;
    }
}
