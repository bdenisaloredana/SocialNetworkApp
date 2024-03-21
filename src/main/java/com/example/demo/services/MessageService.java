package com.example.demo.services;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import com.example.demo.entities.User;
import com.example.demo.repositories.MessageDbRepository;
import com.example.demo.utils.observer.Observable;
import com.example.demo.utils.observer.Observer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class MessageService implements Observable {
    private MessageDbRepository messageRepository;
    private final List<Observer> observers = new ArrayList<>();

    /***
     * Constructor for the MesssageService.
     * @param repo the messages repository
     */
    public MessageService(MessageDbRepository repo) {
        this.messageRepository = repo;
    }

    /***
     * Adds a message to the database.
     * @param idSender the id of the sender of the message
     * @param idReceiver the id of the receiver of the message
     * @param text the text of the message
     */
    public void addMessage(Long idSender, Long idReceiver, String text) throws SQLException {
        Message message = new Message(idSender, idReceiver, text, LocalDateTime.now());
        messageRepository.save(message);
        notifyObservers();
    }

    public void deleteMessage(Long id) {
        messageRepository.delete(id);
    }

    /***
     * Getts the messages between two users in chronological order.
     * @param user the user who opened the conversation
     * @param friend the friend with whom the user is conversing with
     * @return the messages between the user and the friend
     */
    public List<MessageDto> getMessagesBetweenFriends(User user, User friend) {
        List<MessageDto> result = new ArrayList<>();
        List<Message> messages = messageRepository.find(user.getId(), friend.getId());
        for (Message message : messages)
            result.add(new MessageDto(user.getUsername(), message.getMessageText(), message.getDate()));

        messages = messageRepository.find(friend.getId(), user.getId());
        for (Message message : messages)
            result.add(new MessageDto(friend.getUsername(), message.getMessageText(), message.getDate()));

        Comparator<MessageDto> compareByDate = Comparator.comparing(MessageDto::getDate);
        Collections.sort(result, compareByDate);

        return result;
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
