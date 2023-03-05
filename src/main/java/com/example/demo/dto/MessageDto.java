package com.example.demo.dto;

import com.example.demo.controllers.MessageAlert;
import java.time.LocalDateTime;

public class MessageDto{
    private String username;
    private String text;
    private LocalDateTime date;

    /***
     * Constructor for a messagedto.
     * @param username the
     * @param text the text of the message
     * @param date the date the message was sent
     */
    public MessageDto(String username, String text, LocalDateTime date) {
        this.username = username;
        this.text = text;
        this.date = date;
    }

    /***
     * Getter for the username of the user who is sending the message.
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /***
     * Setter for the username of the user who is sending the message.
     * @param username the username of the user who is sending the message
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /***
     * Getter for the text of the message.
     * @return the text of the message
     */
    public String getText() {
        return text;
    }

    /***
     * Setter for the text of the message.
     * @param text the text of the message
     */
    public void setText(String text) {
        this.text = text;
    }

    /***
     * Getter for the date the message was sent.
     * @return the date the message was sent
     */
    public LocalDateTime getDate() {
        return date;
    }

    /***
     * Setter for the date the message was sent.
     * @param date the date the message was sent
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
