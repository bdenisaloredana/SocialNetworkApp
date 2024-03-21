package com.example.demo.entities;

import java.util.Objects;

public class User extends Entity<Long> {
    private String firstName;
    private String lastName;
    private String username;
    private int age;
    private String password;
    private String salt;

    /**
     * Constructor for a user.
     * @param id        the id of the user
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param username  the username of the user
     * @param age       the age of the user
     * @param password  the password of the user
     * @param salt      the salt for the password of the user
     */
    public User(Long id, String firstName, String lastName, String username, int age, String password, String salt) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.age = age;
        this.password = password;
        this.salt = salt;

    }

    /**
     * Constructor for a user.
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param username  the username of the user
     * @param age       the age of the user
     * @param password  the password of the user
     * @param salt      the salt for the password of the user
     */
    public User(String firstName, String lastName, String username, Integer age, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.age = age;
        this.password = password;
        this.salt = salt;
    }

    /**
     * Getter for the first name of the user.
     * @return the first name of the user
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Getter for the last name of the user.
     * @return the last name of the user
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Getter for the age of the user.
     * @return the age of the user
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Getter for the password of the user.
     * @return the password of the user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter for the salt of the password.
     * @return the generated salt for the password
     */
    public String getSalt() {
        return this.salt;
    }

    /**
     * Getter for the username of the user.
     * @return the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter for the first name of the user.
     * @param firstName the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for the last name of the user.
     * @param lastName the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for the username of the user.
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the age of the user.
     * @param age1 the age of the user
     */
    public void setAge(int age1) {
        this.age = age1;
    }

    /**
     * Setter for the password of the user.
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for the salt of the password.
     * @param salt the salt of the password
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /***
     * Overrides the equals function.
     * @param obj the object with which we compare
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User user = (User) obj;

        return Objects.equals(getAge(), user.age) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.lastName) &&
                Objects.equals(getId(), user.getId());
    }

    /**
     * Override for the toString method.
     * @return a string containing details about a user
     */
    @Override
    public String toString() {
        return "ID: " + getId() + "; " + "First name: " + firstName + "; " + "Last name: " + lastName + "; " + "Age: " + age;
    }

    /***
     * Override for the hasCode function.
     * @return the hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }
}
