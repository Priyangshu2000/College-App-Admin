package com.example.collegeapp_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
CardView addNotice,uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNotice = findViewById(R.id.upload_notice);
        uploadImage=findViewById(R.id.uploadImage);
        addNotice.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
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
        }
    }
}