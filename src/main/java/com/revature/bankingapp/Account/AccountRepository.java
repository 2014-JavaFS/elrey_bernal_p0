package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.*;
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
    public Account create(Account newAccount) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "insert into accounts (account_id, owner_id, balance, account_type) values (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, newAccount.getAccountId());
            preparedStatement.setInt(2, newAccount.getOwnerId());
            preparedStatement.setDouble(3, newAccount.getBalance());
            preparedStatement.setString(4, newAccount.getAccountType().toString());

            int checkInsert = preparedStatement.executeUpdate();
            System.out.println("Inserting information....");
            if(checkInsert == 0) {
                throw new RuntimeException("User was not inserted into the database");
            }

            return newAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

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
