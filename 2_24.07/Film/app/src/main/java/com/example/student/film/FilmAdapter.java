package com.example.student.film;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.List;


public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    private boolean filled = false;
    private int tmpPosition;

    public FilmAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_film, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(moviesList.get(tmpPosition).getUrl()));
                view.getContext().startActivity(browserIntent);
            }
        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        tmpPosition = position;
        Movie movie = moviesList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.image.setImageDrawable(movie.getImage());
        holder.tvDescription.setText(movie.getDescription());
        holder.ratingBar.setRating(movie.getRating());
        holder.imgHeart.setImageResource(R.mipmap.empty_heart2);
        holder.imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filled) {
                    holder.imgHeart.setImageResource(R.mipmap.filled_heart2);
                    filled = true;
                } else {
                    holder.imgHeart.setImageResource(R.mipmap.empty_heart2);
                    filled = false;
                }
                holder.imgHeart.setMaxWidth(50);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView image;
        public TextView tvDescription;
        public RatingBar ratingBar;
        public ImageView imgHeart;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            image = view.findViewById(R.id.img);
            tvDescription = view.findViewById(R.id.tv_description);
            ratingBar = view.findViewById(R.id.rating);
            imgHeart = view.findViewById(R.id.img_heart);

        }
    }
}