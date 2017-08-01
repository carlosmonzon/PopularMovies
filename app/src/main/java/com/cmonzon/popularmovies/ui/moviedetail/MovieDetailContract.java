package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.BasePresenter;
import com.cmonzon.popularmovies.BaseView;
import com.cmonzon.popularmovies.data.MovieEntity;

/**
 * @author cmonzon
 */
public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showMovie(MovieEntity entity);

    }

    interface Presenter extends BasePresenter {

        void openMovie();
    }

}
