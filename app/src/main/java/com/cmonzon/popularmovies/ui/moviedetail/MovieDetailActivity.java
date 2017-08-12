package com.cmonzon.popularmovies.ui.moviedetail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cmonzon.popularmovies.Injection;
import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.util.ActivityUtils;

public class MovieDetailActivity extends AppCompatActivity {


    public static final String MOVIE_KEY = "movie_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("");
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();

        if (intent.hasExtra(MOVIE_KEY)) {
            MovieEntity entity = intent.getParcelableExtra(MOVIE_KEY);

            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content);

            if (movieDetailFragment == null) {
                movieDetailFragment = MovieDetailFragment.newInstance();

                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        movieDetailFragment, R.id.content);
            }

            // Create the presenter
            new MovieDetailPresenter(Injection.provideMoviesRepository(this), entity, movieDetailFragment);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
