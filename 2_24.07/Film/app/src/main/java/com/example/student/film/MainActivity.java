package com.example.student.film;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = findViewById(R.id.rv_films);
        addMovieData();
        FilmAdapter filmAdapter = new FilmAdapter(movieList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recyclerView.setAdapter(filmAdapter);
    }

    private void addMovieData() {
        Movie movie = new Movie(getString(R.string.mad_max), getString(R.string.mad_max_description),
                getResources().getDrawable(R.drawable.mad_max), 4f);
        movie.setUrl(getString(R.string.mad_max_url));
        movieList.add(movie);

        movie = new Movie(getString(R.string.transformers_4), getString(R.string.transformers_description),
                getResources().getDrawable(R.drawable.transformers_4), 5f);
        movie.setUrl(getString(R.string.transformers_4_url));
        movieList.add(movie);

        movie = new Movie(getString(R.string.titanic), getString(R.string.titanic_description),
                getResources().getDrawable(R.drawable.titanic_0), 4.5f);
        movie.setUrl(getString(R.string.titanic_url));
        movieList.add(movie);

        movie = new Movie(getString(R.string.fast_and_furious_8), getString(R.string.fast_and_furious_8_description),
                getResources().getDrawable(R.drawable.fate_of_the_furious), 4.5f);
        movie.setUrl(getString(R.string.fast_furious_url));
        movieList.add(movie);
    }
}