package com.revature.bankingapp.User;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Crudable<User> {
    @Override
    public List<User> findAll() {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<User> users = new ArrayList<>();

            String sql = "select * from users";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){

                users.add((generateUserFromResultSet(rs)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User create(User newUser) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<User> users = new ArrayList<>();

            String sql = "insert into users (user_id, first_name, last_name, email, password) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, newUser.getUserId());
            preparedStatement.setString(2, newUser.getFirstName());
            preparedStatement.setString(3, newUser.getLastName());
            preparedStatement.setString(4, newUser.getEmail());
            preparedStatement.setString(5, newUser.getPassword());

            int checkInsert = preparedStatement.executeUpdate();
            System.out.println("Inserting information....");
            if(checkInsert == 0) {
                throw new RuntimeException("User was not inserted into the database");
            }

            return newUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(int number) {
        return null;
    }

    @Override
    public boolean update(User updatedObject) {
        return false;
    }

    @Override
    public boolean delete(int number) {
        return false;
    }

    private User generateUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
