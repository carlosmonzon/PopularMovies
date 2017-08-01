package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.data.MovieEntity;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * @author cmonzon
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    @NonNull
    private MovieEntity mMovie;

    @NonNull
    private MovieDetailContract.View mView;

    public MovieDetailPresenter(@NonNull MovieEntity movie, @NonNull MovieDetailContract.View view) {
        mMovie = checkNotNull(movie);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }


    @Override
    public void unSubscribe() {
        //no implementation
    }

    @Override
    public void openMovie() {
        mView.showMovie(mMovie);
    }
}
