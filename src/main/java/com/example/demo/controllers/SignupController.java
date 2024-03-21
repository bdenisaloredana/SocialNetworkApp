package com.example.demo.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.demo.HelloApplication;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.MessageService;
import com.example.demo.services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField usernameTextField;
    public TextField ageTextField;
    public PasswordField passwordField;
    public Button signupButton;
    public Button loginButton;
    private UserService userService;
    private MessageService messageService;
    private FriendshipService friendshipService;

    /***
     *  Sets up the SignupController.
     * @param userService the users service
     * @param messageService the messages service
     * @param friendshipService the friendships service
     */
    public void initController(UserService userService, MessageService messageService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.messageService = messageService;
    }

    /***
     * Handles the sign-up of a new user.
     */
    public void signup() {
        try {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String username = usernameTextField.getText();
            Integer age = Integer.parseInt(ageTextField.getText());
            String password = passwordField.getText();

            if (userService.findUserByUsername(username) != null) {
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error!", "There already is a user with this username!");
                return;
            }
            userService.addUser(firstName, lastName, age, password, username);
            if (userService.findUserByUsername(username) != null) {
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Successful sign up!");
            }
        } catch (NumberFormatException numberFormatException) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error!", "Age must be an integer!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Handles the return to the login window.
     */
    public void login() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/LoginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            LoginController loginController = fxmlLoader.getController();
            loginController.initController(userService, messageService, friendshipService);
            newStage.setScene(scene);
            newStage.setTitle("Log in");
            newStage.show();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
