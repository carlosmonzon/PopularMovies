package com.cmonzon.popularmovies.data.local;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.VisibleForTesting;

/**
 * @author cmonzon
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.cmonzon.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    @VisibleForTesting
    public static final class MovieEntry implements BaseColumns {

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.popularmovies.movie";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.popularmovies.movie";

        /* The base CONTENT_URI used to query the Movie table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "Movie";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_VOTE_AVERAGE = "average";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_RELEASE_DATE = "release_date";

    }

}
