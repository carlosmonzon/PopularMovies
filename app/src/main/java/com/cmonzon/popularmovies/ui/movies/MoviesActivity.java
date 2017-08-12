package com.cmonzon.popularmovies.ui.movies;

import com.cmonzon.popularmovies.Injection;
import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.util.ActivityUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author cmonzon
 */
public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        MoviesFragment moviesFragment =
                (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (moviesFragment == null) {
            // Create the fragment
            moviesFragment = MoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), moviesFragment, R.id.content);
        }

        // Create the presenter
        new MoviesPresenter(Injection.provideMoviesRepository(this), moviesFragment);
    }
}
