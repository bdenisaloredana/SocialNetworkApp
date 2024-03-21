package com.example.demo.entities;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;

    /***
     * Getter for the id of the entity.
     * @return the id of the entity
     */
    public ID getId() {
        return id;
    }

    /***
     * Setter for the id of the entity.
     * @param id the id of the entity
     */
    public void setId(ID id) {
        this.id = id;
    }

    /***
     * Override for the equals function.
     * @param o the object with which we compare
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    /***
     * Override for the hasCode function.
     * @return the hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
