package org.nus.trailblaze.views;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.nus.trailblaze.R;
import org.nus.trailblaze.dao.LearningTrailDao;
import org.nus.trailblaze.listeners.SignInFailureListener;
import org.nus.trailblaze.listeners.TrialSearchListener;
import org.nus.trailblaze.models.Participant;
import org.nus.trailblaze.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticipantJoin extends AppCompatActivity {

    private static Class next = TrailBlazaFeedActivity.class;
    private Participant participant;
    private LearningTrailDao dao;

    @BindView(R.id.searchId)
    EditText searchid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_join);
        ButterKnife.bind(this);
        this.participant = Participant.fromUser((User) this.getIntent().getExtras().get("participant"));
        this.dao = new LearningTrailDao();
    }

    public void search(View v){
        String term = this.searchid.getText().toString();
        if(this.validate(term)){
            this.searchAndSend(term);
        }
        else {
            Log.d("[Alert/Debug]", "Invalid term");
        }

    }

    public boolean validate(String term){
        return term != null;
    }

    public void searchAndSend(String trailid){
        Log.d("trailid", trailid);
        this.dao.getTrailById(trailid)
                .addOnCompleteListener(new TrialSearchListener(this, ParticipantJoin.next, this.participant, trailid))
                .addOnFailureListener(new SignInFailureListener(this));
    }

}
