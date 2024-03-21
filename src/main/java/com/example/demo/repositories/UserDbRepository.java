package com.example.demo.repositories;

import com.example.demo.entities.User;

import java.sql.*;
import java.util.*;

public class UserDbRepository {
    private String url;
    private String dbUser;
    private String dbPassword;

    /***
     * Constructor for the UserDbRepository.
     * @param url the url for the database
     * @param user the user of the database
     * @param password the password for the database
     */
    public UserDbRepository(String url, String user, String password) {
        this.url = url;
        this.dbUser = user;
        this.dbPassword = password;

    }

    /***
     * Finds the user with a given id in the database.
     * @param idUser -the id of the user to be returned
     *           id must not be null
     * @return null- if the user with the given id does not exist; the searched user-otherwise
     */
    public User findOne(Long idUser) {
        String sql = "select * from users where id = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, idUser);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    String username = resultSet.getString("username");

                    return new User(idUser, firstName, lastName, username, age, password, salt);
                }
            }
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return null;
    }

    /***
     * Finds all the users in the database.
     * @return a list with all the users saved in the database
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Integer age = resultSet.getInt("age");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                String username = resultSet.getString("username");

                User user = new User(id, firstName, lastName, username, age, password, salt);
                users.add(user);
            }

            return users;
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return users;
    }

    /***
     * Saves a user in the database.
     * @param userToSave the user to be saved
     *         user must be not null
     * @return the user- if it was successfully saved
     */
    public User save(User userToSave) {
        String sql = "insert into users (first_name, last_name, age, password, salt, username) values (?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, userToSave.getFirstName());
            ps.setString(2, userToSave.getLastName());
            ps.setInt(3, userToSave.getAge());
            ps.setString(4, userToSave.getPassword());
            ps.setString(5, userToSave.getSalt());
            ps.setString(6, userToSave.getUsername());
            ps.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return userToSave;
    }

    /***
     * Deletes a user from the database.
     * @param idUser the id of the user to be deleted
     *      id must be not null
     * @return the user-if it was successfully deleted; null-otherwise
     */
    public User delete(Long idUser) {
        String sql = "delete from users where id = ?";
        User userFromDb = findOne(idUser);

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, idUser);
            preparedStatement.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return userFromDb;
    }

    /***
     * Updates a user in the database.
     * @param givenUser the updated user;
     *        givenUser must not be null
     * @return the old user-if it was successfully updated; null-otherwise
     */
    public User update(User givenUser) {
        String sql = "update users set first_name = ?, last_name = ?, age = ?, password =?, salt=?, username=? where id = ?";
        User userFromDb = findOne(givenUser.getId());

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, givenUser.getFirstName());
            ps.setString(2, givenUser.getLastName());
            ps.setInt(3, givenUser.getAge());
            ps.setString(4, givenUser.getPassword());
            ps.setString(5, givenUser.getSalt());
            ps.setString(6, givenUser.getUsername());
            ps.setLong(7, givenUser.getId());
            ps.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return userFromDb;
    }

    /***
     * Finds a user by his username.
     * @param username the username of the user we are searching for
     * @return the user-if it exists; null-otherwise
     */
    public User find(String username) {
        String sql = "select * from users where username =?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    String username2 = resultSet.getString("username");

                    return new User(id, firstName, lastName, username2, age, password, salt);
                }
            }
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return null;
    }

    /***
     * Finds the users who have a given name.
     * @param name the name of the users we are searching for
     * @return a list containing the users having the given name
     */
    public List<User> findUsersByName(String name) {
        String[] nameSeparated = name.split(" ");

        if (nameSeparated.length == 2) {
            return getUsersByFullName(nameSeparated);
        }

        if (nameSeparated.length == 1) {
            return getUsersByFirstOrLastName(nameSeparated[0]);
        }

        return new ArrayList<>();
    }

    private List<User> getUsersByFullName(String[] fullName){
        String sql = "select * from users where first_name = ? and last_name = ? union select * from users where first_name = ? or last_name = ? ";
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, fullName[0]);
            ps.setString(2, fullName[1]);
            ps.setString(3, fullName[1]);
            ps.setString(4, fullName[0]);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    String username2 = resultSet.getString("username");

                    User user = new User(id, firstName, lastName, username2, age, password, salt);
                    users.add(user);
                }
            }
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return users;
    }

    private List<User> getUsersByFirstOrLastName(String name) {
        String sql = "select * from users where first_name = ? or last_name = ? ";
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    String username2 = resultSet.getString("username");

                    User user = new User(id, firstName, lastName, username2, age, password, salt);
                    users.add(user);
                }
            }
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }

        return users;
    }
}