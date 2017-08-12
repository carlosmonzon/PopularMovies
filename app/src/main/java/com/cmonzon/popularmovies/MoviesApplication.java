package com.cmonzon.popularmovies;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import android.app.Application;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author cmonzon
 */
public class MoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 5 * 1024 * 1024);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().cache(cache);
        Picasso.Builder picassoBuilder = new Picasso.Builder(getApplicationContext());
        picassoBuilder.downloader(new OkHttp3Downloader(clientBuilder.build()));
        Picasso picasso = picassoBuilder.build();
        Picasso.setSingletonInstance(picasso);
    }
}
