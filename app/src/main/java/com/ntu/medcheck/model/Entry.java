package com.ntu.medcheck.model;

/**
 * General entry class
 * extended by checkup and medication entry
 */
public class Entry {
    String name;
    String comment;
    String type; // type of medicine and type of checkup

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
