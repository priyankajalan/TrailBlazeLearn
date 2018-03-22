package org.nus.trailblaze;

import android.content.Intent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;

import org.nus.trailblaze.dao.FacebookDao;
import org.nus.trailblaze.listeners.FacebookRegistryListener;
import org.nus.trailblaze.listeners.SignInFailureListener;
import org.nus.trailblaze.listeners.SignInListener;

import org.nus.trailblaze.dao.GoogleDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailBlazeMainActivity extends AppCompatActivity {


    public static final int GOOGLE_SIGN = 9001;
    public static final int FACEBOOK_SIGN = 64206;

    private GoogleSignInClient gClient;
    private GoogleDao gDao;
    public FirebaseUser loggedInUser;
    private CallbackManager mCallback;
    private FacebookDao fDao;

    @BindView(R.id.progressBar)
    ProgressBar bar;

    @BindView(R.id.facebookBtn)
    LoginButton fbLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail_blaze_main);
        ButterKnife.bind(this);
        mCallback = CallbackManager.Factory.create();
        gDao = new GoogleDao();
        fDao = new FacebookDao();
        gClient = gDao.getClient(this);
        Log.d("client id", getString(R.string.default_web_client_id));
        loggedInUser = fDao.getCurrent();
        if(loggedInUser != null){
            Intent next = new Intent(this, TrailBlazaFeedActivity.class);
            startActivity(next);
        }
        fbLoginButton.registerCallback(mCallback, new FacebookRegistryListener(this, this.fDao, TrailBlazaFeedActivity.class));
    }

    // Google click listener
    public void googleClickListener(View view){
        Intent intent = gClient.getSignInIntent();
        startActivityForResult(intent, GOOGLE_SIGN);
    }

    // update on google account selected.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("act", "got the code");
        Log.d("mtg", String.valueOf(requestCode));
        if(requestCode == GOOGLE_SIGN){
            Log.d(String.valueOf(requestCode), data.toString());
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("account", account.toString());
                bar.setVisibility(bar.VISIBLE);
                this.gDao
                        .createFirebaseGoogleAuth(account)
                        .addOnCompleteListener(this,
                                new SignInListener(this, this.gDao, TrailBlazaFeedActivity.class))
                        .addOnFailureListener(this, new SignInFailureListener(this));
            }
            catch (ApiException e){
                // handle exeception here.
                Log.d("Exception", e.toString());
                e.printStackTrace();
            }

        } else if(requestCode == FACEBOOK_SIGN) {
            bar.setVisibility(bar.VISIBLE);
            mCallback.onActivityResult(requestCode, resultCode, data);
        }
    }

}
