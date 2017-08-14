package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.BasePresenter;
import com.cmonzon.popularmovies.BaseView;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.ReviewEntity;
import com.cmonzon.popularmovies.data.VideoEntity;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showMovie(MovieEntity entity);

        void showFavorite(boolean isFavorite);

        void showVideos(ArrayList<VideoEntity> videos);

        void showReviews(ArrayList<ReviewEntity> reviews);

        void showVideosError();

        void showVideosEmpty();

        void showReviewsError();

        void showReviewsEmpty();
    }

    interface Presenter extends BasePresenter {

        void openMovie();

        void onFavoriteClick();

        void loadMovieVideos();

        void loadMovieReviews();
    }

}
