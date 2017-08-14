package com.cmonzon.popularmovies.data;

import io.reactivex.Observable;

/**
 * @author cmonzon
 */
public interface MoviesDataSource {

    Observable<MovieList> getPopularMovies();

    Observable<MovieList> getTopRatedMovies();

    Observable<MovieList> getFavoriteMovies();

    Observable<Boolean> isFavorite(MovieEntity movie);

    Observable<VideoList> getMovieVideos(int movieId);

    Observable<ReviewList> getMovieReviews(int movieId);

    void saveFavoriteMovie(MovieEntity movie);

    void deleteFavoriteMovie(MovieEntity movie);


}
