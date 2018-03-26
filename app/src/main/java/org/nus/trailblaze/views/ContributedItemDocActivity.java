/**
 * Created by Liu Cao on 3/20/2018.
 */

package org.nus.trailblaze.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.nus.trailblaze.R;
import org.nus.trailblaze.adapters.IntentHelper;
import org.nus.trailblaze.models.ContributedItem;
import org.nus.trailblaze.models.File;
import org.nus.trailblaze.models.Participant;
import org.nus.trailblaze.models.TextDocument;
import org.nus.trailblaze.models.User;

import java.util.Date;
import java.util.UUID;

public class ContributedItemDocActivity extends AppCompatActivity  {
    private Button btnUpload;
    private ImageButton btnChoose;
    private Uri filePath;
    private final int PICK_DOC_REQUEST = 71;
    private EditText editText_Description;
    private String mimeType;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db;
    //Get Participant from context
    Participant p= new Participant("PT1","Participant (Green)","Green@test.com");
    TextDocument td= new TextDocument("Doc1","Document (PDF/Text)","Test@Url",1.0f,new Date(),"PDF/TXT" );
    ContributedItem ci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributed_item_doc);
        //Initialize Views
        btnChoose = (ImageButton) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        editText_Description=(EditText)findViewById(R.id.editText_Description);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db= FirebaseFirestore.getInstance();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
                ci= new ContributedItem("ContributedItem_1",p,new Date(),td,editText_Description.getText().toString());
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveContributedItemDoc();
            }
        });
    }

    private void chooseFile()
    {
        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
        intent= IntentHelper.SetIntentType("document",intent);
        startActivityForResult(Intent.createChooser(intent,"Choose File"), PICK_DOC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_DOC_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            mimeType=getContentResolver().getType(filePath);;

            Cursor returnCursor = getContentResolver().query(filePath, null, null, null, null);
            int name_index=returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int size_Index = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            td.setName( returnCursor.getString (name_index));
            td.setSize( returnCursor.getFloat(size_Index) );
        }
    }

    private void SaveContributedItemDoc() {


        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving...");
            progressDialog.show();
            StorageReference ref = storageReference.child("document/"+ ci.getFile().getName().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            ci.getFile().setUrl(downloadUrl.toString());
                            ci.getFile().setMimeType(mimeType);
                            db.collection("contributed_items").document(ci.getId()).set(ci);
                            progressDialog.dismiss();
                            Toast.makeText(ContributedItemDocActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ContributedItemDocActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Saved "+(int)progress+"%");
                        }
                    });
        }
    }

}

