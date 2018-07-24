package com.example.student.film;

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

    public FilmAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_film, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.image.setImageDrawable(movie.getImage());
        holder.tvDescription.setText(movie.getDescription());
        holder.ratingBar.setNumStars(movie.getRating());
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


        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            image = view.findViewById(R.id.img);
            ratingBar = view.findViewById(R.id.rating);
            tvDescription = view.findViewById(R.id.tv_description);
        }
    }

}