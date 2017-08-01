package com.cmonzon.popularmovies.ui.movies;

import com.cmonzon.popularmovies.BasePresenter;
import com.cmonzon.popularmovies.BaseView;
import com.cmonzon.popularmovies.data.MovieEntity;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showMovies(ArrayList<MovieEntity> movies);

        void showProgressIndicator(boolean isVisible);

        void showLoadingError();

        void showMovieDetail(MovieEntity movie);

    }

    interface Presenter extends BasePresenter {

        void loadMovies();

        void openMovieDetail(MovieEntity movie);

        void setSortType(MoviesSortType sortType);

        MoviesSortType getSortType();
    }

}
