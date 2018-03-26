/**
 * Created by Liu Cao on 3/22/2018.
 */

package org.nus.trailblaze.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.nus.trailblaze.R;
import org.nus.trailblaze.models.ContributedItem;
import org.nus.trailblaze.models.Participant;
import org.nus.trailblaze.models.Photo;
import org.nus.trailblaze.models.TextDocument;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class ContributedItemImageActivity extends AppCompatActivity {

    private Button btnChooseImage, btnSaveImage;
    private ImageView imageView;
    private  EditText editText_Ci_ImageDesc;

    private Uri filePath;
    //Firebase
    private String mimeType;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db;
    Participant p= new Participant("PT1","Participant (Green)","Green@test.com");
    Photo po= new Photo("Photo1","My Photo","",1.0f,new Date(),"" );
    ContributedItem ci;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributed_item_image);

         btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
        btnSaveImage = (Button) findViewById(R.id.btnSaveImage);
         imageView = (ImageView) findViewById(R.id.imgView);
         editText_Ci_ImageDesc=(EditText)findViewById(R.id.editText_Ci_ImageDesc);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db= FirebaseFirestore.getInstance();

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                ci= new ContributedItem("ContributedItem_3",p,new Date(),po,editText_Ci_ImageDesc.getText().toString());
            }
        });

        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            mimeType=getContentResolver().getType(filePath);;
            Cursor returnCursor = getContentResolver().query(filePath, null, null, null, null);
            int name_index=returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int size_Index = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            po.setName( returnCursor.getString (name_index));
            po.setSize( returnCursor.getFloat(size_Index) );

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void saveImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ ci.getId().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            ci.getFile().setUrl(downloadUrl.toString());
                            ci.getFile().setMimeType(mimeType);
                            db.collection("contributed_items").document(ci.getId()).set(ci);
                            progressDialog.dismiss();
                            Toast.makeText(ContributedItemImageActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ContributedItemImageActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
