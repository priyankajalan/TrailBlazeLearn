package org.nus.trailblaze.dao;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import org.nus.trailblaze.models.User;

/**
 * Created by plasmashadow on 3/11/18.
 */

public class UserDao {

    private CollectionReference collectionRef;

    // stub for testing....
    public UserDao(CollectionReference ref){
        this.collectionRef = ref;
    }

    public UserDao() {
        this.collectionRef = FirebaseFirestore.getInstance().collection("users");
    }

    public Task<DocumentSnapshot> getUser(String id) {
        return this.collectionRef.document(id).get();
    }

    public Task<Void> storeUser(User user){
        return this.collectionRef.document(user.getId()).set(user);
    }

}