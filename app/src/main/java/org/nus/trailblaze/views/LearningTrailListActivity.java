package org.nus.trailblaze.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.nus.trailblaze.R;
import org.nus.trailblaze.TrailBlazeMainActivity;

public class LearningTrailListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_trail_list);

        //Account Settings Toolbar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar                                                                                                                            );
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Learning Trails");

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            sendToLogin();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings_menu:
                goToAccountSetupActivity();
                return true;
            case R.id.logout_menu:
                logout();
                return true;
            default:
                return false;
        }
    }

    private void logout(){
        firebaseAuth.signOut();
        sendToLogin();
    }
    private void sendToLogin(){
        Intent loginIntent = new Intent(LearningTrailListActivity.this,TrailBlazeMainActivity.class);
        startActivity(loginIntent);
        finish();
    }
    private void goToAccountSetupActivity(){
        Intent setupActivity = new Intent(LearningTrailListActivity.this,SetupActivity.class);
        startActivity(setupActivity);
        finish();
    }

}
