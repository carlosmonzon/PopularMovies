package com.cmonzon.popularmovies.data.remote;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author cmonzon
 */
public class MoviesService {

    private static final String API_URL = "https://api.themoviedb.org/3/";

    private static final String API_KEY_PARAM = "api_key";

    private static final String API_KEY_VALUE = "API_VALUE";

    public static final String API_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private static final long CONNECT_TIMEOUT_SECS = 15;

    private static final long READ_TIMEOUT_SECS = 15;

    private MoviesApi mMoviesApi;

    public MoviesService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECS, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY_VALUE).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mMoviesApi = retrofit.create(MoviesApi.class);
    }

    public MoviesApi getMoviesApi() {
        return mMoviesApi;
    }
}
