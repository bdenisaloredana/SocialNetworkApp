package com.example.demo.services;

import com.example.demo.dto.FriendshipDto;
import com.example.demo.entities.Friendship;
import com.example.demo.entities.Status;
import com.example.demo.entities.User;
import com.example.demo.repositories.FriendshipDbRepository;
import com.example.demo.repositories.UserDbRepository;
import com.example.demo.utils.observer.Observable;
import com.example.demo.utils.observer.Observer;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.demo.entities.Status.Pending;

public class FriendshipService implements Observable {
    FriendshipDbRepository friendshipRepository;
    UserDbRepository userRepo;
    private final List<Observer> observers=new ArrayList<>();

    /**
     * Constructor for the FriendshipService.
     * @param friendshipRepository the friendships repository
     * @param userRepo the users repository
     */
    public FriendshipService(FriendshipDbRepository friendshipRepository, UserDbRepository userRepo) {
        this.friendshipRepository = friendshipRepository;
        this.userRepo = userRepo;
    }


    /***
     * Finds the ids of the friends of a given user.
     * @param user the id of the user for whom we search the friends
     * @return a list containing the ids of the friends of the given user
     */
    public List<Long> findFriendsOfUser(User user){
        return friendshipRepository.findFriends(user);
    }

    /**
     * Adds a friendship in the user repository.
     * @param id1 the id of the first user of the friendship
     * @param id2 the id of the second user of the friendship
     */
    public void addFriendship(Long id1, Long id2) throws SQLException {
        User u1 = userRepo.findOne(id1);
        User u2 = userRepo.findOne(id2);
        if(u1 != null)
            if(u2 != null) {
                friendshipRepository.save(new Friendship(u1.getId(), u2.getId(),Pending));
            }
        notifyObservers();
    }

    /**
     * Deletes a friendship from the database.
     *@param idFriendship the id of the friendship
     */
    public void deleteFriendship(Long idFriendship) throws SQLException {
        Friendship friendship = friendshipRepository.findOne(idFriendship);
        if(friendship != null){
            friendshipRepository.delete(friendship.getId());
        }
        notifyObservers();
    }


    /**
     * Sterge toate prieteniile care contin un user dat.
     * @param u user-ul dat
     * @throws InvalidId
     */
    /*public void deleteFriendshipsWithUser(User u) throws InvalidId, SQLException {
        Friendship friendship;
        for(Friendship f: friendshipRepository.findAll())
        {
            if(Objects.equals(f.getIdUser1() , u.getId()) || Objects.equals(f.getIdUser2(), u.getId())) {
                User u1 = userRepo.findOne(f.getIdUser1());
                User u2 = userRepo.findOne(f.getIdUser2());
                deleteFriendship(f.getId(), f.getIdUser1(), f.getIdUser2());
            }

        }
    }
     */

    /**
     * Updates a friendship in the database.
     * @param id the id of the friendship
     * @param status the status of the friendship
     */
    public void updateFriendship(Long id, Status status) throws  SQLException {
        Friendship f = findFriendship(id);
        f.setStatus(status);
        friendshipRepository.update(f);
        notifyObservers();
    }

    /**
     * Getter for all the friendships from the database.
     * @return the friendships stored in the database
     */
    public Iterable<Friendship> findAll(){
        return this.friendshipRepository.findAll();
    }

    /**
     * Getter for a friendship.
     * @param id the id of the friendship
     * @return the friendship with the given id
     */
    public Friendship findFriendship(Long id) throws SQLException {
        return this.friendshipRepository.findOne(id);
    }

    /**
     * Finds the friendships of a user.
     * @param user the user for whom we search the friendships
     * @return a list containing the users friends
     */
    public List<FriendshipDto> findFriendshipsOfUser(User user) throws SQLException {
        List<Friendship> friendships;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<FriendshipDto> friendshipDtos = new ArrayList<>();
        friendships = this.friendshipRepository.find(user.getId());
        for(Friendship friendship: friendships) {
            User user2 = userRepo.findOne(friendship.getIdUser2());
            friendshipDtos.add(new FriendshipDto(friendship.getId(),user2.getId(), user2.getFirstName(), user2.getLastName(), friendship.getStatus().toString(), friendship.getDate().format(format)));
        }

        return friendshipDtos;

    }

    /***
     * Finds the friendships requests sent by a given user.
     * @param user the given user
     * @return a list containing the friendships for which the user has sent a request
     */
    public List<FriendshipDto> findRequestsSentByUser(User user) throws SQLException {
        List<Friendship> friendships;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<FriendshipDto> friendshipDtos = new ArrayList<>();
        friendships = this.friendshipRepository.search(user.getId());
        for(Friendship friendship: friendships) {
            if (friendship.getStatus() == Pending) {
                User user2 = userRepo.findOne(friendship.getIdUser2());
                friendshipDtos.add(new FriendshipDto(friendship.getId(), user2.getId(), user2.getFirstName(), user2.getLastName(), friendship.getStatus().toString(), friendship.getDate().format(format)));
            }
        }

        return friendshipDtos;

    }

    /***
     * Finds the friendships where the sender and receiver of the request have given ids.
     * @param idSender the given id of the sender
     * @param idReceiver the given id of the receiver
     * @return a list containing the friendships where the sender has the id idSender and receiver has the id idReceiver
     */
    public Friendship findFriendshipByReceiverAndSender(Long idSender, Long idReceiver){
       List<Friendship> friendships = friendshipRepository.find(idSender, idReceiver);
       if(friendships.size() !=0)
           return friendships.get(0);
       return null;
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
        observers.stream().forEach(x-> {
            try {
                x.update();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

