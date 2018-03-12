package org.nus.trailblaze.models;

/**
 * Created by plasmashadow on 3/11/18.
 */

public class Post extends Item{
    private String message;

    public Post(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
