package com.cmonzon.popularmovies.ui.movies;

import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.MovieList;
import com.cmonzon.popularmovies.data.MoviesRepository;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * @author cmonzon
 */
public class MoviesPresenter implements MoviesContract.Presenter {

    @NonNull
    private final MoviesRepository mMoviesRepository;

    @NonNull
    private final MoviesContract.View mView;

    @NonNull
    private CompositeDisposable composite;

    private String mSortType = MoviesSortType.MOST_POPULAR;

    public MoviesPresenter(@NonNull MoviesRepository moviesRepository, @NonNull MoviesContract.View view) {
        mMoviesRepository = checkNotNull(moviesRepository);
        mView = checkNotNull(view);
        composite = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void unSubscribe() {
        composite.clear();
    }

    @Override
    public void loadMovies() {
        mView.showProgressIndicator(true);
        Observable<MovieList> movieListObservable;
        switch (mSortType) {
            case MoviesSortType.MOST_POPULAR:
                movieListObservable = mMoviesRepository.getPopularMovies();
                break;
            case MoviesSortType.TOP_RATED:
                movieListObservable = mMoviesRepository.getTopRatedMovies();
                break;
            default:
                movieListObservable = mMoviesRepository.getFavoriteMovies();
                break;

        }

        composite.clear();
        composite.add(movieListObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<MovieList>() {
                    @Override
                    public void accept(@NonNull MovieList movieList) throws Exception {
                        mView.showProgressIndicator(false);
                        mView.showMovies(new ArrayList<>(movieList.getResults()));
                        if (movieList.getResults().isEmpty()) {
                            mView.showNoDataFound();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showLoadingError();
                    }
                }));
    }

    @Override
    public void openMovieDetail(MovieEntity movie) {
        checkNotNull(movie);
        mView.showMovieDetail(movie);
    }

    @Override
    public void setSortType(String sortType) {
        mSortType = sortType;
    }

    @Override
    public String getSortType() {
        return mSortType;
    }
}
