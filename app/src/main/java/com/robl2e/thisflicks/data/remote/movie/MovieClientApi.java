package com.robl2e.thisflicks.data.remote.movie;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.robl2e.thisflicks.data.remote.AppResponseHandlerInterface;

/**
 * Created by robl2e on 9/15/17.
 */

public class MovieClientApi {
    private final String BASE_URL = "https://api.themoviedb.org/3";
    private final String NOW_PLAYING_ENDPOINT = BASE_URL + "/movie/now_playing";

    private final String PARAM_API_KEY = "api_key";
    private final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";


    private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    private final String IMAGE_PARAM_SIZE = "/w342";
    private final String IMAGE_ENDPOINT = IMAGE_BASE_URL + IMAGE_PARAM_SIZE;

    private static class Holder {
        static final MovieClientApi movieClientApi = new MovieClientApi();
    }

    /**
     * Returns the convenience global injector.
     * @return global injector
     */
    public static MovieClientApi getInstance() {
        return Holder.movieClientApi;
    }

    private AsyncHttpClient client;

    MovieClientApi() {
        client = new AsyncHttpClient();
    }

    public void getNowPlaying(AppResponseHandlerInterface responseHandler) {
        RequestParams params = new RequestParams();
        params.put(PARAM_API_KEY, API_KEY);
        client.get(NOW_PLAYING_ENDPOINT, params, responseHandler);
    }

    public String buildImageUrl(String filepath) {
        return IMAGE_ENDPOINT + filepath;
    }
}
