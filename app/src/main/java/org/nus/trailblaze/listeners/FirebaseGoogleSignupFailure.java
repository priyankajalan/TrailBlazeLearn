package org.nus.trailblaze.listeners;

import android.util.Log;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by plasmashadow on 3/18/18.
 */

public class FirebaseGoogleSignupFailure implements OnFailureListener {

    private Activity activity;

    public FirebaseGoogleSignupFailure(Activity act){
        this.activity = act;
    }


    @Override
    public void onFailure(@NonNull Exception e) {
        Log.d("Failure", e.toString());
    }
}
