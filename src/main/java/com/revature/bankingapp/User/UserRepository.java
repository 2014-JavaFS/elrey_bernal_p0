package com.revature.bankingapp.User;

import com.revature.bankingapp.util.ConnectionFactory;
import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.interfaces.Crudable;

import java.sql.*;
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

                users.add(generateUserFromResultSet(rs));
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

            String sql = "insert into users (first_name, last_name, email, password) values (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setString(1, newUser.getFirstName());
            preparedStatement.setString(2, newUser.getLastName());
            preparedStatement.setString(3, newUser.getEmail());
            preparedStatement.setString(4, newUser.getPassword());

            int checkInsert = preparedStatement.executeUpdate();
            System.out.println("Inserting information....");
            if(checkInsert == 0) {
                throw new RuntimeException("User was not inserted into the database");
            }

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return generateUserFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(int number) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {

            String sql = "select * from users where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, number);

            ResultSet rs = preparedStatement.executeQuery();

            if(!rs.next()) {
                throw new DataNotFoundException("No user with that id " + number + " exists in our database.");
            }

            return generateUserFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByEmailAndPassword(String email, String password) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from users where email = ? and password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new DataNotFoundException("No user with that info found");
            }

            User user = new User();

            user.setUserId(resultSet.getInt("user_id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));

            return user;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(User updatedUser) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "update users set first_name = ?, last_name = ?,  email = ?, password = ? where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, updatedUser.getFirstName());
            preparedStatement.setString(2, updatedUser.getLastName());
            preparedStatement.setString(3, updatedUser.getEmail());
            preparedStatement.setString(4, updatedUser.getPassword());

            preparedStatement.setInt(5, updatedUser.getUserId());

            int checkUpdate = preparedStatement.executeUpdate();
            System.out.println("Updating information....");
            if (checkUpdate == 0) {
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
            String sql = "delete from users where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, number);
            boolean checkDelete = preparedStatement.executeUpdate() == 1;
            if(checkDelete){
                System.out.println("User information was deleted.");
            } else{
                System.out.println("User information was deleted.");
            }
            return checkDelete;

        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks the database whether the email is already taken by a different user.
        * @param user is a User object containing the user information
        * @return returns true if it is available, false if already taken.
     */
    public boolean checkEmailAvailability(User user) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<User> users = new ArrayList<>();

            String sql = "select * from users where email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, user.getEmail());

            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) { // if email is not used
                return !rs.next();
            }
            else {
                User retrievedUser = generateUserFromResultSet(rs);
                return retrievedUser.getUserId() == user.getUserId(); //makes sure only one user is using an email
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
