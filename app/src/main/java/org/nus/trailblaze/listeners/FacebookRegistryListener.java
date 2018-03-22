package org.nus.trailblaze.listeners;

import android.app.Activity;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import org.nus.trailblaze.dao.FacebookDao;

/**
 * Created by plasmashadow on 3/21/18.
 */

public class FacebookRegistryListener implements FacebookCallback<LoginResult> {

    private FacebookDao dao;
    private Activity activity;
    private Class next;

    public FacebookRegistryListener(Activity activity, FacebookDao dao, Class next){
        this.activity = activity;
        this.dao = dao;
        this.next = next;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        dao.createFirebaseFacebookAuth(loginResult.getAccessToken())
           .addOnCompleteListener(new SignInListener(this.activity, this.dao, this.next))
                .addOnFailureListener(new SignInFailureListener(this.activity));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
}
