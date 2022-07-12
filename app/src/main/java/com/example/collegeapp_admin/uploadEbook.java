package com.example.collegeapp_admin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.logging.FileHandler;

public class uploadEbook extends AppCompatActivity {
CardView selectEbook;
Button upload;
Uri bookuri=null;
TextView selectedBook;
FirebaseStorage storage;
StorageReference storageReference;
DatabaseReference ebook;
ActivityResultLauncher<String> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ebook);
        selectEbook=findViewById(R.id.selectEbooklogo);
        upload=findViewById(R.id.button_upload_ebook);
        selectedBook=findViewById(R.id.uploadBook_selected_book);
        ebook= FirebaseDatabase.getInstance().getReference();

        uploadFromFile();
        selectEbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch("*/*");
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookuri==null)
                    Toast.makeText(uploadEbook.this, "No Book selected", Toast.LENGTH_SHORT).show();
                else
                    uploadData();
            }
        });
    }




    public void uploadFromFile() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                bookuri = result;
                if(result==null)
                    return;
                selectedBook.setVisibility(View.VISIBLE);
                selectedBook.setText(bookuri.toString());
//                Toast.makeText(uploadEbook.this, result.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadData() {
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref= storageReference.child("Ebooks/");
        StorageReference filename=ref.child("file"+bookuri.getLastPathSegment());
        filename.putFile(bookuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadEbook.this, "Posted Successfully",Toast.LENGTH_SHORT).show();
                filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ebook.child("Ebooks").push().setValue(uri.toString());
                        onBackPressed();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadEbook.this, "Failed to upload!!",Toast.LENGTH_SHORT).show();
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
}