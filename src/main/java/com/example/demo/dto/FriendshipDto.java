package com.example.demo.dto;


public class FriendshipDto {
    private Long idFriendship;
    private Long idFriend;
    private String firstName;
    private String lastName;
    private String status;
    private String requestDate;

    /***
     * Constructor for a friendshipdto.
     * @param id the id of the friendshipdto
     * @param idfriend the id of the friend
     * @param firstName the first name of the friend
     * @param lastName the last name of the friend
     * @param status the status of the friendship
     * @param requestDate the date the request was sent
     */
    public FriendshipDto(Long id, Long idfriend, String firstName, String lastName, String status, String requestDate){
        this.idFriendship = id;
        this.idFriend = idfriend;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.requestDate = requestDate;
    }

    /***
     * Getter for the id of the friendshipdto.
     * @return the id of the friendshipdto
     */
    public Long getIdFriendship() {
        return idFriendship;
    }

    /***
     * Getter for the id of the friend.
     * @return the id of the friend
     */
    public Long getIdFriend(){return this.idFriend;}

    /***
     * Setter for the id of the friend.
     * @param id the id of the friend
     */
    public void setIdFriend(Long id){this.idFriend = id;}

    /***
     * Setter for the id of the friendshipdto.
     * @param id the id of the friendshipdto
     */
    public void setIdFriendship(Long id) {
        this.idFriendship = id;
    }

    /***
     * Getter for the first name of the friend.
     * @return the first name of the friend
     */
    public String getFirstName() {
        return firstName;
    }

    /***
     * Setter for the first name of the friend.
     * @param firstName the first name of the friend
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /***
     * Getter for the last name of the friend.
     * @return the last name of the friend
     */
    public String getLastName() {
        return lastName;
    }

    /***
     * Setter for the last name of the friend.
     * @param lastName the last name of the friend
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /***
     * Getter for the status of the friendshipdto.
     * @return the status of the friendshipdto
     */
    public String getStatus() {
        return status;
    }

    /***
     * Setter for the status of the friendshipdto.
     * @param status the status of the friendshipdto
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /***
     * Getter for the date of the friendship request.
     * @return the date of the friendship request
     */
    public String getRequestDate(){return this.requestDate;}

    /***
     * Setter for the date of the friendship request.
     * @param date the date of the friendship request
     */
    public void setRequestDate(String date){this.requestDate = date;}
}
