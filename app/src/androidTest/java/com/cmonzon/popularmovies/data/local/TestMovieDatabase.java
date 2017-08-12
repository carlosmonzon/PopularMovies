package com.cmonzon.popularmovies.data.local;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.runner.AndroidJUnit4;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author cmonzon
 */
@RunWith(AndroidJUnit4.class)
public class TestMovieDatabase {

    private final Context context = getInstrumentation().getTargetContext();

    private static final String packageName = "com.cmonzon.popularmovies";

    private static final String movieDbHelperName = ".MovieDbHelper";

    private static final String localDataPackageName = packageName + ".data.local";

    private SQLiteDatabase database;

    @Before
    public void before() {
        try {
            Class movieDbHelperClass = Class.forName(localDataPackageName + movieDbHelperName);

            Constructor movieDbHelperConstructor = movieDbHelperClass.getConstructor(Context.class);
            SQLiteOpenHelper dbHelper = (SQLiteOpenHelper) movieDbHelperConstructor.newInstance(context);
            context.deleteDatabase(MovieDbHelper.DATABASE_NAME);
            Method getWritableDatabase = SQLiteOpenHelper.class.getDeclaredMethod("getWritableDatabase");
            database = (SQLiteDatabase) getWritableDatabase.invoke(dbHelper);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (InstantiationException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInsertSingleRecordIntoMovieTable() {
        ContentValues contentValues = createTestMovieContentValues();
        long movieRowId = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);

           /* If the insert fails, database.insert returns -1 */
        int valueOfIdIfInsertFails = -1;
        String insertFailed = "Unable to insert into the database";
        assertNotSame(insertFailed, valueOfIdIfInsertFails, movieRowId);

        Cursor movieCursor = database.query(
                MovieContract.MovieEntry.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from movie query";
        assertTrue(emptyQueryError,
                movieCursor.moveToFirst());

        /* Verify that the returned results match the expected results */
        String expectedMovieDidntMatchActual =
                "Expected movie values didn't match actual values.";
        validateCurrentRecord(expectedMovieDidntMatchActual, movieCursor, contentValues);

        assertFalse("Error: More than one record returned from movie query",
                movieCursor.moveToNext());

        movieCursor.close();
    }

    @Test
    public void testNullColumnConstraints() {
        ContentValues testValues = createTestMovieContentValues();
        testValues.putNull(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        long shouldFailRowId = database.insert(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                testValues);
        assertEquals(-1, shouldFailRowId);
    }

    @Test
    public void testDuplicatedMovieIdShouldReplace() {
        ContentValues contentValues = createTestMovieContentValues();
        //Insert movie with originalMovieId
        database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);

        //update movie vote average with same movieId
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, 8.4);
        //insert updated movie
        database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);

        Cursor movieCursor = database
                .query(MovieContract.MovieEntry.TABLE_NAME, new String[]{MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE}, null,
                        null, null, null, null);
        String recordWithNewIdNotFound = "New record did not overwrite the previous record for the same movie id";
        assertTrue(recordWithNewIdNotFound, movieCursor.getCount() == 1);

        movieCursor.close();
    }

    private static ContentValues createTestMovieContentValues() {

        ContentValues testMovieValues = new ContentValues();

        testMovieValues.put(MovieContract.MovieEntry._ID, 1);
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, 315635);
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, "Original Title");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Overview");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/poster_path.jpg");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "1999-10-15");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Title");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, 8.300000000000001);

        return testMovieValues;
    }

    /**
     * This method iterates through a set of expected values and makes various assertions that
     * will pass if our app is functioning properly.
     *
     * @param error          Message when an error occurs
     * @param valueCursor    The Cursor containing the actual values received from an arbitrary query
     * @param expectedValues The values we expect to receive in valueCursor
     */
    private static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);

            /* Test to see if the column is contained within the cursor */
            String columnNotFoundError = "Column '" + columnName + "' not found. " + error;
            assertFalse(columnNotFoundError, index == -1);

            /* Test to see if the expected value equals the actual value (from the Cursor) */
            String expectedValue = entry.getValue().toString();
            String actualValue = valueCursor.getString(index);

            String valuesDontMatchError = "Actual value '" + actualValue
                    + "' did not match the expected value '" + expectedValue + "'. "
                    + error;

            Assert.assertEquals(valuesDontMatchError,
                    expectedValue,
                    actualValue);
        }
    }
}
