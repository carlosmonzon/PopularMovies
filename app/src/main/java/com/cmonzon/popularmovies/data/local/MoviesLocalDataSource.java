package com.cmonzon.popularmovies.data.local;

import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.MovieList;
import com.cmonzon.popularmovies.data.MoviesDataSource;
import com.squareup.sqlbrite2.BriteContentResolver;
import com.squareup.sqlbrite2.SqlBrite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * @author cmonzon
 */
public class MoviesLocalDataSource implements MoviesDataSource {

    public static volatile MoviesLocalDataSource INSTANCE;

    @NonNull
    private final BriteContentResolver briteResolver;

    private ContentResolver resolver;

    @NonNull
    private static final Function<Cursor, MovieEntity> movieMapperFunction = new Function<Cursor, MovieEntity>() {
        @Override
        public MovieEntity apply(@NonNull Cursor cursor) throws Exception {
            return getMovie(cursor);
        }
    };


    @NonNull
    private static final Function<SqlBrite.Query, Boolean> isFavoriteMapperFunction
            = new Function<SqlBrite.Query, Boolean>() {
        @Override
        public Boolean apply(@NonNull SqlBrite.Query query) throws Exception {
            Cursor cursor = query.run();
            return cursor != null && cursor.getCount() > 0;
        }
    };

    private MoviesLocalDataSource(Context context) {
        resolver = context.getContentResolver();
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        briteResolver = sqlBrite.wrapContentProvider(resolver, Schedulers.io());
    }

    public static MoviesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<MovieList> getPopularMovies() {
        //not required
        return null;
    }

    @Override
    public Observable<MovieList> getTopRatedMovies() {
        //not required
        return null;
    }

    @Override
    public Observable<MovieList> getFavoriteMovies() {
        String[] projection = {
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
                MovieContract.MovieEntry.COLUMN_OVERVIEW,
                MovieContract.MovieEntry.COLUMN_POSTER_PATH,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_TITLE,
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE
        };
        return briteResolver.createQuery(MovieContract.MovieEntry.CONTENT_URI, projection, null, null, null, true)
                .mapToList(movieMapperFunction).map(new Function<List<MovieEntity>, MovieList>() {
                    @Override
                    public MovieList apply(@NonNull List<MovieEntity> movieEntities)
                            throws Exception {
                        MovieList movieList = new MovieList();
                        movieList.setResults(movieEntities);
                        return movieList;
                    }
                });
    }

    @Override
    public Observable<Boolean> isFavorite(MovieEntity movie) {
        String[] projection = {MovieContract.MovieEntry.COLUMN_MOVIE_ID};
        return briteResolver
                .createQuery(ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, movie.getId()), projection,
                        null, null, null, true).map(isFavoriteMapperFunction);
    }

    @NonNull
    private static MovieEntity getMovie(@NonNull Cursor c) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(c.getInt(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
        movieEntity.setTitle(c.getString(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_TITLE)));
        movieEntity.setOriginalTitle(c.getString(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movieEntity.setOverview(c.getString(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
        movieEntity.setPosterPath(c.getString(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
        movieEntity.setVoteAverage(c.getDouble(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
        movieEntity.setReleaseDate(c.getString(c.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
        return movieEntity;
    }

    @Override
    public void saveFavoriteMovie(MovieEntity movie) {
        checkNotNull(movie);
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        resolver.insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    @Override
    public void deleteFavoriteMovie(MovieEntity movie) {
        String selection = MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(movie.getId())};
        resolver.delete(MovieContract.MovieEntry.CONTENT_URI, selection, selectionArgs);
    }
}
