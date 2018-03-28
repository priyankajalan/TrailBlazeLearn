package org.nus.trailblaze.views;
//Author : Priyanka Jalan

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.nus.trailblaze.R;

import butterknife.BindView;

public class DiscussionThreadActivity extends AppCompatActivity {
    //Declaring widgets
    private EditText threadMessageText;
    private ImageView newThreadPostImage;

    //Declaring Firebase
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_thread);

        //Typecasting Widgets
        newThreadPostImage = findViewById(R.id.new_thread_post_image);
        threadMessageText = (EditText) findViewById(R.id.thread_message_text);

        //Instantiate Firebase
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void sendPostClickListener(View view){
        String threadMessage = threadMessageText.getText().toString();

    }
}
