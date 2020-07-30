package com.davidlutta.ytsapp.UI.allMovies.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.davidlutta.ytsapp.api.MoviesApi;
import com.davidlutta.ytsapp.api.RetrofitService;
import com.davidlutta.ytsapp.models.movies.Movies;
import com.davidlutta.ytsapp.models.movies.MoviesResponse;
import com.davidlutta.ytsapp.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDataSource extends PageKeyedDataSource<Integer, Movies> {

    public static final String TAG = "MoviesDataSource";
    private MoviesApi moviesApi;

    public MoviesDataSource() {
        moviesApi = RetrofitService.getMoviesApi();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movies> callback) {

        final int page = 1;
        Map<String, String> options = new HashMap<>();
        options.put(Constants.PAGE, String.valueOf(page));
        moviesApi.searchMovie(options).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Movies> movies = response.body().getData().getMovies();
                        callback.onResult(movies, null, page + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "LoadInitial: onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movies> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movies> callback) {
        final int page = params.key;
        Map<String, String> options = new HashMap<>();
        options.put(Constants.PAGE, String.valueOf(page));
        moviesApi.searchMovie(options)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Movies> movies = response.body().getData().getMovies();
                                callback.onResult(movies, page + 1);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.d(TAG, "LoadAfter: onFailure: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
    }
}
