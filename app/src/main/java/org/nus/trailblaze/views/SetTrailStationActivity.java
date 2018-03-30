package org.nus.trailblaze.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.nus.trailblaze.R;
import org.nus.trailblaze.models.Trainer;
import org.nus.trailblaze.models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AswathyLeelakumari on 24/3/2018.
 */


public class SetTrailStationActivity extends AppCompatActivity {

    private Button mSaveBtn;
    private EditText mInstrn;
    private EditText mName;
    private EditText mSeq;
    private FirebaseFirestore mFireStore;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INSTRUCTION = "instruction";
    public static final String SEQ = "sequence";
    private static final String COLLECTION = "stations";
    public final static String DOCUMENTID ="org.nus.trailblaze.docID";
    public final static String SEQVALUE ="org.nus.trailblaze.seqVal";
    public final static String NAMEVALUE = "org.nus.trailblaze.nameVal";
    public final static String INSTRVALUE = "org.nus.trailblaze.instrVal";
    Map<String, String> stationMap = new HashMap<>();
    private String documentID;
    private String nameValue;
    private String instrValue;
    private  String seqValue;
    private String trailId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_trail_station);

        mFireStore = FirebaseFirestore.getInstance();

        mName = (EditText) findViewById(R.id.trail_name);
        mInstrn = (EditText) findViewById(R.id.trail_instructions);
        mSeq = (EditText) findViewById(R.id.trail_sequence);
        mSaveBtn = (Button) findViewById(R.id.btn_save_station);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nameValue = bundle.getString(NAMEVALUE);
        seqValue = bundle.getString(SEQVALUE);
        documentID = bundle.getString(DOCUMENTID);
        instrValue = bundle.getString(INSTRVALUE);

        trailId = intent.getStringExtra("trailID");
        Log.d("trainer set bundle", trailId);


        if (nameValue != null) {
            nameValue = nameValue.substring(nameValue.lastIndexOf("-") + 1);
            mName.setText(nameValue);
        }
        if (instrValue != null) {
            instrValue = instrValue.substring(instrValue.lastIndexOf("-") + 1);
            mInstrn.setText(instrValue);
        }
        if (seqValue != null) {
            seqValue = seqValue.substring(seqValue.lastIndexOf("-") + 1);
            mSeq.setText(seqValue);
        }
        if(documentID != null) {
            Log.d("docId not null",documentID);
        } else {
            Date date = new Date();
            documentID = (String) DateFormat.format("yyMMddhhmmss", date);
        }



        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stationName = mName.getText().toString();
                String stationInstrn = mInstrn.getText().toString();
                String stationSeq = mSeq.getText().toString();

                stationMap.put("name", stationName);
                stationMap.put("id", documentID);
                stationMap.put("instruction", stationInstrn);
                stationMap.put("sequence", stationSeq);
                stationMap.put("trail_id", trailId);

                mFireStore.collection(COLLECTION).document(documentID).set(stationMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SetTrailStationActivity.this,"Station changes saved",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), TrailStationMainActivity.class);
                        i.putExtra("trailID", trailId);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String err = e.getMessage();
                        Toast.makeText(SetTrailStationActivity.this, "Error: "+ err,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
