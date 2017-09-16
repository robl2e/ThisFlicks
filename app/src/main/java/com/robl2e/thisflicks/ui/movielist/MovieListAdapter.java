package com.robl2e.thisflicks.ui.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robl2e.thisflicks.R;

import java.util.List;


/**
 * Created by robl2e on 9/14/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemView);
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

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.text_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.text_description);
        }

        public void bindView(MovieItemViewModel viewModel) {
            titleTextView.setText(viewModel.getName());
            descriptionTextView.setText(viewModel.getDescription());
        }
    }

}
