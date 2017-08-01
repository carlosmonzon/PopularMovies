package com.cmonzon.popularmovies;

import com.cmonzon.popularmovies.data.MoviesRepository;
import com.cmonzon.popularmovies.data.remote.MoviesRemoteDataSource;
import com.cmonzon.popularmovies.data.remote.MoviesService;

/**
 * Enables injections for {@link MoviesRepository} at compile time
 *
 * @author cmonzon
 */
public class Injection {

    public static MoviesRepository provideMoviesRepository() {
        return MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(new MoviesService().getMoviesApi()));
    }

}
