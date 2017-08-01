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

    private MoviesRepository(@NonNull MoviesDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static MoviesRepository getInstance(@NonNull MoviesDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository(remoteDataSource);
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
}
