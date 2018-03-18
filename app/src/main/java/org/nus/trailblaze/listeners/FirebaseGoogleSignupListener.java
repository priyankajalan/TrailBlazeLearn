package org.nus.trailblaze.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.nus.trailblaze.TrailBlazaFeedActivity;
import org.nus.trailblaze.dao.GoogleDao;

/**
 * Created by plasmashadow on 3/18/18.
 */

public class FirebaseGoogleSignupListener implements OnCompleteListener<AuthResult> {

    private Activity activity;
    private GoogleDao dao;

    public FirebaseGoogleSignupListener(Activity act, GoogleDao gdao){
        this.activity = act;
        this.dao = gdao;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        // do some code here.
        FirebaseUser user = this.dao.getCurrent();
        Log.d("User", user.toString());
        Intent nextActivity = new Intent(this.activity, TrailBlazaFeedActivity.class);
        Log.d("redirect", "to next activity");
        this.activity.startActivity(nextActivity);
    }
}
