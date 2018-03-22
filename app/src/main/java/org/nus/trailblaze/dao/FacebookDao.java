package org.nus.trailblaze.dao;

import com.facebook.AccessToken;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;

/**
 * Created by plasmashadow on 3/18/18.
 */

public class FacebookDao extends AuthDao {

    public FacebookDao(){
        super();
    }

    public FacebookDao(FirebaseAuth auth){
        super(auth);
    }

    public Task<AuthResult> createFirebaseFacebookAuth(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        return this.getAuth().signInWithCredential(credential);
    }

}
