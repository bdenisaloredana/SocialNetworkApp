package com.example.demo.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Friendship extends Entity<Long> {
    private Long idUser1;
    private Long idUser2;
    private LocalDateTime date;
    private Status status;

    /**
     * Constructor for a friendship.
     * @param id the id of the friendship
     * @param idUser1 the id of the first user of the friendship
     * @param idUser2 the id of the second user of the friendship
     * @param status  the status of the friendship
     */
    public Friendship(Long id, Long idUser1, Long idUser2, Status status) {
        super.setId(id);
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.date = LocalDateTime.now();
        this.status = status;
    }

    /***
     * Constructor for a friendship.
     * @param idUser1 the id of the first user of the friendship
     * @param idUser2 the id of the second user of the friendship
     * @param status the status of the friendship
     */
    public Friendship(Long idUser1, Long idUser2, Status status) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.status = status;
    }

    /**
     * Constructor for a friendship.
     * @param idUser1 the id of the first user of the friendship
     * @param idUser2 the id of the second user of the friendship
     * @param date    the date of the friendship
     * @param status  the status of the friendship
     */
    public Friendship(Long id, Long idUser1, Long idUser2, LocalDateTime date, Status status) {
        super.setId(id);
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.date = date;
        this.status = status;
    }

    /**
     * Getter for the id of the first user of the friendship .
     * @return the id of the first user of the friendship
     */
    public Long getIdUser1() {
        return this.idUser1;
    }

    /**
     * Getter for the id of the second user of the friendship .
     * @return the id of the second user of the friendship
     */
    public Long getIdUser2() {
        return this.idUser2;
    }

    /**
     * Getter for the date of the friendship.
     * @return the date of the friendship
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /***
     * Getter for the status of the friendship.
     * @return the status of the friendship
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Setter for the id of the first user of the friendship .
     * @param id the id of the first user of the friendship
     */
    public void setIdUser1(Long id) {
        this.idUser1 = id;
    }

    /**
     * Setter for the id of the second user of the friendship .
     * @param id the id of the second user of the friendship
     */
    public void setIdUser2(Long id) {
        this.idUser2 = id;
    }

    /**
     * Setter for the date of the friendship.
     * @param date the date of the friendship
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Setter for the status of the friendship.
     * @param status the status of the friendship
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /***
     * Overrides the equals function.
     * @param obj the object with which we compare
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Friendship))
            return false;
        Friendship friendship = (Friendship) obj;

        return Objects.equals(getIdUser1(), friendship.getIdUser1()) && Objects.equals(getIdUser2(), friendship.getIdUser2());
    }

    /**
     * Overrides the toString function.
     * @return a string with details about the friendship
     */
    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return getId() + " " + getIdUser1() + " " + getIdUser2() + " " + getDate().format(format) + " " + getStatus().toString();
    }

    /***
     * Override for the hasCode function.
     * @return the hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(idUser1, idUser2, date);
    }
}

