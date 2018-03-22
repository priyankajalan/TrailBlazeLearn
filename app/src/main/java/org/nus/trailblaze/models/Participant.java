package org.nus.trailblaze.models;

import android.os.Parcelable;

/**
 * Created by plasmashadow on 3/11/18.
 */

public class Participant extends User {
    public Participant(String id, String name, String email) {
        super(id, name, email);
    }

    public static Participant fromUser(User user){
        return new Participant(user.getId(), user.getName(), user.getEmail());
    }
}
