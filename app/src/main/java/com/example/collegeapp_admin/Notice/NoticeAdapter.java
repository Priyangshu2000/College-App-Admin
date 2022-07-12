package com.example.collegeapp_admin.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp_admin.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.viewholder> {
    ArrayList<NoticeModel>list;
    Context context;
    DatabaseReference noticeRef;

    public NoticeAdapter(ArrayList<NoticeModel> list, Context context) {
        this.list = list;
        this.context = context;
        noticeRef=FirebaseDatabase.getInstance().getReference().child("Notice");
    }

    public NoticeAdapter() {
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenotice, parent, false);
        context=parent.getContext();
        return new NoticeAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        NoticeModel ithnotice=list.get(position);
        holder.heading.setText(ithnotice.getHeading());
        holder.noticeBody.setText(ithnotice.getBody());
        Picasso.get().load(ithnotice.getUserPic()).placeholder(R.drawable.ic_baseline_person_24).into(holder.userPic);
        Picasso.get().load(ithnotice.getNoticePic()).into(holder.noticePic);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                deleteData(pos);
            }
        });
    }

    private void deleteData(int pos) {
        String key=list.get(pos).getNoticeId();
        DatabaseReference reference=noticeRef.child(key);
        reference.removeValue();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView heading,noticeBody;
        MaterialButton delete;
        CircleImageView userPic;
        ImageView noticePic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.delete_notice_heading);
            noticeBody=itemView.findViewById(R.id.delete_notice_body);
            delete=itemView.findViewById(R.id.delete_notice_deleteButton);
            userPic=itemView.findViewById(R.id.delete_notice_userPic);
            noticePic=itemView.findViewById(R.id.delete_notice_notice_pic);
        }
    }
}
