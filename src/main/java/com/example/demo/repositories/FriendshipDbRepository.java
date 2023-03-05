package com.example.demo.repositories;

import com.example.demo.entities.Friendship;
import com.example.demo.entities.Status;
import com.example.demo.entities.User;

import java.sql.*;
import java.util.*;

public class FriendshipDbRepository{
    private String url;
    private String user;
    private String password;

    /***
     * Constructor for the FriendshipDbRepository.
     * @param url the url for the database
     * @param user the user of the database
     * @param password the password for the database
     */
    public FriendshipDbRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

    }

    /***
     * Finds the friendships with a given id in the database.
     * @param idFriendship -the id of the friendships to be returned
     *           id must not be null
     * @return null- if the friendships with the given id does not exist; the searched friendships-otherwise
     */
    public Friendship findOne(Long idFriendship) throws SQLException {
        String sql = "select * from friendships where id = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,idFriendship);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Long idUser1 = resultSet.getLong("id_user1");
                    Long idUser2 = resultSet.getLong("id_user2");
                    Timestamp date = resultSet.getTimestamp("date");
                    Status status = Status.valueOf(resultSet.getString("status"));

                    return new Friendship(idFriendship, idUser1, idUser2, date.toLocalDateTime(), status);
                }
            }
        }catch(SQLException sq){
            System.out.println(sq.getMessage());
        }
        return null;
    }

    /***
     * Finds all the friendships in the database.
     * @return an iterable with all the friendships saved in the database
     */
    public List<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id_user1 = resultSet.getLong("id_user1");
                Long id_user2 = resultSet.getLong("id_user2");
                Timestamp date = resultSet.getTimestamp("date");
                Status status = Status.valueOf(resultSet.getString("status"));

                Friendship friendship = new Friendship(id, id_user1, id_user2, date.toLocalDateTime(), status);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return friendships;
    }

    /***
     * Saves a friendship in the database.
     * @param friendship the friendship to be saved
     *         friendship must be not null
     * @return the friendship- if it was successfully saved
     */
    public Friendship save(Friendship friendship) throws  SQLException {
        String sql = "insert into friendships (id_user1, id_user2, status) values (?,?,?)";

        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){


                ps.setLong(1, friendship.getIdUser1());
                ps.setLong(2, friendship.getIdUser2());
                ps.setString(3, "Pending");

            ps.executeUpdate();

        }catch (SQLException sq){
            System.out.println(sq.getMessage());
        }
        return friendship;
    }

    /***
     * Deletes a friendship from the database.
     * @param idFriendship the id of the friendship to be deleted
     *      id must be not null
     * @return the friendship-if it was successfully deleted; null-otherwise
     */
    public Friendship delete(Long idFriendship) throws SQLException {
        String sql = "delete from friendships where id = ?";
        Friendship friendship = findOne(idFriendship);
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, idFriendship);
            ps.executeUpdate();

        }catch (SQLException sq){
            System.out.println(sq.getMessage());
        }
        return friendship;
    }

    /***
     * Updates a friendship in the database.
     * @param friendship the updated friendship;
     *        friendship must not be null
     * @return the old friendship-if it was successfully updated; null-otherwise
     */
    public Friendship update(Friendship friendship) throws  SQLException {
        String sql = "update friendships set status = ? where id = ?";
        Friendship friendship1 = findOne(friendship.getId());
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1,friendship.getStatus().toString());
            ps.setLong(2,friendship.getId());


            ps.executeUpdate();
        }catch (SQLException sq){
            System.out.println(sq.getMessage());
        }
        return friendship1;
    }

    /***
     * Finds the friendship where the second user has a given id.
     * @param id the given id of the second user
     * @return a list containing the friendships where the second user has the given id
     */
    public List<Friendship> find(Long id){
        List<Friendship> friendships = new ArrayList<>();
        String sql = "select * from friendships where id_user2 = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long idFriendship = resultSet.getLong("id");
                    Long idFriend = resultSet.getLong("id_user1");
                    String status = resultSet.getString("status");
                    Timestamp date = resultSet.getTimestamp("date");

                    friendships.add(new Friendship(idFriendship, id, idFriend,date.toLocalDateTime(),Status.valueOf(status)));
                }
            }

            return friendships;

        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return friendships;

    }

    /***
     Finds the friendship where the first user has a given id.
     * @param id the given id of the first user
     * @return a list containing the friendships where the first user has the given id
     */
    public List<Friendship> search(Long id){
        List<Friendship> friendships = new ArrayList<>();
        String sql = "select * from friendships where id_user1 = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long idFriendship = resultSet.getLong("id");
                    Long idFriend = resultSet.getLong("id_user2");
                    String status = resultSet.getString("status");
                    Timestamp date = resultSet.getTimestamp("date");

                    friendships.add(new Friendship(idFriendship, id, idFriend,date.toLocalDateTime(),Status.valueOf(status)));
                }
            }

            return friendships;

        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return friendships;

    }

    /***
     * Finds the friendships where the sender and receiver of the request have given ids.
     * @param idSender the given id of the sender
     * @param idReceiver the given id of the receiver
     * @return a list containing the friendships where the sender has the id idSender and receiver has the id idReceiver
     */
    public List<Friendship> find(Long idSender, Long idReceiver){
        List<Friendship> friendships = new ArrayList<>();
        String sql = "select * from friendships where id_user1 = ? and id_user2 = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,idSender);
            ps.setLong(2, idReceiver);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Long id = resultSet.getLong("id");
                    Long idUser1 = resultSet.getLong("id_user1");
                    Long idUser2 = resultSet.getLong("id_user2");
                    Timestamp date = resultSet.getTimestamp("date");
                    Status status = Status.valueOf(resultSet.getString("status"));

                    friendships.add( new Friendship(id, idUser1, idUser2, date.toLocalDateTime(), status));
                }
                return friendships;
            }
        }catch(SQLException sq){
            System.out.println(sq.getMessage());
        }
        return null;
    }

    /***
     * Finds the ids of the friends of a given user.
     * @param user1 the id of the user for whom we search the friends
     * @return a list containing the ids of the friends of the given user
     */
    public List<Long> findFriends(User user1) {
        List<Long> users = new ArrayList<>();
        String sql = "select id_user1 as id from friendships where id_user2 = ? and status=? union select id_user2 as id from friendships where id_user1 = ? and status = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, user1.getId());
            ps.setString(2, "Accepted");
            ps.setLong(3, user1.getId());
            ps.setString(4, "Accepted");

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    users.add(id);
                }
            }

            return users;

        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return users;
    }
}

