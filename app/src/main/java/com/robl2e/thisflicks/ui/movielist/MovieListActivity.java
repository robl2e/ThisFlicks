package com.robl2e.thisflicks.ui.movielist;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robl2e.thisflicks.R;
import com.robl2e.thisflicks.data.model.movie.Movie;
import com.robl2e.thisflicks.data.remote.AppAsyncHttpResponseHandler;
import com.robl2e.thisflicks.data.remote.movie.MovieClientApi;
import com.robl2e.thisflicks.data.remote.movie.MovieNowPlayingResponse;
import com.robl2e.thisflicks.ui.utils.UIResourceUtils;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = MovieListActivity.class.getSimpleName();
    private RecyclerView movieListView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        bindViews();
        initializeList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNowPlayingMovies();
    }

    private void loadNowPlayingMovies() {
        MovieClientApi.getInstance().getNowPlaying(new AppAsyncHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new GsonBuilder().create();
                // Define Response class to correspond to the JSON response returned
                MovieNowPlayingResponse response = gson.fromJson(responseString, MovieNowPlayingResponse.class);
                List<Movie> nowPlayingMovies = response.getResults();

                List<MovieItemViewModel> viewModels = new ArrayList<>();
                for (Movie movie : nowPlayingMovies) {
                    MovieItemViewModel viewModel = MovieItemViewModel.convert(movie);
                    viewModel.setOrientation(UIResourceUtils.getScreenOrientation(MovieListActivity.this));
                    viewModels.add(viewModel);
                }

                listAdapter.setItems(viewModels);
                updateListAdapter();
            }
        });
    }

    private void bindViews() {
        movieListView = (RecyclerView) findViewById(R.id.list_movies);
    }

    private void initializeList() {
        listAdapter = new MovieListAdapter(this, new ArrayList<MovieItemViewModel>());

        if (isLandscapeOrientation()) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    MovieItemViewModel viewModel = listAdapter.getItem(position);
                    if (viewModel == null) return 1;

                    if (viewModel.isHighlyRated()) {
                        return 2;
                    }
                    return 1;
                }
            });
        }
        movieListView.setLayoutManager(layoutManager);
        movieListView.setAdapter(listAdapter);
    }

    private boolean isLandscapeOrientation() {
        return UIResourceUtils.getScreenOrientation(this)
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }
}

