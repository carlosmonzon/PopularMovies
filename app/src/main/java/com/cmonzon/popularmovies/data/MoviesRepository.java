package com.cmonzon.popularmovies.data;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * @author cmonzon
 */
public class MoviesRepository implements MoviesDataSource {

    private static volatile MoviesRepository INSTANCE;

    @NonNull
    private MoviesDataSource mRemoteDataSource;

    @NonNull
    private MoviesDataSource mLocalDataSource;

    private MoviesRepository(@NonNull MoviesDataSource remoteDataSource, @NonNull MoviesDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = localDataSource;
    }

    public static MoviesRepository getInstance(@NonNull MoviesDataSource remoteDataSource,
            @NonNull MoviesDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<MovieList> getPopularMovies() {
        return mRemoteDataSource.getPopularMovies();
    }

    @Override
    public Observable<MovieList> getTopRatedMovies() {
        return mRemoteDataSource.getTopRatedMovies();
    }

    @Override
    public Observable<MovieList> getFavoriteMovies() {
        return mLocalDataSource.getFavoriteMovies();
    }

    @Override
    public Observable<Boolean> isFavorite(MovieEntity movie) {
        return mLocalDataSource.isFavorite(movie);
    }

    @Override
    public void saveFavoriteMovie(MovieEntity movie) {
        mLocalDataSource.saveFavoriteMovie(movie);
    }

    @Override
    public void deleteFavoriteMovie(MovieEntity movie) {
        mLocalDataSource.deleteFavoriteMovie(movie);
    }
}
