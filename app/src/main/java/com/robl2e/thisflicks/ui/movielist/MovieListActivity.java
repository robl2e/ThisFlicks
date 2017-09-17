package com.robl2e.thisflicks.ui.movielist;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.robl2e.thisflicks.R;
import com.robl2e.thisflicks.data.model.movie.Movie;
import com.robl2e.thisflicks.data.remote.common.AppAsyncHttpResponseHandler;
import com.robl2e.thisflicks.data.remote.movie.MovieClientApi;
import com.robl2e.thisflicks.data.remote.movie.MovieNowPlayingResponse;
import com.robl2e.thisflicks.ui.common.ItemClickSupport;
import com.robl2e.thisflicks.ui.moviedetail.MovieDetailActivity;
import com.robl2e.thisflicks.ui.util.UIResourceUtils;
import com.robl2e.thisflicks.util.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = MovieListActivity.class.getSimpleName();
    private RecyclerView movieListView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.now_playing);
        }

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
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                final MovieNowPlayingResponse nowPlayingResponse = JsonUtils.fromJson(responseString, MovieNowPlayingResponse.class);

                MovieListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Movie> nowPlayingMovies = nowPlayingResponse.getResults();
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
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(movieListView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MovieItemViewModel viewModel = listAdapter.getItem(position);
                if (viewModel == null) return;

                MovieDetailActivity.start(MovieListActivity.this, viewModel);
            }
        });
    }

    private boolean isLandscapeOrientation() {
        return UIResourceUtils.getScreenOrientation(this)
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void updateListAdapter() {
        listAdapter.notifyDataSetChanged();
    }
}

