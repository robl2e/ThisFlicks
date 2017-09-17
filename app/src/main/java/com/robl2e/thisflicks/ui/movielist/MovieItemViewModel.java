package com.robl2e.thisflicks.ui.movielist;

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
    private final String backdropImageUrl;

    public MovieItemViewModel(Integer id, String name, String description, String posterImagePath, String backdropImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posterImagePath = posterImagePath;
        this.backdropImageUrl = backdropImageUrl;
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
        return MovieClientApi.getInstance().buildImagePosterUrl(posterImagePath);
    }

    public String getBackdropImageUrl() {
        return backdropImageUrl;
    }

    public static MovieItemViewModel convert(Movie from) {
        return new MovieItemViewModel(from.getId()
                , from.getTitle(), from.getOverview()
                , from.getPosterPath(), from.getBackdropPath());
    }
}
