package com.cmonzon.popularmovies.data.remote;

import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.MovieList;
import com.cmonzon.popularmovies.data.MoviesDataSource;
import com.cmonzon.popularmovies.data.ReviewList;
import com.cmonzon.popularmovies.data.VideoList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author cmonzon
 */
public class MoviesRemoteDataSource implements MoviesDataSource {

    private static volatile MoviesRemoteDataSource INSTANCE;

    private MoviesApi mMoviesApi;

    public MoviesRemoteDataSource(MoviesApi api) {
        mMoviesApi = api;
    }

    public static MoviesRemoteDataSource getInstance(MoviesApi api) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRemoteDataSource(api);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<MovieList> getPopularMovies() {
        return mMoviesApi.getPopularMovies().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MovieList> getTopRatedMovies() {
        return mMoviesApi.getTopRatedMovies().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MovieList> getFavoriteMovies() {
        //not implemented
        return null;
    }

    @Override
    public Observable<Boolean> isFavorite(MovieEntity movie) {
        return null;
    }

    @Override
    public Observable<VideoList> getMovieVideos(int movieId) {
        return mMoviesApi.getMovieVideos(movieId).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ReviewList> getMovieReviews(int movieId) {
        return mMoviesApi.getMovieReviews(movieId).subscribeOn(Schedulers.io());
    }

    @Override
    public void saveFavoriteMovie(MovieEntity movie) {
        //not implemented
    }

    @Override
    public void deleteFavoriteMovie(MovieEntity movie) {
        //not implemented
    }
}
