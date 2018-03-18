package org.nus.trailblaze;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;

import android.app.Activity;
import android.text.TextUtils;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import static org.junit.Assert.*;
import org.nus.trailblaze.dao.auth.GoogleDao;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
/**
 * Created by plasmashadow on 3/18/18.
 */


@RunWith(PowerMockRunner.class)
public class TestGoogleDao {

    @Mock
    FirebaseAuth auth;

    @Mock
    GoogleSignInAccount account;

    @Mock
    GoogleSignInOptions opts;

    @Mock
    GoogleSignInClient client;

    @Mock
    AuthCredential cred;

    @Mock
    Activity act;

    @PrepareForTest(GoogleAuthProvider.class)
    @Test
    public void testFirebaseGooglePass(){
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(GoogleAuthProvider.class);
        GoogleDao dao = new GoogleDao(this.auth);
        PowerMockito.when(account.getIdToken()).thenReturn("sometoken");
        PowerMockito.when(GoogleAuthProvider.getCredential("sometoken", null)).thenReturn(cred);
        dao.createFirebaseGoogleAuth(account);
        verify(auth).signInWithCredential(cred);
    }

    @PrepareForTest({GoogleDao.class, GoogleSignIn.class})
    @Test
    public void testGetSignInClient(){
        PowerMockito.mockStatic(GoogleDao.class);
        PowerMockito.mockStatic(GoogleSignIn.class);
        GoogleDao dao = new GoogleDao(this.auth);
        PowerMockito.when(GoogleDao.getOptions()).thenReturn(opts);
        PowerMockito.when(GoogleSignIn.getClient(this.act, opts)).thenReturn(client);
        assertThat(dao.getClient(this.act), is(client));
    }

    @Test
    public void testGetCurrentUser(){
        GoogleDao dao = new GoogleDao(this.auth);
        FirebaseUser user = mock(FirebaseUser.class);
        when(this.auth.getCurrentUser()).thenReturn(user);
        assertThat(dao.getCurrent(), is(user));
    }


}
