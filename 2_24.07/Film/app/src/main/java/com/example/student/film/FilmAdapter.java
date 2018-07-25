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
    private View tmpView;

    public FilmAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_film, parent, false);
        tmpView = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        tmpPosition = position;
        Movie movie = moviesList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.image.setImageDrawable(movie.getImage());
        holder.tvDescription.setText(movie.getDescription());
        holder.ratingBar.setRating(movie.getRating());
        holder.btnHeart.setImageResource(R.drawable.ic_black_heart);
        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHeart(holder);
            }
        });
        tmpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(moviesList.get(position).getUrl()));
                view.getContext().startActivity(browserIntent);
            }
        });
    }

    private void setHeart(MyViewHolder holder) {
        if (!filled) {
            holder.btnHeart.setImageResource(R.drawable.ic_red_heart);
            filled = true;
        } else {
            holder.btnHeart.setImageResource(R.drawable.ic_black_heart);
            filled = false;
        }
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView image;
        private TextView tvDescription;
        private RatingBar ratingBar;
        private ImageView btnHeart;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            image = view.findViewById(R.id.img);
            tvDescription = view.findViewById(R.id.tv_description);
            ratingBar = view.findViewById(R.id.rating);
            btnHeart = view.findViewById(R.id.btn_heart);
        }
    }
}