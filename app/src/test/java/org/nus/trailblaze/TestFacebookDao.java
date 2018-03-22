package org.nus.trailblaze;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import static org.junit.Assert.*;
import org.nus.trailblaze.dao.FacebookDao;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
/**
 * Created by plasmashadow on 3/21/18.
 */


@RunWith(PowerMockRunner.class)
@PrepareForTest({FacebookAuthProvider.class, FirebaseAuth.class, AccessToken.class, FirebaseFirestore.class})
public class TestFacebookDao {

    @Mock
    FirebaseAuth auth;

    @Mock
    FirebaseFirestore store;

    @Mock
    AuthCredential cred;

    @Mock
    AccessToken token;

    @Test
    public void testFacebookCreateCreds(){
        PowerMockito.mockStatic(FirebaseAuth.class);
        PowerMockito.mockStatic(FacebookAuthProvider.class);
        PowerMockito.mockStatic(FirebaseFirestore.class);
        PowerMockito.when(FirebaseFirestore.getInstance()).thenReturn(this.store);
        FacebookDao dao = new FacebookDao(this.auth);
        PowerMockito.when(token.getToken()).thenReturn("sometoken");
        PowerMockito.when(FacebookAuthProvider.getCredential("sometoken")).thenReturn(this.cred);
        dao.createFirebaseFacebookAuth(this.token);
        verify(this.token).getToken();
        verify(this.auth).signInWithCredential(this.cred);
    }
}
