package org.nus.trailblaze.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by plasmashadow on 3/18/18.
 */

public class AuthDao {
    private FirebaseAuth auth;

    public AuthDao(FirebaseAuth auth){
        this.auth = auth;
    }

    public AuthDao(){
        this.auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseUser getCurrent(){
        return this.auth.getCurrentUser();
    }
}
