package com.example.demo.entities;

import java.time.LocalDateTime;

public class Message extends Entity<Long> {
    private Long idSender;
    private Long idReceiver;
    private String messageText;
    private LocalDateTime date;

    /***
     * Constructor for a message.
     * @param idSender the id of the sender
     * @param idReceiver the id of the receiver
     * @param messageText the text of the message
     * @param date the date of the message
     */
    public Message(Long idSender, Long idReceiver, String messageText, LocalDateTime date){
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.messageText = messageText;
        this.date = date;
    }

    /***
     * Constructor for a message.
     * @param id the id of the message
     * @param idSender the id of the sender
     * @param idReceiver the id of the receiver
     * @param messageText the text of the message
     * @param date the date of the message
     */
    public Message(Long id,Long idSender, Long idReceiver, String messageText, LocalDateTime date) {
        this.setId(id);
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.messageText = messageText;
        this.date = date;
    }

    /***
     * Getter for the id of the sender.
     * @return the id of the sender.
     */
    public Long getIdSender() {
        return idSender;
    }

    /***
     * Getter for the id of the receiver.
     * @return the id of the receiver.
     */
    public Long getIdReceiver() {
        return idReceiver;
    }

    /***
     * Getter for the text of the message.
     * @return the text of the message
     */
    public String getMessageText() {
        return messageText;
    }

    /***
     * Getter for the date of the message.
     * @return the date of the message
     */
    public LocalDateTime getDate() {
        return date;
    }

    /***
     * Setter for the id of the sender.
     * @param idSender the id of the sender
     */
    public void setIdSender(Long idSender) {
        this.idSender = idSender;
    }

    /***
     * Setter for the id of the receiver.
     * @param idReceiver the id of the receiver
     */
    public void setIdReceiver(Long idReceiver) {
        this.idReceiver = idReceiver;
    }

    /***
     * Setter for the text of the message.
     * @param messageText the text of the message
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /***
     * Setter for the date of the message.
     * @param date the date of the message
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
