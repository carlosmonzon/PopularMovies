package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.binding.BindingAdapters;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.ReviewEntity;
import com.cmonzon.popularmovies.data.VideoEntity;
import com.cmonzon.popularmovies.databinding.FragmentMovieDetailsBinding;
import com.cmonzon.popularmovies.util.ItemOffsetDecoration;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * @author cmonzon
 */
public class MovieDetailFragment extends Fragment
        implements MovieDetailContract.View, VideosAdapter.VideoAdapterOnClickHandler {

    public static final String VIDEOS_KEY = "videos";

    public static final String REVIEWS_KEY = "reviews";

    private MovieDetailContract.Presenter mPresenter;

    FragmentMovieDetailsBinding binding;

    private ArrayList<VideoEntity> videos;

    private ArrayList<ReviewEntity> reviews;

    private VideosAdapter videosAdapter;

    private ReviewAdapter reviewsAdapter;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        binding.setDetailPresenter(mPresenter);
        //videos adapter
        LinearLayoutManager horizontalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvVideos.setLayoutManager(horizontalManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.grid_offset);
        binding.rvVideos.addItemDecoration(itemDecoration);
        videosAdapter = new VideosAdapter(this);
        binding.rvVideos.setAdapter(videosAdapter);

        //reviews adapter
        LinearLayoutManager verticalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.reviewsSection.rvReviews.setLayoutManager(verticalManager);
        reviewsAdapter = new ReviewAdapter();
        binding.reviewsSection.rvReviews.setAdapter(reviewsAdapter);
        binding.reviewsSection.rvReviews.setNestedScrollingEnabled(false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter.openMovie();
        if (savedInstanceState != null) {
            //restore saved instance
            videos = savedInstanceState.getParcelableArrayList(VIDEOS_KEY);
            reviews = savedInstanceState.getParcelableArrayList(REVIEWS_KEY);
        }
        //if there are videos show them, if not request
        if (videos != null) {
            if (videos.isEmpty()) {
                showVideosEmpty();
            } else {
                showVideos(videos);
            }
        } else {
            mPresenter.loadMovieVideos();
        }

        //if there are reviews show tem, if not request
        if (reviews != null) {
            if (reviews.isEmpty()) {
                showReviewsEmpty();
            } else {
                showReviews(reviews);
            }
        } else {
            mPresenter.loadMovieReviews();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (videos != null) {
            outState.putParcelableArrayList(VIDEOS_KEY, videos);
        }
        if (reviews != null) {
            outState.putParcelableArrayList(REVIEWS_KEY, reviews);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_detail_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (videos != null && videos.size() > 0) {
            menu.findItem(R.id.action_share).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = createShareTrailerIntent();
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void showVideos(ArrayList<VideoEntity> videos) {
        this.videos = videos;
        binding.progressBar.setVisibility(View.GONE);
        videosAdapter.setVideos(videos);
        ActivityCompat.invalidateOptionsMenu(getActivity());
    }

    @Override
    public void showReviews(ArrayList<ReviewEntity> reviews) {
        this.reviews = reviews;
        binding.reviewsSection.pbReviews.setVisibility(View.GONE);
        reviewsAdapter.setReviews(reviews);
    }

    @Override
    public void showVideosError() {
        binding.videosErrorMessage.setText(R.string.error_message);
        binding.videosErrorMessage.setVisibility(View.VISIBLE);
        hideTrailerSection();
    }

    @Override
    public void showVideosEmpty() {
        binding.videosErrorMessage.setText(R.string.empty_videos_message);
        binding.videosErrorMessage.setVisibility(View.VISIBLE);
        hideTrailerSection();
    }

    @Override
    public void showReviewsError() {
        binding.reviewsSection.reviewsErrorMessage.setText(R.string.error_message);
        binding.reviewsSection.reviewsErrorMessage.setVisibility(View.VISIBLE);
        hideReviewSection();
    }

    @Override
    public void showReviewsEmpty() {
        binding.reviewsSection.reviewsErrorMessage.setText(R.string.empty_reviews_message);
        binding.reviewsSection.reviewsErrorMessage.setVisibility(View.VISIBLE);
        hideReviewSection();
    }

    private void hideTrailerSection() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.rvVideos.setVisibility(View.GONE);
    }

    private void hideReviewSection() {
        binding.reviewsSection.pbReviews.setVisibility(View.INVISIBLE);
        binding.reviewsSection.rvReviews.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(VideoEntity video) {
        String videoUrl = String.format(getResources().getString(R.string.youtube_video_url), video.getKey());
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
    }

    private Intent createShareTrailerIntent() {
        String videoUrl = String.format(getResources().getString(R.string.youtube_video_url), videos.get(0).getKey());
        String shareText = String.format(getResources().getString(R.string.message_trailer_share), videos.get(0).getName(),
                binding.tvMovieTitle.getText(), videoUrl);
        Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText(shareText)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }
}
