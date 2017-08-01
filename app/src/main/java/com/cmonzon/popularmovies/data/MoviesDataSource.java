package com.cmonzon.popularmovies.data;

import io.reactivex.Observable;

/**
 * @author cmonzon
 */
public interface MoviesDataSource {

    Observable<MovieList> getPopularMovies();

    Observable<MovieList> getTopRatedMovies();

}
