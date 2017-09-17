package com.robl2e.thisflicks.ui.moviedetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robl2e.thisflicks.R;
import com.robl2e.thisflicks.ui.movielist.MovieItemViewModel;
import com.robl2e.thisflicks.util.JsonUtils;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_VIEW_MODEL = "EXTRA_MOVIE_VIEW_MODEL";

    private Toolbar toolbar;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView posterImageView;
    private RatingBar ratingBarView;

    private MovieItemViewModel movieItemViewModel;

    public static void start(Activity activity, MovieItemViewModel viewModel) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_VIEW_MODEL, JsonUtils.toJson(viewModel));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setupView();
        setSupportActionBar(toolbar);
        setupToolbar();
        extractExtras();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindView(movieItemViewModel);
    }

    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.text_title);
        descriptionTextView = (TextView) findViewById(R.id.text_description);
        posterImageView = (ImageView) findViewById(R.id.image_poster);
        ratingBarView = (RatingBar) findViewById(R.id.rating_bar);
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void extractExtras() {
        if (getIntent() == null) return;
        if (!getIntent().hasExtra(EXTRA_MOVIE_VIEW_MODEL)) return;

        String jsonString = getIntent().getStringExtra(EXTRA_MOVIE_VIEW_MODEL);
        if (TextUtils.isEmpty(jsonString)) return;

        movieItemViewModel = JsonUtils.fromJson(jsonString, MovieItemViewModel.class);
    }


    public void bindView(MovieItemViewModel viewModel) {
        titleTextView.setText(viewModel.getName());
        descriptionTextView.setText(viewModel.getDescription());

        displayToolbarView(viewModel);
        displayPosterView(viewModel);
        displayRatingView(viewModel);
    }

    private void displayToolbarView(MovieItemViewModel viewModel) {
        String name = viewModel.getName();
        if (TextUtils.isEmpty(name)) return;

        toolbar.setTitle(name);
    }

    private void displayPosterView(MovieItemViewModel viewModel) {
        String imageUrl = viewModel.getImageBackdropUrl();
        Picasso.with(posterImageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_filmstrip)
                .error(R.drawable.ic_filmstrip)
                .fit()
                .centerCrop()
                .into(posterImageView);
    }

    private void displayRatingView(MovieItemViewModel viewModel) {
        Double rating = viewModel.getVoteAverage();
        if (rating == null) return;

        BigDecimal bigDecimal = new BigDecimal(rating/2);
        float normalizedRating = bigDecimal.floatValue();
        ratingBarView.setRating(normalizedRating);
    }
}
