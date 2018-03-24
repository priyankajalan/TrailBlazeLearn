package org.nus.trailblaze.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;


import org.nus.trailblaze.R;
import org.nus.trailblaze.models.DiscussionThread;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DiscussionThreadActivity extends AppCompatActivity {

    private EditText topic_text;
    private Button startThreadBtn;
    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_thread);

        topic_text = (EditText) findViewById(R.id.topic_text);
        startThreadBtn = (Button) findViewById(R.id.start_thread_btn);

        startThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = FieldValue.serverTimestamp().toString();
                String topic = topic_text.getText().toString();
                Map<String, Object> threadMap = new HashMap<>();
                threadMap.put("id",id);
                threadMap.put("topic",topic);

                if (!TextUtils.isEmpty(topic)) {
                    //Create Thread in Firebase
                    myDatabase = FirebaseDatabase.getInstance().getReference("threads");
                    myDatabase.child("QxGQnck3GNN72tKQLKR0v8BDSOM2").setValue(threadMap);

                    //Go To Contributed Item
                    Intent contributedItemIntent = new Intent(DiscussionThreadActivity.this,ContributedItemActivity.class);
                    startActivity(contributedItemIntent);
                    finish();
                }
            }
        });

    }
}
