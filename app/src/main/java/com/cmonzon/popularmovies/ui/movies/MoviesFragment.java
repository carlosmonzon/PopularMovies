package com.cmonzon.popularmovies.ui.movies;

import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.ui.moviedetail.MovieDetailActivity;
import com.cmonzon.popularmovies.util.ActivityUtils;
import com.cmonzon.popularmovies.util.ItemOffsetDecoration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cmonzon
 */
public class MoviesFragment extends Fragment implements MoviesContract.View, MoviesAdapter.MoviesAdapterOnClickHandler {

    public static final String MOVIES_KEY = "movies";

    public static final String SORT_BY = "sort_by";

    private MoviesContract.Presenter mPresenter;

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_error_message_display)
    TextView errorMessageDisplay;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar loadingIndicator;

    private MoviesAdapter mMoviesAdapter;

    private ArrayList<MovieEntity> movies;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), ActivityUtils.numberOfColumns(getActivity()));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.grid_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            //restore saved instance
            String sortBy = savedInstanceState.getString(SORT_BY);
            mPresenter.setSortType(sortBy);
            movies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
        }
        //if there are movies show them, if not request
        if (movies != null) {
            showMovies(movies);
        } else {
            mPresenter.loadMovies();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies, menu);
        if (mPresenter.getSortType().equals(MoviesSortType.MOST_POPULAR)) {
            MenuItem item = menu.findItem(R.id.action_sort_by_most_popular);
            item.setChecked(true);
        } else if (mPresenter.getSortType().equals(MoviesSortType.TOP_RATED)) {
            MenuItem item = menu.findItem(R.id.action_sort_by_top_rated);
            item.setChecked(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SORT_BY, mPresenter.getSortType());
        if (movies != null) {
            outState.putParcelableArrayList(MOVIES_KEY, movies);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_by_most_popular) {
            if (!item.isChecked()) {
                item.setChecked(true);
                mPresenter.setSortType(MoviesSortType.MOST_POPULAR);
                mPresenter.loadMovies();
            }
            return true;
        } else if (id == R.id.action_sort_by_top_rated) {
            if (!item.isChecked()) {
                item.setChecked(true);
                mPresenter.setSortType(MoviesSortType.TOP_RATED);
                mPresenter.loadMovies();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMovies(ArrayList<MovieEntity> movies) {
        this.movies = movies;
        mRecyclerView.setVisibility(View.VISIBLE);
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        mMoviesAdapter.setMovies(movies);
    }

    @Override
    public void showProgressIndicator(boolean isVisible) {
        loadingIndicator.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showLoadingError() {
        errorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMovieDetail(MovieEntity movie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_KEY, movie);
        startActivity(intent);
    }

    @Override
    public void onItemClick(MovieEntity movie) {
        mPresenter.openMovieDetail(movie);
    }
}
