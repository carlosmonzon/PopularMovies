package com.cmonzon.popularmovies.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

}
