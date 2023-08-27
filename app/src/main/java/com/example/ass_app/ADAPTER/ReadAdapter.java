package com.example.ass_app.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_app.DTO.ReadDTO;

import com.example.ass_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadHolder> {
    private Context context;
    private List<ReadDTO> list= new ArrayList<>();

    public ReadAdapter(List<ReadDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read,parent,false);
        return new ReadHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadHolder holder, int position) {
        ReadDTO readDTO = list.get(position);
        if (readDTO==null){
            return;
        }
   //     Picasso.get().load(readDTO.getRead()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public  class ReadHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        public ReadHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_read);


        }

    }
}
