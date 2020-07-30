package com.davidlutta.ytsapp.api;

import androidx.lifecycle.LiveData;

import com.davidlutta.ytsapp.models.movie.MovieResponse;
import com.davidlutta.ytsapp.models.movies.MoviesResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MoviesApi {
    /*@GET("list_movies.json")
    Call<MoviesResponse> getMovies(@QueryMap Map<String, String> filters);*/
    @GET("list_movies.json")
    LiveData<ApiResponse<MoviesResponse>> getMovies(@QueryMap Map<String, String> filters);

    @GET("list_movies.json")
    Call<MoviesResponse> searchMovie(@QueryMap Map<String, String> filters);

    /*@GET("movie_details.json")
    Call<MovieResponse> getMovie(@Query("movie_id") String movieId);*/

    @GET("movie_details.json")
    LiveData<ApiResponse<MovieResponse>> getMovie(@Query("movie_id") String movieId);
}
