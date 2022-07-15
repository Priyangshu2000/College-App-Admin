package com.example.collegeapp_admin.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp_admin.Notice.NoticeAdapter;
import com.example.collegeapp_admin.Notice.NoticeModel;
import com.example.collegeapp_admin.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.viewholder> {
    ArrayList<FacultyModel> list;
    Context context;
    DatabaseReference facultyRef;
    String Dept;
    public FacultyAdapter(ArrayList<FacultyModel> list, Context context,String department) {
        this.list = list;
        this.context = context;
        Dept=department;
        facultyRef= FirebaseDatabase.getInstance().getReference().child("Faculty");
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlefaculty, parent, false);
        return new FacultyAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        FacultyModel model=list.get(position);
        holder.name.setText(model.getName());
        holder.designation.setText(model.getDesignation());
        holder.email.setText(model.getEmail());
        Picasso.get().load(model.getUserpic()).placeholder(R.drawable.ic_baseline_person_24).into(holder.userPic);
        if(model.getGender().equals("Female"))
            Picasso.get().load(model.getUserpic()).placeholder(R.drawable.ic_baseline_person_2_24).into(holder.userPic);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= holder.getAdapterPosition();
                updateData(pos);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= holder.getAdapterPosition();
                deleteData(pos);
            }
        });
    }

    private void deleteData(int pos) {
        String key=list.get(pos).getFacultyId();
        DatabaseReference reference=facultyRef.child(Dept).child(key);
        reference.removeValue();
    }

    private void updateData(int pos) {
        Intent intent= new Intent(context,FacultyDetails.class);
        intent.putExtra("FacultyID",list.get(pos).getFacultyId());
        intent.putExtra("name",list.get(pos).getName());
        intent.putExtra("designation",list.get(pos).getDesignation());
        intent.putExtra("userPic",list.get(pos).getUserpic());
        intent.putExtra("gender",list.get(pos).getGender());
        intent.putExtra("email",list.get(pos).getEmail());
        intent.putExtra("flag","1");
        intent.putExtra("department",Dept);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,designation,email;
        MaterialButton update,delete;
        ImageView userPic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.updateFaculty_name);
            designation=itemView.findViewById(R.id.updateFaculty_position);
            email=itemView.findViewById(R.id.updateFaculty_email);
            update=itemView.findViewById(R.id.updateFaculty_updateButton);
            delete=itemView.findViewById(R.id.updateFaculty_deleteButton);
            userPic=itemView.findViewById(R.id.updateFaculty_userPic);
        }
    }
}
