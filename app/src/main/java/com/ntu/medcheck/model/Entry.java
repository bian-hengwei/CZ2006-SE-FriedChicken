package com.ntu.medcheck.model;

/**
 * General entry class
 * extended by checkup and medication entry
 * @author Fu Yongding
 */
public class Entry {
    String name;
    String comment;
    String type;

    /**
     * Constructor for Entry class
     */
    public Entry() {
    }

    /**
     * Constructor for Entry class with name and type input
     * @param name
     * @param type
     */
    public Entry(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Getter for name of entry
     * @return name of entry
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name of entry
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for comment of entry
     * @return comment of entry
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for comment of entry
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter for type of entry
     * @return type of entry
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type of entry
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
