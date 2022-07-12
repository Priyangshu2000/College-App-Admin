package com.example.collegeapp_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.collegeapp_admin.Faculty.updateFaculty;
import com.example.collegeapp_admin.Notice.deleteNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
CardView addNotice,uploadImage,uploadEbook,deleteNotice,updateFaculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNotice = findViewById(R.id.upload_notice);
        uploadImage=findViewById(R.id.uploadImage);
        uploadEbook=findViewById(R.id.uploadEbook);
        deleteNotice=findViewById(R.id.delete_notice);
        updateFaculty=findViewById(R.id.updateFaculty);
        addNotice.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
        uploadEbook.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
        updateFaculty.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_notice:
                Intent notice= new Intent(MainActivity.this,uploadNotice.class);
                startActivity(notice);
                break;
            case R.id.uploadImage:
                Intent uploadImage= new Intent(MainActivity.this,uploadImage.class);
                startActivity(uploadImage);
                break;
            case R.id.uploadEbook:
                Intent uploadEbook= new Intent(MainActivity.this,uploadEbook.class);
                startActivity(uploadEbook);
                break;
            case R.id.delete_notice:
                Intent delNotice= new Intent(MainActivity.this, deleteNotice.class);
                startActivity(delNotice);
                break;
            case R.id.updateFaculty:
                Intent update_faculty= new Intent(MainActivity.this, updateFaculty.class);
                startActivity(update_faculty);
                break;
        }
    }
}