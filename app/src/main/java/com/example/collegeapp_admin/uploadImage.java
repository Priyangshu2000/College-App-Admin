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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class uploadImage extends AppCompatActivity {
Spinner spinner;
MaterialButton uploadImage;
CardView selectImage;
ImageView displayImage;
String selectedCategory;
Uri imageuri;
FirebaseStorage storage;
StorageReference storageReference;
DatabaseReference postref;
String time;
String postId;
ActivityResultLauncher<String> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        spinner=findViewById(R.id.select_category_uploadImage);
        uploadImage=findViewById(R.id.button_upload_image);
        selectImage=findViewById(R.id.selectImage_uploadImage);
        displayImage=findViewById(R.id.uploadImage_selected_image);
        postref= FirebaseDatabase.getInstance().getReference();

//        open gallery to choose image
        uploadFromGallery();
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch("image/*");
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageuri==null){
                    displayImage.requestFocus();
                    Toast.makeText(uploadImage.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
                else if (selectedCategory==null||selectedCategory.equals("--select category--")){
                    spinner.requestFocus();
                    Toast.makeText(uploadImage.this, "Please select category", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadData();
                }
            }
        });

        String items[]=new String[]{"--select category--","Independence day","convocation","sports day","others"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory=null;
            }
        });
    }


    public void uploadFromGallery() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageuri = result;
                displayImage.setImageURI(result);
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
        StorageReference ref= storageReference.child("images/");
        StorageReference filename=ref.child("file"+imageuri.getLastPathSegment());
        filename.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadImage.this, "Posted Successfully",Toast.LENGTH_SHORT).show();
                filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                                Toast.makeText(getContext(), uploadedurl+"here", Toast.LENGTH_SHORT).show();
                        postref.child("Images").child(selectedCategory).push().setValue(uri.toString());
                        onBackPressed();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(uploadImage.this, "Failed to upload!!",Toast.LENGTH_SHORT).show();
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