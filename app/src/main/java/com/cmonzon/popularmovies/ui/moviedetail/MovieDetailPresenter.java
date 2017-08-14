package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.MoviesRepository;
import com.cmonzon.popularmovies.data.ReviewEntity;
import com.cmonzon.popularmovies.data.ReviewList;
import com.cmonzon.popularmovies.data.VideoEntity;
import com.cmonzon.popularmovies.data.VideoList;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void loadMovieVideos() {
        composite.add(mMoviesRepository.getMovieVideos(mMovie.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<VideoList>() {
                    @Override
                    public void accept(@NonNull VideoList videoList) throws Exception {
                        List<VideoEntity> videoEntityList = videoList.getResults();
                        mView.showVideos(new ArrayList<>(videoEntityList));
                        if (videoEntityList.isEmpty()) {
                            mView.showVideosEmpty();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showVideosError();
                    }
                }));

    }

    @Override
    public void loadMovieReviews() {
        composite.add(mMoviesRepository.getMovieReviews(mMovie.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<ReviewList>() {
                    @Override
                    public void accept(@NonNull ReviewList reviewList) throws Exception {
                        List<ReviewEntity> reviewEntityList = reviewList.getResults();
                        mView.showReviews(new ArrayList<>(reviewEntityList));
                        if (reviewEntityList.isEmpty()) {
                            mView.showReviewsEmpty();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showReviewsError();
                    }
                }));
    }
}
