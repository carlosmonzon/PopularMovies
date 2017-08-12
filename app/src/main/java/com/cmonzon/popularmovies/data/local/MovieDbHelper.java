package com.cmonzon.popularmovies.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author cmonzon
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String REAL_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String NOT_NULL = " NOT NULL";

    private static final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                    MovieContract.MovieEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    MovieContract.MovieEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                    MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + REAL_TYPE + COMMA_SEP +
                    MovieContract.MovieEntry.COLUMN_POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                    MovieContract.MovieEntry.COLUMN_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                    MovieContract.MovieEntry.COLUMN_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                    "UNIQUE (" + MovieContract.MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required
    }
}
