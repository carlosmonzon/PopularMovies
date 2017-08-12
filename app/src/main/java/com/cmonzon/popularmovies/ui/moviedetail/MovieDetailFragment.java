package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.binding.BindingAdapters;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.databinding.FragmentMovieDetailsBinding;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author cmonzon
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

    private MovieDetailContract.Presenter mPresenter;

    FragmentMovieDetailsBinding binding;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        binding.setDetailPresenter(mPresenter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.openMovie();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMovie(MovieEntity movie) {
        binding.tvOverview.setText(movie.getOverview());
        binding.tvMovieTitle.setText(movie.getOriginalTitle());
        binding.tvMovieReleaseDate.setText(movie.getReleaseDate());
        binding.tvMovieRating.setText(String.valueOf(movie.getVoteAverage()));
        BindingAdapters.setImageUrl(binding.ivPoster, movie.getPosterPath());
    }

    @Override
    public void showFavorite(boolean isFavorite) {
        Drawable drawable = ContextCompat
                .getDrawable(getContext(), isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        int colorText = ContextCompat
                .getColor(getContext(), isFavorite ? R.color.colorAccent : android.R.color.primary_text_dark);
        String text = getString(isFavorite ? R.string.action_remove_from_favorites : R.string.action_add_to_favorites);
        binding.addToFavorites.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        binding.addToFavorites.setText(text);
        binding.addToFavorites.setTextColor(colorText);
    }
}
