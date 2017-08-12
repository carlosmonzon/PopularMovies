package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.MoviesRepository;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * @author cmonzon
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    @NonNull
    private MovieEntity mMovie;

    @NonNull
    private MovieDetailContract.View mView;

    @NonNull
    private final MoviesRepository mMoviesRepository;

    @NonNull
    private CompositeDisposable composite;

    private boolean isFavorite;

    public MovieDetailPresenter(@NonNull MoviesRepository moviesRepository, @NonNull MovieEntity movie,
            @NonNull MovieDetailContract.View view) {
        mMoviesRepository = checkNotNull(moviesRepository);
        mMovie = checkNotNull(movie);
        mView = checkNotNull(view);
        composite = new CompositeDisposable();
        mView.setPresenter(this);
    }


    @Override
    public void unSubscribe() {
        composite.clear();
    }

    @Override
    public void openMovie() {
        mView.showMovie(mMovie);
        composite.add(mMoviesRepository.isFavorite(mMovie).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        isFavorite = aBoolean;
                        mView.showFavorite(isFavorite);
                    }
                }));
    }

    @Override
    public void onFavoriteClick() {
        if (isFavorite) {
            mMoviesRepository.deleteFavoriteMovie(mMovie);
        } else {
            mMoviesRepository.saveFavoriteMovie(mMovie);
        }
    }
}
