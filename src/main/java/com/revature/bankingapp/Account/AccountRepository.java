package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.exceptions.DataNotFoundException;
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

            String sql = "insert into accounts (account_id, owner_id, balance, account_type) values (?,?,?, cast(? as account_enum))";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, newAccount.getAccountId());
            preparedStatement.setInt(2, newAccount.getOwnerId());
            preparedStatement.setDouble(3, newAccount.getBalance());
            preparedStatement.setObject(4, newAccount.getAccountType().toString());

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
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "select * from accounts where account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, number);

            ResultSet rs = preparedStatement.executeQuery();

            if(!rs.next()) {
                throw new DataNotFoundException("No account with that id " + number + " exists in our database");
            }

            return generateAccountFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Account updatedAccount) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "update accounts set owner_id = ?, balance = ? where account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, updatedAccount.getOwnerId());
            preparedStatement.setDouble(2, updatedAccount.getBalance());
            preparedStatement.setInt(3, updatedAccount.getAccountId());

            int checkUpdate = preparedStatement.executeUpdate();
            System.out.println("Updating information....");
            if(checkUpdate == 0) {
                throw new RuntimeException("User record was not updated");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(int number) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "delete from accounts where account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, number);
            boolean checkDelete = preparedStatement.executeUpdate() == 1;

            if(checkDelete) {
                System.out.println("Account information was deleted.");
            } else {
                System.out.println("Account information was deleted.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
