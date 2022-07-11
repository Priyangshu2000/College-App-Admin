package com.example.collegeapp_admin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class uploadNotice extends AppCompatActivity {
CardView addImage;
Uri imageuri=null;
ImageView pick;
MaterialButton uploadNotice;
EditText noticeTitle;
FirebaseStorage storage;
StorageReference storageReference;
DatabaseReference postref;
String time;
String postId;
ActivityResultLauncher<String> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        addImage=findViewById(R.id.addImage);
        pick=findViewById(R.id.notice_selected_image);
        noticeTitle=findViewById(R.id.notice_title);
        uploadNotice=findViewById(R.id.button_upload_notice);
        postref=FirebaseDatabase.getInstance().getReference();
        uploadFromGallery();
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch("image/*");
            }
        });
        uploadNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noticeTitle.getText().toString().isEmpty()){
//                    Toast.makeText(uploadNotice.this, "Please enter title", Toast.LENGTH_SHORT).show();
                    noticeTitle.setError("Empty");
                    noticeTitle.requestFocus();
                }
              else if(imageuri==null){
                    time= Calendar.getInstance().getTime().toString();
                    time=time.substring(0,20);
                    postId=time;
                    postref.child("Notice").child(postId).child("noticeTitle").setValue(noticeTitle.getText().toString());
                }
              else{
                  uploadData();
                }
            }
        });
    }

    private void uploadData() {
        time= Calendar.getInstance().getTime().toString();
        time=time.substring(0,20);
        postId=time;
        postref.child("Notice").child(postId).child("noticeTitle").setValue(noticeTitle.getText().toString());
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref= storageReference.child("images/");
        StorageReference filename=ref.child("file"+imageuri.getLastPathSegment());
        filename.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadNotice.this, "Posted Successfully",Toast.LENGTH_SHORT).show();
                filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                                Toast.makeText(getContext(), uploadedurl+"here", Toast.LENGTH_SHORT).show();
                        postref.child("Notice").child(postId).child("noticeImage").setValue(uri.toString());
                        onBackPressed();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadNotice.this, "Failed to upload!!",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress
                        = (100.0
                        * taskSnapshot.getBytesTransferred()
                        / taskSnapshot.getTotalByteCount());
                if((int)progress==100)
                {
                    progressDialog.dismiss();}
                progressDialog.setMessage("Uploaded "+ (int)progress + "%");
            }
        });

    }

    public void uploadFromGallery() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageuri = result;
                pick.setImageURI(result);
            }
        });
    }
}