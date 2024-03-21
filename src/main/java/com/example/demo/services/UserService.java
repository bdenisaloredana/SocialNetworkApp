package com.example.demo.services;

import com.example.demo.entities.Friendship;
import com.example.demo.entities.User;
import com.example.demo.repositories.FriendshipDbRepository;
import com.example.demo.repositories.UserDbRepository;
import com.example.demo.utils.observer.Observable;
import com.example.demo.utils.observer.Observer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserService implements Observable {
    private UserDbRepository userRepo;
    private FriendshipDbRepository friendshipRepo;
    private final List<Observer> observers = new ArrayList<>();

    /***
     * Constructor for the UserService.
     * @param userRepo       the users repository
     * @param friendshipRepo the friendships repository
     */
    public UserService(UserDbRepository userRepo, FriendshipDbRepository friendshipRepo) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
    }

    /***
     * Adds a user in the database.
     * @param firstName     the first name of the user
     * @param lastName      the last name of the user
     * @param age           the age of the user
     * @param passwordGiven the password of the user
     * @param username      the username of the user
     */
    public void addUser(String firstName, String lastName, Integer age, String passwordGiven, String username) throws SQLException {
        String salt = generateSalt();
        String password = createSecurePassword(passwordGiven, salt);
        User user = new User(firstName, lastName, username, age, password, salt);
        userRepo.save(user);
    }

    /***
     * Finds a user in the database by a given id.
     * @param id the id of the user we are searching for
     * @return the user with the given id-if it exists; null-otherwise
     */
    public User findUser(Long id) throws SQLException {
        return this.userRepo.findOne(id);
    }

    /***
     * Getter for all the users from the database.
     * @return all the users from the database
     */
    public Iterable<User> findAll() {
        return this.userRepo.findAll();
    }

    /***
     * Finds a user by his username.
     * @param username the username of the user we are searching for
     * @return the user-if it exists; null-otherwise
     */
    public User findUserByUsername(String username) {
        return this.userRepo.find(username);
    }

    /***
     * Finds the users who have a given name.
     * @param name the name of the users we are searching for
     * @return a list containing the users having the given name
     */
    public List<User> getUsersByName(String name) {
        return userRepo.findUsersByName(name);
    }

    public List<User> getAll() {
        return this.userRepo.findAll();
    }

    /***
     * Secures the password chosen by the user.
     * @param password the password chosen by user
     * @param salt the generated salt for the password
     * @return secured password
     */
    public String createSecurePassword(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    /***
     * Generates salt for a password.
     * @return the generated salt
     */
    public String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt.toString();
    }

    /***
     * Adds an observer to the list of observers.
     * @param observer the observer to be added to the list
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /***
     * Removes an observer from the list of observers.
     * @param observer the observer to be removed from the list
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /***
     * Notifies the observers when a change occurred.
     */
    @Override
    public void notifyObservers() {
        observers.stream().forEach(observer -> {
            try {
                observer.update();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}