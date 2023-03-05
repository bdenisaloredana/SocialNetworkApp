package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.controllers.LoginController;
import com.example.demo.repositories.FriendshipDbRepository;
import com.example.demo.repositories.MessageDbRepository;
import com.example.demo.repositories.UserDbRepository;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.MessageService;
import com.example.demo.services.UserService;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserDbRepository userRepository = new UserDbRepository("jdbc:postgresql://localhost:5432/socialNetwork", "postgres", "postgres");
        FriendshipDbRepository friendshipRepository = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/socialNetwork", "postgres", "postgres");
        MessageDbRepository messageRepository = new MessageDbRepository("jdbc:postgresql://localhost:5432/socialNetwork", "postgres", "postgres");
        UserService userService = new UserService(userRepository, friendshipRepository);
        MessageService messageService= new MessageService(messageRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository, userRepository);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(userService, messageService, friendshipService);
        stage.setScene(scene);
        stage.setTitle("Log in");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}