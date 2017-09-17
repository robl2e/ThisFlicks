package com.robl2e.thisflicks.ui.movielist;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robl2e.thisflicks.R;
import com.robl2e.thisflicks.ui.utils.UIResourceUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by robl2e on 9/14/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private static final int VIEW_TYPE_PORTRAIT = Configuration.ORIENTATION_PORTRAIT;
    private static final int VIEW_TYPE_LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;

    private Context context;
    private LayoutInflater inflater;
    private List<MovieItemViewModel> movieItemViewModels;

    public MovieListAdapter(Context context, List<MovieItemViewModel> movieItemViewModels) {
        this.context = context;
        this.movieItemViewModels = movieItemViewModels;
        this.inflater = LayoutInflater.from(context);
    }

    public void setItems(List<MovieItemViewModel> movieItemViewModels) {
        this.movieItemViewModels = movieItemViewModels;
    }

    @Override
    public int getItemViewType(int position) {
        if (movieItemViewModels == null) {
            int orientation = UIResourceUtils.getScreenOrientation(context);
            return getViewTypeFrom(orientation);
        }

        try {
            MovieItemViewModel viewModel = movieItemViewModels.get(position);
            if (viewModel == null) {
                int orientation = UIResourceUtils.getScreenOrientation(context);
                return getViewTypeFrom(orientation);
            }

            return getViewTypeFrom(viewModel.getOrientation());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return VIEW_TYPE_PORTRAIT;
    }

    private int getViewTypeFrom(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return VIEW_TYPE_LANDSCAPE;
        } else {
            return VIEW_TYPE_PORTRAIT;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieItemViewModel viewModel = this.movieItemViewModels.get(position);
        holder.bindView(viewModel);
    }

    @Override
    public int getItemCount() {
        return movieItemViewModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView posterImageView;
        private int viewType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            titleTextView = (TextView) itemView.findViewById(R.id.text_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.text_description);
            posterImageView = (ImageView) itemView.findViewById(R.id.image_poster);
        }

        public void bindView(MovieItemViewModel viewModel) {
            titleTextView.setText(viewModel.getName());
            descriptionTextView.setText(viewModel.getDescription());

            displayPosterView(viewModel);
        }

        private void displayPosterView(MovieItemViewModel viewModel) {
            String imageUrl;
            if (viewType == VIEW_TYPE_LANDSCAPE) {
                imageUrl = viewModel.getImageBackdropUrl();
            } else {
                imageUrl = viewModel.getImagePosterUrl();
            }
            Picasso.with(posterImageView.getContext())
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(posterImageView);
        }
    }

}
