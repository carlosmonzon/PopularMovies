package com.cmonzon.popularmovies.ui.movies;

import com.cmonzon.popularmovies.BR;
import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.MovieEntity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;


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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_movie, parent, false);

        return new ViewHolder(binding);
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

        ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MovieEntity movie) {
            binding.setVariable(BR.movie, movie);
            binding.setVariable(BR.callback, mListener);
            binding.executePendingBindings();
        }
    }

    public interface MoviesAdapterOnClickHandler {

        void onItemClick(MovieEntity movie);
    }

}
