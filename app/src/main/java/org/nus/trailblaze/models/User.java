package org.nus.trailblaze.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Created by plasmashadow on 3/11/18.
 */


@IgnoreExtraProperties
public class User {
    public String id;
    public String name;
    public String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
