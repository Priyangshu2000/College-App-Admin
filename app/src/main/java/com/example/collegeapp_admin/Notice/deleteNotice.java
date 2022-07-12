package com.example.collegeapp_admin.Notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.collegeapp_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class deleteNotice extends AppCompatActivity {
RecyclerView recyclerView;
ProgressBar progressBar;
DatabaseReference noticeRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);
        recyclerView=findViewById(R.id.delete_notice_recyclerView);
        progressBar=findViewById(R.id.delete_notice_progressbar);
        progressBar.setVisibility(View.VISIBLE);


        ArrayList<NoticeModel> noticeModel=new ArrayList<>();
        NoticeAdapter adapter=new NoticeAdapter(noticeModel,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        noticeRef= FirebaseDatabase.getInstance().getReference();
        noticeRef.child("Notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noticeModel.clear();
                for(DataSnapshot d:snapshot.getChildren()){
                    NoticeModel model=d.getValue(NoticeModel.class);
                    model.setNoticeId(d.getKey());
                    model.setHeading("Heading of Notice");
                    noticeModel.add(model);
                    progressBar.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(deleteNotice.this, "Can not retrieve information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}