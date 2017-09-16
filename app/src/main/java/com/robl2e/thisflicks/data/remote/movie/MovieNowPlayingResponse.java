package com.robl2e.thisflicks.data.remote.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.robl2e.thisflicks.data.model.movie.Dates;
import com.robl2e.thisflicks.data.model.movie.Movie;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


/**
 * Created by robl2e on 9/15/17.
 */
public class MovieNowPlayingResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("dates")
    @Expose
    private Dates dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("results")
    @Expose
    private List<Movie> results;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}