package org.nus.trailblaze.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.nus.trailblaze.models.LearningTrail;
import org.nus.trailblaze.models.Trainer;

/**
 * Created by plasmashadow on 3/24/18.
 */

public class LearningTrailDao {

    private static final String TRAIL_COLLECTION = "trails";
    private CollectionReference ref;

    public LearningTrailDao(CollectionReference ref){
        this.ref = ref;
    }

    public LearningTrailDao(){
        this.ref = FirebaseFirestore.getInstance().collection(LearningTrailDao.TRAIL_COLLECTION);
    }


    public Task<QuerySnapshot> getTrailById(String trail){
        return  this.ref.whereEqualTo("trail_id", trail).get();
    }

    public Task<QuerySnapshot> getTrails(Trainer trainer){
        return this.ref.whereEqualTo("trainer_id", trainer.getId()).get();
    }

    public Task<Void> createNewTrail(LearningTrail trail){
        return this.ref.document(trail.getId()).set(trail);
    }

    public Task<Void> deleteTrail(LearningTrail trail){
        return this.ref.document(trail.getId()).delete();
    }
}
