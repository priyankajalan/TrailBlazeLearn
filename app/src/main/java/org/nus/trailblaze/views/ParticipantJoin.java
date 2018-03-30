package org.nus.trailblaze.views;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.nus.trailblaze.R;
import org.nus.trailblaze.models.Participant;
import org.nus.trailblaze.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticipantJoin extends AppCompatActivity {

    private static Class next = TrailStationMainActivity.class;
    private Participant participant;

    @BindView(R.id.searchId)
    EditText searchid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_join);
        ButterKnife.bind(this);
        this.participant = Participant.fromUser((User) this.getIntent().getExtras().get("participant"));
    }

    public void search(View v){
        String term = this.searchid.getText().toString();
        if(this.validate(term)){
            Intent nextTo = new Intent(this, ParticipantJoin.next);
            nextTo.putExtra("user", this.participant);
            nextTo.putExtra("trailId", term);
            nextTo.putExtra("userMode", "participant");
            startActivity(nextTo);
        }
        else {
            Log.d("[Alert/Debug]", "Invalid term");
        }

    }

    public boolean validate(String term){
        return term != null;
    }

}
