package com.cmonzon.popularmovies.data;

import com.cmonzon.popularmovies.data.local.MovieContract;

import android.content.ContentValues;

/**
 * @author cmonzon
 */
public final class MovieMapper {


    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
            return this;
        }

        public Builder title(String title) {
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
            return this;
        }

        public Builder originalTitle(String originalTitle) {
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
            return this;
        }

        public Builder voteAverage(long voteAverage) {
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
            return this;
        }

        public ContentValues build() {
            return values; // TODO defensive copy?
        }
    }
}
