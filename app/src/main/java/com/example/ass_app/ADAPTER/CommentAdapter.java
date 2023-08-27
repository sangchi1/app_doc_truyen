package com.example.ass_app.ADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_app.DTO.CommentDTO;
import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.DTO.UserDTO;
import com.example.ass_app.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{
    private List<CommentDTO> commentList;
    public CommentAdapter(List<CommentDTO> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        CommentDTO commentDTO =commentList.get(position);
        holder.tvuser.setText("User: "+commentDTO.getId_user().getUsername());
        holder.tvTime.setText("Time: "+commentDTO.getNgayGio());
        holder.tv_noidung.setText("Nội dung: "+commentDTO.getNoidung());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public  class CommentHolder extends RecyclerView.ViewHolder{

        private TextView tvuser,tvTime,tv_noidung;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            tvuser = itemView.findViewById(R.id.item_user);
            tvTime = itemView.findViewById(R.id.item_time);
            tv_noidung = itemView.findViewById(R.id.item_noidung);

        }

    }
}
