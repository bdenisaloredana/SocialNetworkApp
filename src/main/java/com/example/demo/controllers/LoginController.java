package com.example.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.example.demo.HelloApplication;
import com.example.demo.entities.User;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.MessageService;
import com.example.demo.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button signupButton;
    public Button newLoginButton;
    private UserService userService;
    private MessageService messageService;
    private FriendshipService friendshipService;

    /***
     * Sets up the LoginController.
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
     * Handles the login.
     */
    public void login() {
        String username = usernameField.getText();
        User user = userService.findUserByUsername(usernameField.getText());
        if (username == null || user == null) {
            MessageAlert.showErrorMessage(null, "Wrong username!");
            return;
        }

        String salt = user.getSalt();
        String checkPassword = userService.createSecurePassword(passwordField.getText(), salt);
        try {
            if (Objects.equals(checkPassword, user.getPassword())) {
                URL url = HelloApplication.class.getResource("views/UserView.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                AnchorPane root = loader.load();

                UserController controller = loader.getController();
                controller.initController(userService, friendshipService, messageService, user);

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600.0, 400.0));
                stage.setTitle(user.getFirstName() + " " + user.getLastName());
                stage.show();
                Stage thisStage = (Stage) loginButton.getScene().getWindow();
                thisStage.close();
            } else {
                MessageAlert.showErrorMessage(null, "Wrong password!");
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    /***
     * Handles the opening of the sign-up form.
     */
    public void signup() {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        stage.close();

        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/SignupView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            SignupController signupController = fxmlLoader.getController();
            signupController.initController(userService, messageService, friendshipService);
            newStage.setScene(scene);
            newStage.setTitle("Sign up");
            newStage.show();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    /***
     * Handles a new login by opening another window.
     */
    public void newLogin() {
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
