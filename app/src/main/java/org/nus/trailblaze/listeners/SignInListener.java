package org.nus.trailblaze.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.nus.trailblaze.dao.AuthDao;
import org.nus.trailblaze.models.User;

/**
 * Created by plasmashadow on 3/21/18.
 */

public class SignInListener implements OnCompleteListener<AuthResult> {
    private Activity activity;
    private AuthDao dao;
    private Class next;

    public SignInListener(Activity act, AuthDao dao, Class next){
        this.activity = act;
        this.dao = dao;
        this.next = next;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        // do some code here.
        if(task.isSuccessful()){
            FirebaseUser user = this.dao.getCurrent();
            Log.d("User", user.toString());
            Intent nextActivity = new Intent(this.activity, this.next);
            Log.d("redirect", "to next activity");
            this.dao.saveUser(user);
            User trailblazeUser = this.dao.fromFirebaseUser(user);
            nextActivity.putExtra("user", trailblazeUser);
            this.activity.startActivity(nextActivity);
        } else {
            Log.d("[Alert]", "Signin failure");
        }

    }
}
