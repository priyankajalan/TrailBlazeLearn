package org.nus.trailblaze.dao;


import android.app.Activity;
import android.content.Context;
import org.nus.trailblaze.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by plasmashadow on 3/18/18.
 */

public class GoogleDao {
    private FirebaseAuth auth;

    public GoogleDao(FirebaseAuth auth) {
        this.auth = auth;
    }

    public GoogleDao(){
        this.auth = FirebaseAuth.getInstance();
    }

    public GoogleSignInOptions getOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("521102015172-15i641ilk0nedmi2je3gph69jdgdrn0m.apps.googleusercontent.com")
                .requestEmail()
                .build();
    }

    public GoogleSignInClient getClient(Activity act) {
        GoogleSignInOptions gso = this.getOptions();
        GoogleSignInClient client = GoogleSignIn.getClient(act, gso);
        return client;
    }

    public Task<AuthResult> createFirebaseGoogleAuth(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return this.auth.signInWithCredential(credential);
    }

    public FirebaseUser getCurrent(){
        return this.auth.getCurrentUser();
    }
}
