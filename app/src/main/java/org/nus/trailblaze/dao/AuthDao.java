package org.nus.trailblaze.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.nus.trailblaze.models.User;

/**
 * Created by plasmashadow on 3/18/18.
 */

public class AuthDao {

    private FirebaseAuth auth;
    private UserDao dao;

    public AuthDao(FirebaseAuth auth){
        this.auth = auth;
        this.dao = new UserDao();
    }

    public AuthDao(){

        this.auth = FirebaseAuth.getInstance();
        this.dao = new UserDao();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseUser getCurrent(){
        return this.auth.getCurrentUser();
    }

    public User fromFirebaseUser(FirebaseUser user){
        return new User(user.getUid(), user.getDisplayName(), user.getEmail());
    }

    public Task<Void> saveUser(FirebaseUser user){
        User tobeSaved = this.fromFirebaseUser(user);
        return this.dao.storeUser(tobeSaved);
    }

    public Task<DocumentSnapshot> getUser(FirebaseUser user){
        return this.dao.getUser(user.getUid());
    }
}
