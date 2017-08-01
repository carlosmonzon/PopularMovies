package com.cmonzon.popularmovies.ui.movies;

import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.MovieEntity;
import com.cmonzon.popularmovies.data.remote.MoviesService;
import com.squareup.picasso.Picasso;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cmonzon
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private ArrayList<MovieEntity> movies;

    private MoviesAdapterOnClickHandler mListener;

    public MoviesAdapter(MoviesAdapterOnClickHandler listener) {
        mListener = listener;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() instanceof MovieEntity) {
                    mListener.onItemClick((MovieEntity) v.getTag());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        }
        return 0;
    }

    public void setMovies(ArrayList<MovieEntity> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MovieEntity movie) {
            itemView.setTag(movie);
            Picasso.with(itemView.getContext()).load(MoviesService.API_IMAGE_BASE_URL + movie.getPosterPath())
                    .placeholder(R.color.light_grey).error(R.color.light_grey).into(poster);
        }
    }

    public interface MoviesAdapterOnClickHandler {

        void onItemClick(MovieEntity movie);
    }

}
