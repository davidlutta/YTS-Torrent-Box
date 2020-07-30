package com.davidlutta.ytsapp.api;

import androidx.lifecycle.LiveData;

import com.davidlutta.ytsapp.models.download.MovieData;
import com.davidlutta.ytsapp.models.download.MovieDataResponse;
import com.davidlutta.ytsapp.models.download.MovieDownload;
import com.davidlutta.ytsapp.models.download.MovieDownloadRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExpressApi {
    @Headers("Content-Type: application/json")
    @POST("movies")
    Call<MovieDataResponse> downloadMovie(@Body MovieData data);

    /* @GET("movies")
     Call<List<MovieDownload>> getDownloadingMovies();*/
    @GET("movies")
    LiveData<ApiResponse<List<MovieDownload>>> getDownloadingMovies();

    @DELETE("movies/{uid}")
    Call<MovieDataResponse> deleteMovie(@Path("uid") String hash);

    /*@GET("requests")
    Call<List<MovieDownload>> getDownloadRequestMovies();*/

    @GET("requests")
    LiveData<ApiResponse<List<MovieDownloadRequest>>> getDownloadRequestMovies();


    @DELETE("requests/{uid}")
    Call<MovieDataResponse> deleteDownloadRequestMovie(@Path("uid") String hash);
}
