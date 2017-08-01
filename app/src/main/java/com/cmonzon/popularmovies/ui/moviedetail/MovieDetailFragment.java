package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cmonzon
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

    private MovieDetailContract.Presenter mPresenter;

    @BindView(R.id.tv_movie_title)
    TextView tvTitle;

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @BindView(R.id.tv_movie_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.tv_movie_rating)
    TextView tvRating;

    @BindView(R.id.iv_poster)
    ImageView ivPoster;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.openMovie();
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMovie(MovieEntity movie) {
        tvOverview.setText(movie.getOverview());
        tvTitle.setText(movie.getOriginalTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvRating.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                .placeholder(R.color.light_grey).error(R.color.light_grey).into(ivPoster);

    }
}
