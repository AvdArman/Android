package com.example.arman.userlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>{

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new ImageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List<String> list = Data.userList.get(position).getImgUrlList();
        if (position < list.size()) {
            String imageUrl = list.get(position);
            Picasso.get().load(imageUrl).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return Data.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.single_image);
        }
    }
}
