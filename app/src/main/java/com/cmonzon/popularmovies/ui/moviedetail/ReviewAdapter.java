package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.BR;
import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.ReviewEntity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<ReviewEntity> reviews;

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_review, parent, false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        if (reviews != null) {
            return reviews.size();
        }
        return 0;
    }

    public void setReviews(ArrayList<ReviewEntity> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public ReviewViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ReviewEntity review) {
            binding.setVariable(BR.review, review);
            binding.executePendingBindings();
        }
    }
}
