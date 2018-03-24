package org.nus.trailblaze.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.nus.trailblaze.R;



/**
 * Created by priyanka on 15/3/2018.
 */

public class SetLearningTrailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titlecode;
    private String trailId;
    private static final String NAME = "name";
    Button btn;

    Map<String, Object> noteDataMap = new HashMap<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_learning_trail);
        titlecode = findViewById(R.id.trailCodeDisplay);
        noteDataMap.put(NAME, titlecode.getText());

        btn = (Button) findViewById(R.id.newTrail);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick (View view) {
        //todo: bind date and trailcode
        //todo: in edit mode, need to pass document value
        db.collection("trails").document().set(noteDataMap)

        .addOnSuccessListener(new OnSuccessListener< Void >() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SetLearningTrailActivity.this, "Learning Trail Saved",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(),LearningTrailMainActivity.class);
                startActivity(i);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SetLearningTrailActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
}
