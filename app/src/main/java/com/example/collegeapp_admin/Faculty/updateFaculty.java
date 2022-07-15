package com.example.collegeapp_admin.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeapp_admin.Faculty.FacultyModel;
import com.example.collegeapp_admin.Notice.NoticeAdapter;
import com.example.collegeapp_admin.Notice.deleteNotice;
import com.example.collegeapp_admin.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class updateFaculty extends AppCompatActivity {
RecyclerView cs,it,ece;
LinearLayout csnodata,itnodata,ecenodata;
ArrayList<FacultyModel>csfaculty,ecefaculty,ITfaculty;
DatabaseReference facultyRef;
ExtendedFloatingActionButton efab;
FacultyAdapter adapterIT,adaptercs,adapterece;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        cs=findViewById(R.id.updateFaculty_cs_rv);
        it=findViewById(R.id.updateFaculty_IT_rv);
        ece=findViewById(R.id.updateFaculty_ece_rv);
        csnodata=findViewById(R.id.updateFaculty_nodatafoundcs);
        itnodata=findViewById(R.id.updateFaculty_nodatafoundIT);
        ecenodata=findViewById(R.id.updateFaculty_nodatafoundEce);
        efab=findViewById(R.id.updateFaculty_fab);

        csfaculty=new ArrayList<>();
        ecefaculty=new ArrayList<>();
        ITfaculty=new ArrayList<>();

        facultyRef=FirebaseDatabase.getInstance().getReference().child("Faculty");



       adapterIT=new FacultyAdapter(ITfaculty,this,"Information Technology");
        it.setAdapter(adapterIT);
        LinearLayoutManager layoutManagerIT=new LinearLayoutManager(this);
        it.setLayoutManager(layoutManagerIT);

        adaptercs=new FacultyAdapter(csfaculty,this,"Computer Science");
        cs.setAdapter(adaptercs);
        LinearLayoutManager layoutManagercs=new LinearLayoutManager(this);
        cs.setLayoutManager(layoutManagercs);


        adapterece=new FacultyAdapter(ecefaculty,this,"Electronics and Communication");
        ece.setAdapter(adapterece);
        LinearLayoutManager layoutManagerece=new LinearLayoutManager(this);
        ece.setLayoutManager(layoutManagerece);


        csloadFaculty();
        ITloadFaculty();
        eceloadFaculty();



        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(updateFaculty.this,FacultyDetails.class);
                intent.putExtra("flag","0");
                startActivity(intent);
            }
        });
    }



    private void ITloadFaculty() {
        facultyRef.child("Information Technology").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    itnodata.setVisibility(View.VISIBLE);
                    it.setVisibility(View.GONE);
                } else {
                    ITfaculty.clear();
                    itnodata.setVisibility(View.GONE);
                    it.setVisibility(View.VISIBLE);
                    for (DataSnapshot d : snapshot.getChildren()) {
                        FacultyModel model;
                        model = d.getValue(FacultyModel.class);
                        model.setFacultyId(d.getKey());
                        ITfaculty.add(model);
                    }
                    adapterIT.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this, "Can not retrieve information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void csloadFaculty() {
        facultyRef.child("Computer Science").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                csfaculty.clear();
                if (!snapshot.exists()) {
                    csnodata.setVisibility(View.VISIBLE);
                cs.setVisibility(View.GONE);}
                    else{
                    csnodata.setVisibility(View.GONE);
                    cs.setVisibility(View.VISIBLE);
                        for (DataSnapshot d : snapshot.getChildren()) {
                            FacultyModel model;
                            model = d.getValue(FacultyModel.class);
                            model.setFacultyId(d.getKey());
                            csfaculty.add(model);
                        }
                        adaptercs.notifyDataSetChanged();
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this, "Can not retrieve information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eceloadFaculty() {
        facultyRef.child("Electronics And Communication").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ecefaculty.clear();
                if (!snapshot.exists()) {
                    ecenodata.setVisibility(View.VISIBLE);
                    ece.setVisibility(View.GONE);
                } else{
                    ecenodata.setVisibility(View.GONE);
                    ece.setVisibility(View.VISIBLE);
                    for (DataSnapshot d : snapshot.getChildren()) {
                        FacultyModel model;
                        model = d.getValue(FacultyModel.class);
                        model.setFacultyId(d.getKey());
                        ecefaculty.add(model);
                    }
                    adapterece.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this, "Can not retrieve information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}