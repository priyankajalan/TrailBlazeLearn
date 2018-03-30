package org.nus.trailblaze.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import org.nus.trailblaze.models.Participant;
import org.nus.trailblaze.models.User;

/**
 * Created by plasmashadow on 3/30/18.
 */

public class TrialSearchListener implements OnCompleteListener<QuerySnapshot> {

    private Activity activity;
    private Class nextActivity;
    private User user;
    private String trailID;

    public TrialSearchListener(Activity activity, Class next, User user, String trialid){
        this.activity = activity;
        this.nextActivity = next;
        this.user = user;
        this.trailID = trialid;
    }


    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        Log.d("[TASK]", String.valueOf(task.isComplete()));
        if(task.isSuccessful()){
            QuerySnapshot result = task.getResult();
            Log.d("query_result", String.valueOf(result.isEmpty()));
            if(!result.isEmpty()){
                Intent intent = new Intent(this.activity, this.nextActivity);
                intent.putExtra("userMode", "participant");
                intent.putExtra("participant", Participant.fromUser(this.user));
                intent.putExtra("trailID", this.trailID);
                this.activity.startActivity(intent);
            }
            else {
                Toast.makeText(this.activity, "Learning trail not found", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this.activity, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }
}