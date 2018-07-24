package com.example.student.film;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Movie> movieList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_films);
        FilmAdapter filmAdapter = new FilmAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(filmAdapter);
        prepareMovieData();
        filmAdapter.notifyDataSetChanged();
    }

    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("Transformers 4", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);

        movieList.add(movie);
        movie = new Movie("Titanic", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("Transformers 2", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("Fast and furius 8", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("aaaaaaaa", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("Mad Max: Fury", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("Fury Road", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);

        movie = new Movie("Max", "DESCRIPtion",
                getResources().getDrawable(R.drawable.ic_launcher_background), 5);
        movieList.add(movie);
    }
}
