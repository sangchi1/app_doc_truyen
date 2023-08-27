package com.example.ass_app.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TruyenAdapter extends RecyclerView.Adapter<TruyenAdapter.TruyenHolder>{
    private Context context;
    private List<TruyenDTO> list= new ArrayList<>();
    private OnItemClickListener listener;

    public TruyenAdapter(Context context, List<TruyenDTO> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void setfilterliss(List<TruyenDTO> fiteliss) {
        this.list = fiteliss;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TruyenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truyen,parent,false);
        return new TruyenHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenHolder holder, int position) {
            TruyenDTO truyenDTO = list.get(position);
            if (truyenDTO==null){
                return;
            }
        Picasso.get().load(truyenDTO.getImg()).into(holder.imgComic);
            holder.tvName.setText(truyenDTO.getName());
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public  class TruyenHolder extends RecyclerView.ViewHolder{
        private ImageView imgComic;
        private TextView tvName;
        public TruyenHolder(@NonNull View itemView) {
            super(itemView);
            imgComic = itemView.findViewById(R.id.imgComic);
            tvName = itemView.findViewById(R.id.item_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            TruyenDTO comic = list.get(position);
                            listener.onItemClick(comic);
                        }
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(TruyenDTO comic);
    }
}
