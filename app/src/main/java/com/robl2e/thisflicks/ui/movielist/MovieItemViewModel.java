package com.robl2e.thisflicks.ui.movielist;

import com.robl2e.thisflicks.data.model.movie.Movie;

/**
 * Created by robl2e on 9/15/17.
 */

public class MovieItemViewModel {
    private final Integer id;
    private final String name;
    private final String description;
    private final String posterImageUrl;
    private final String backdropImageUrl;

    public MovieItemViewModel(Integer id, String name, String description, String posterImageUrl, String backdropImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posterImageUrl = posterImageUrl;
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

    public String getPosterImageUrl() {
        return posterImageUrl;
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
