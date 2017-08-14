package com.cmonzon.popularmovies.ui.moviedetail;

import com.cmonzon.popularmovies.BR;
import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.VideoEntity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    private ArrayList<VideoEntity> videos;

    private VideoAdapterOnClickHandler callback;

    public VideosAdapter(VideoAdapterOnClickHandler callback) {
        this.callback = callback;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_video, parent, false);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        if (videos != null) {
            return videos.size();
        }
        return 0;
    }

    public void setVideos(ArrayList<VideoEntity> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public VideoViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(VideoEntity video) {
            binding.setVariable(BR.video, video);
            binding.setVariable(BR.videoCallback, callback);
            binding.executePendingBindings();
        }
    }

    public interface VideoAdapterOnClickHandler {

        void onItemClick(VideoEntity video);
    }

}
