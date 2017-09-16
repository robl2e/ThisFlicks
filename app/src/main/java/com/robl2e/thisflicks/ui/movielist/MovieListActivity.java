package com.robl2e.thisflicks.ui.movielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robl2e.thisflicks.R;
import com.robl2e.thisflicks.data.model.movie.Movie;
import com.robl2e.thisflicks.data.remote.AppAsyncHttpResponseHandler;
import com.robl2e.thisflicks.data.remote.movie.MovieClientApi;
import com.robl2e.thisflicks.data.remote.movie.MovieNowPlayingResponse;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = MovieListActivity.class.getSimpleName();
    private RecyclerView movieListView;

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
        movieListView.setLayoutManager(new LinearLayoutManager(this));
        movieListView.setAdapter(listAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        movieListView.addItemDecoration(itemDecoration);
    }

    private void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }
}

