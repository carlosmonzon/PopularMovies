package com.cmonzon.popularmovies;

import com.cmonzon.popularmovies.data.MoviesRepository;
import com.cmonzon.popularmovies.data.local.MoviesLocalDataSource;
import com.cmonzon.popularmovies.data.remote.MoviesRemoteDataSource;
import com.cmonzon.popularmovies.data.remote.MoviesService;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Enables injections for {@link MoviesRepository} at compile time
 *
 * @author cmonzon
 */
public class Injection {

    public static MoviesRepository provideMoviesRepository(@NonNull Context context) {
        return MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(new MoviesService().getMoviesApi()),
                MoviesLocalDataSource.getInstance(context));
    }

}
