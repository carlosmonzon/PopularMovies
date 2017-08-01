package com.cmonzon.popularmovies.data.remote;

import com.cmonzon.popularmovies.data.MovieList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author cmonzon
 */
public interface MoviesApi {

    @GET("movie/popular")
    Observable<MovieList> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieList> getTopRatedMovies();

}
