package com.cmonzon.popularmovies.data.remote;

import com.cmonzon.popularmovies.data.MovieList;
import com.cmonzon.popularmovies.data.ReviewList;
import com.cmonzon.popularmovies.data.VideoList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author cmonzon
 */
public interface MoviesApi {

    @GET("movie/popular")
    Observable<MovieList> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieList> getTopRatedMovies();

    @GET("movie/{id}/videos")
    Observable<VideoList> getMovieVideos(@Path("id") int movieId);

    @GET("movie/{id}/reviews")
    Observable<ReviewList> getMovieReviews(@Path("id") int movieId);

}
