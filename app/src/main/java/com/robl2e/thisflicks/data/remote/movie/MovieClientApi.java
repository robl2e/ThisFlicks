package com.robl2e.thisflicks.data.remote.movie;

import com.robl2e.thisflicks.data.remote.common.AppResponseHandlerInterface;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    private OkHttpClient client;

    MovieClientApi() {
        client = new OkHttpClient();
    }

    public void getNowPlaying(final AppResponseHandlerInterface responseHandler) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(NOW_PLAYING_ENDPOINT).newBuilder();
        urlBuilder.addQueryParameter(PARAM_API_KEY, API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (responseHandler == null) return;

                responseHandler.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (responseHandler == null) return;

                responseHandler.onResponse(call, response);
            }
        });
    }

    public String buildImageUrl(String filepath) {
        return IMAGE_ENDPOINT + filepath;
    }
}
