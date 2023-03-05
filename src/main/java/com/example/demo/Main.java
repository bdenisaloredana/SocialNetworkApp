package com.example.demo;


import com.example.demo.repositories.FriendshipDbRepository;
import com.example.demo.repositories.UserDbRepository;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.UserService;


public class Main {
    public static void main(String[] args) {


        UserDbRepository userRepository = new UserDbRepository("jdbc:postgresql://localhost:5432/socialNetwork", "postgres", "postgres");
        FriendshipDbRepository friendshipRepository = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/socialNetwork", "postgres", "postgres");
        UserService userService = new UserService(userRepository,friendshipRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository, userRepository);





       //userService.addUser("Andreea", "Liescu", 45, "1234", "andreealiescu");
        //System.out.println(friendshipService.findAll());
        //userService.addUser("Ioana", "Sorescu", 44, "1234", "ioanasorescu");
        //userService.addUser("Cosmina", "Salajean", 18, "1234", "cosminasalajean");
        System.out.println(userService.findAll());
        System.out.println(friendshipService.findAll());
        //userService.addUser("Anda","Musadfd", 33, "1234", "anda");
        //userService.addUser("Larisa", "Pop", 33, "1234", "larisapop");
        //friendshipService.addFriendship(12L, 17L);
        //userService.addUser("Maria", "Popescu",20, "1234", "mariapopescu");
        //userService.addUser("Andrei" ,"Ionescu", 30, "1234","andreiionescu");
        //userService.addUser("Cristina", "Craciun", 28, "1234", "cristinapop");
        //friendshipService.addFriendship(12L, 16L);
        //friendshipService.addFriendship(12L, 15L);
        //System.out.println(userRepository.findOne(12L).getFriends());

    }
}