package com.cmonzon.popularmovies.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

/**
 * @author cmonzon
 */
public final class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,
            int contentId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(contentId, fragment);
        transaction.commit();
    }


    //helper method to calculate the number of columns according to the poster size
    public static int numberOfColumns(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) {
            return 2;
        }
        return nColumns;
    }
}
