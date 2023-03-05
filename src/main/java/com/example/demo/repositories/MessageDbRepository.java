package com.example.demo.repositories;


import com.example.demo.entities.Message;
import java.sql.*;
import java.util.*;

public class MessageDbRepository {

    private String url;
    private String user;
    private String password;

    /***
     * Constructor for the MessageDbRepository.
     * @param url the url for the database
     * @param user the user of the database
     * @param password the password for the database
     */
    public MessageDbRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

    }

    /***
     * Finds the message with a given id in the database.
     * @param idMessage -the id of the message to be returned
     *           id must not be null
     * @return null- if the message with the given id does not exist; the searched message-otherwise
     */
    public Message findOne(Long idMessage) throws SQLException {
        String sql = "select * from messages where id = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,idMessage);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Long idM = resultSet.getLong("id");
                    Long idSender = resultSet.getLong("id_sender");
                    Long idReceiver = resultSet.getLong("id_receiver");
                    Timestamp date = resultSet.getTimestamp("date");
                    String message = resultSet.getString(resultSet.getString("message"));

                    return new Message(idM, idSender, idReceiver, message, date.toLocalDateTime());
                }
            }
        }catch(SQLException sq){
            System.out.println(sq.getMessage());
        }
        return null;
    }

    /***
     * Finds all the messages in the database.
     * @return a list with all the messages saved in the database
     */
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long idM = resultSet.getLong("id");
                Long idSender = resultSet.getLong("id_sender");
                Long idReceiver = resultSet.getLong("id_receiver");
                Timestamp date = resultSet.getTimestamp("date");
                String message = resultSet.getString(resultSet.getString("message"));

                Message message1 = new Message(idM, idSender, idReceiver, message, date.toLocalDateTime());
                messages.add(message1);
            }
            return messages;
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return messages;
    }

    /***
     * Saves a message in the database.
     * @param message the message to be saved
     *         message must be not null
     * @return the message- if it was successfully saved
     */
    public Message save(Message message) {
        String sql = "insert into messages (id_sender, id_receiver, message) values (?,?,?)";


        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setLong(1, message.getIdSender());
                ps.setLong(2, message.getIdReceiver());
                ps.setString(3, message.getMessageText());

            ps.executeUpdate();

        }catch (SQLException sq){
            System.out.println(sq.getMessage());
        }
        return message;
    }

    /***
     * Deletes a message from the database.
     * @param id the id of the message to be deleted
     *      id must be not null
     * @return the message-if it was successfully deleted; null-otherwise
     */
    public Message delete(Long id) throws SQLException {
        String sql = "delete from messages where id = ?";
        Message message = findOne(id);
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, id);
            ps.executeUpdate();

        }catch (SQLException sq){
            System.out.println(sq.getMessage());
        }
        return message;
    }

    /***
     * Updates a message in the database.
     * @param message the updated message;
     *        message must not be null
     * @return the old message-if it was successfully updated; null-otherwise
     */
    public Message update(Message message) throws SQLException {
        String sql = "update messages set message = ? where id = ?";
        Message message1 = findOne(message.getId());
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1,message.getMessageText());
            ps.setLong(2,message.getId());

            ps.executeUpdate();
        }catch (SQLException sq){
            System.out.println(sq.getMessage());
        }
        return message1;
    }

    /**
     * Finds the messages in which user1 is the sender and user2 the receiver.
     * @param idUser1 the id of user1
     * @param idUser2 the id of user2
     * @return a list that contains the messages in which user1 is the sender and user2 the receiver
     */
    public List<Message> find(Long idUser1, Long idUser2){
        List<Message> messages = new ArrayList<>();
        String sql = "select * from messages where id_sender = ? and id_receiver = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, idUser1);
            ps.setLong(2, idUser2);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long idM = resultSet.getLong("id");
                    Long idSender = resultSet.getLong("id_sender");
                    Long idReceiver = resultSet.getLong("id_receiver");
                    Timestamp date = resultSet.getTimestamp("date");
                    String message = resultSet.getString("message");

                    Message message1 = new Message(idM, idSender, idReceiver, message, date.toLocalDateTime());
                    messages.add(message1);
                }
            }

            return messages;

        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return messages;
    }
}
