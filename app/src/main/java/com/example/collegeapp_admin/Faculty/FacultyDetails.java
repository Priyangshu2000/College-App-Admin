package com.example.collegeapp_admin.Faculty;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp_admin.R;
import com.example.collegeapp_admin.uploadNotice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class FacultyDetails extends AppCompatActivity {
ImageView userPic,addImage;
TextView name,email,designation,deptartment;
MaterialButton update;
RadioButton male,female;
String user_name,user_email,user_desg,dept,user_gender="";
Uri imageuri=null;
FirebaseStorage storage;
StorageReference storageReference;
String intent_name="",intent_email=" ",intent_desg=" ",intent_dept=" ",intent_gender="kjkj",intent_image="pic",faculty_id="";
String prevUrl="empty";
ActivityResultLauncher<String> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);
        userPic=findViewById(R.id.faculty_details_userPic);
        addImage=findViewById(R.id.faculty_details_add_image);
        name=findViewById(R.id.faculty_details_name);
        email=findViewById(R.id.faculty_details_email);
        designation=findViewById(R.id.faculty_details_desg);
        update=findViewById(R.id.faculty_details_updateButton);
        deptartment=findViewById(R.id.faculty_details_dept);
        male=findViewById(R.id.faculty_details_male);
        female=findViewById(R.id.faculty_details_female);
        if(getIntent().getStringExtra("flag").equals("1")) {
            intent_email = getIntent().getStringExtra("email");
            intent_name = getIntent().getStringExtra("name");
            intent_dept = getIntent().getStringExtra("department");
            intent_desg = getIntent().getStringExtra("designation");
            intent_gender = getIntent().getStringExtra("gender");
            intent_image = getIntent().getStringExtra("userPic");
            faculty_id = getIntent().getStringExtra("FacultyID");
            prevUrl=intent_image;
        }
        if(getIntent().getStringExtra("flag").equals("1")) {
            email.setText(intent_email);
            name.setText(intent_name);
            designation.setText(intent_desg);
            deptartment.setText(intent_dept);
            Picasso.get().load(intent_image).placeholder(R.drawable.ic_baseline_person_24).into(userPic);
            user_gender = intent_gender;
            if (intent_gender.equals("Male")) {

                male.setChecked(true);
            } else {
                female.setChecked(true);
            }
        }


        male.setOnClickListener(this::onRadioButtonClicked);
        female.setOnClickListener(this::onRadioButtonClicked);

        uploadFromGallery();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch("image/*");

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Empty");
                    name.setFocusable(true);
                }
                else if(email.getText().toString().isEmpty()){
                    email.setError("Empty");
                    email.setFocusable(true);
                }
                else if(designation.getText().toString().isEmpty()){
                    designation.setError("Empty");
                    designation.setFocusable(true);
                }
                else if(deptartment.getText().toString().isEmpty()){
                    deptartment.setError("Empty");
                    deptartment.setFocusable(true);
                }
                else if(user_gender.isEmpty()){
                    Toast.makeText(FacultyDetails.this, "Please select gender", Toast.LENGTH_SHORT).show();
                }
                else{
//                    Toast.makeText(FacultyDetails.this, faculty_id, Toast.LENGTH_SHORT).show();
                    user_name=name.getText().toString();
                    user_email=email.getText().toString();
                    user_desg=designation.getText().toString();
                    dept=deptartment.getText().toString();
                    sendData();
                }
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.faculty_details_male:
                if (checked)
                    user_gender="Male";
                    break;
            case R.id.faculty_details_female:
                if (checked)
                    user_gender="Female";
                    break;
        }
    }



    public void uploadFromGallery() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageuri = result;
                userPic.setImageURI(imageuri);
            }
        });
    }


    private void sendData() {

        DatabaseReference faculty= FirebaseDatabase.getInstance().getReference().child("Faculty");
        FacultyModel model=new FacultyModel();
//        model.setUserpic(imageuri.toString());
        model.setDesignation(user_desg);
        model.setEmail(user_email);
        model.setGender(user_gender);
        model.setName(user_name);
        model.setUserpic(prevUrl);
//        if(faculty_id.isEmpty())
//            faculty.child(dept).child(faculty_id).setValue(model);

        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

//        faculty.child(dept).push().setValue(model);
        if(imageuri==null){
            if(faculty_id.isEmpty()){
                faculty.child(dept).push().setValue(model);
                }
            else{
                faculty.child(dept).child(faculty_id).setValue(model);
            }
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();}
        else {
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref = storageReference.child("images/");
            StorageReference filename = ref.child("file" + imageuri.getLastPathSegment());
            filename.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(FacultyDetails.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                    filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
//                                Toast.makeText(getContext(), uploadedurl+"here", Toast.LENGTH_SHORT).show();
                            model.setUserpic(uri.toString());
                        if(faculty_id.isEmpty()){
                            faculty.child(dept).push().setValue(model);
                        }
                        else
                            faculty.child(dept).child(faculty_id).setValue(model);
                        name.setText("");
                        email.setText("");
                        designation.setText("");
                        deptartment.setText("");

                            onBackPressed();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FacultyDetails.this, "Failed to upload!! " + e.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
//                progressDialog.cancel();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress
                            = (100.0
                            * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount());
                    if ((int) progress == 100) {
                        progressDialog.dismiss();
                    }
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

}