package com.robl2e.thisflicks.ui.movielist;


import android.content.res.Configuration;

import com.robl2e.thisflicks.data.model.movie.Movie;
import com.robl2e.thisflicks.data.remote.movie.MovieClientApi;

/**
 * Created by robl2e on 9/15/17.
 */

public class MovieItemViewModel {
    private final Integer id;
    private final String name;
    private final String description;
    private final String posterImagePath;
    private final String backdropImagePath;
    private final Double voteAverage;
    private int orientation = Configuration.ORIENTATION_PORTRAIT;

    public MovieItemViewModel(Integer id, String name, String description, String posterImagePath, String backdropImagePath, Double voteAverage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posterImagePath = posterImagePath;
        this.backdropImagePath = backdropImagePath;
        this.voteAverage = voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public String getImagePosterUrl() {
        return MovieClientApi.getInstance().buildImageUrl(posterImagePath);
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    public String getImageBackdropUrl() {
        return MovieClientApi.getInstance().buildImageUrl(backdropImagePath);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }

    public boolean isHighlyRated() {
        return voteAverage != null && voteAverage > 5.0;
    }

    public static MovieItemViewModel convert(Movie from) {
        return new MovieItemViewModel(from.getId()
                , from.getTitle(), from.getOverview()
                , from.getPosterPath(), from.getBackdropPath(), from.getVoteAverage());
    }
}
