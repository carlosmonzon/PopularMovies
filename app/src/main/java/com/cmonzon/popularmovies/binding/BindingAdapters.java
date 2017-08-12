package com.cmonzon.popularmovies.binding;

import com.cmonzon.popularmovies.R;
import com.cmonzon.popularmovies.data.remote.MoviesService;
import com.squareup.picasso.Picasso;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * @author cmonzon
 */
public final class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        Picasso.with(view.getContext()).load(MoviesService.API_IMAGE_BASE_URL + url)
                .placeholder(R.color.light_grey).error(R.color.light_grey).into(view);
    }

}
