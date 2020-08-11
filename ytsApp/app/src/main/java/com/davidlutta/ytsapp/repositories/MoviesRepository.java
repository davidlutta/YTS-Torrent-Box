package com.davidlutta.ytsapp.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidlutta.ytsapp.api.ApiResponse;
import com.davidlutta.ytsapp.api.ExpressApi;
import com.davidlutta.ytsapp.api.MoviesApi;
import com.davidlutta.ytsapp.api.RetrofitService;
import com.davidlutta.ytsapp.database.MoviesDatabase;
import com.davidlutta.ytsapp.database.express.ExpressDao;
import com.davidlutta.ytsapp.database.yts.MoviesDao;
import com.davidlutta.ytsapp.models.download.MovieDataResponse;
import com.davidlutta.ytsapp.models.download.MovieDownload;
import com.davidlutta.ytsapp.models.download.MovieDownloadRequest;
import com.davidlutta.ytsapp.models.movies.Movies;
import com.davidlutta.ytsapp.models.movies.MoviesResponse;
import com.davidlutta.ytsapp.util.AppExecutors;
import com.davidlutta.ytsapp.util.Constants;
import com.davidlutta.ytsapp.util.NetworkBoundResource;
import com.davidlutta.ytsapp.util.RateLimiter;
import com.davidlutta.ytsapp.util.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    public static final String TAG = "MoviesRepository";
    private static MoviesRepository instance;
    private MoviesApi moviesApi;
    private ExpressApi expressApi;
    private MoviesDao moviesDao;
    private ExpressDao expressDao;
    private RateLimiter<String> movieListLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public MoviesRepository(Context context) {
        moviesApi = RetrofitService.getMoviesApi();
        moviesDao = MoviesDatabase.getInstance(context).getMoviesDao();
        expressDao = MoviesDatabase.getInstance(context).getDownloadingMoviesDao();
        expressApi = RetrofitService.getExpressApi();
    }

    public static MoviesRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MoviesRepository(context);
        }
        return instance;
    }

    public LiveData<Resource<List<Movies>>> getPopularMovieList(String owner) {
        return new NetworkBoundResource<List<Movies>, MoviesResponse>(AppExecutors.getInstance()) {
            @Override
            public void saveCallResult(@NonNull MoviesResponse item) {
                if (item.getData() != null) {
                    moviesDao.insertMovies(item.getData().getMovies());
                }
            }

            @Override
            public boolean shouldFetch(@Nullable List<Movies> data) {
                return true;
            }

            @NonNull
            @Override
            public LiveData<List<Movies>> loadFromDb() {
                return moviesDao.getMovies();
            }

            @NonNull
            @Override
            public LiveData<ApiResponse<MoviesResponse>> createCall() {
                new DeleteMoviesTableAsyncTask(moviesDao).execute();
                ArrayMap<String, String> options = new ArrayMap<>();
                options.put(Constants.LIMIT, "10");
                options.put(Constants.RATING, "8");
                options.put(Constants.SORT, "downloadcount");
                return moviesApi.getMovies(options);
            }

            @Override
            public void onFetchFailed() {
                Log.d(TAG, "onFetchFailed: -----------------------> Failed To Fetch Movies <-----------------------");
            }
        }.getAsLiveData();
    }

    public MutableLiveData<List<Movies>> searchMovie(String query) {
        final MutableLiveData<List<Movies>> movieData = new MutableLiveData<>();
        Map<String, String> options = new HashMap<>();
        options.put(Constants.QUERY, query);
        moviesApi.searchMovie(options).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData().getMovieCount() == 0) {
                            movieData.postValue(null);
                            Log.d(TAG, "SearchMovie: onResponse: No Movie Was Found");
                        }
                        movieData.postValue(response.body().getData().getMovies());
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                movieData.postValue(null);
                Log.d(TAG, "SearchMovie: onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return movieData;
    }

    public LiveData<Resource<List<MovieDownload>>> getDownloadingMovies(String owner) {
        return new NetworkBoundResource<List<MovieDownload>, List<MovieDownload>>(AppExecutors.getInstance()) {
            @Override
            public void saveCallResult(@NonNull List<MovieDownload> item) {
                if (item.size() > 0) {
                    expressDao.insertDownloadingMovie(item);
                } else {
                    Log.d(TAG, "saveCallResult: getDownloadingMovies: NO ITEMS TO SAVE");
                }
            }

            @Override
            public boolean shouldFetch(@Nullable List<MovieDownload> data) {
                return true;
            }

            @NonNull
            @Override
            public LiveData<List<MovieDownload>> loadFromDb() {
                return expressDao.getDownloadingMovies();
            }

            @NonNull
            @Override
            public LiveData<ApiResponse<List<MovieDownload>>> createCall() {
                return expressApi.getDownloadingMovies();
            }

            @Override
            public void onFetchFailed() {
                Log.d(TAG, "onFetchFailed: Failed to Fetch data");
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<MovieDownloadRequest>>> getDownloadRequestMovies(String owner) {
        return new NetworkBoundResource<List<MovieDownloadRequest>, List<MovieDownloadRequest>>(AppExecutors.getInstance()) {
            @Override
            public void saveCallResult(@NonNull List<MovieDownloadRequest> item) {
                if (item.size() > 0) {
                    expressDao.insertDownloadRequestMovie(item);
                } else {
                    Log.d(TAG, "saveCallResult: getDownloadRequestMovies: NO ITEMS TO SAVE");
                }
            }

            @Override
            public boolean shouldFetch(@Nullable List<MovieDownloadRequest> data) {
                return true;
            }

            @NonNull
            @Override
            public LiveData<List<MovieDownloadRequest>> loadFromDb() {
                return expressDao.getDownloadRequestMovies();
            }

            @NonNull
            @Override
            public LiveData<ApiResponse<List<MovieDownloadRequest>>> createCall() {
                return expressApi.getDownloadRequestMovies();
            }

            @Override
            public void onFetchFailed() {
                Log.d(TAG, "onFetchFailed: getDownloadRequestMovies: Failed to fetch movies");
            }
        }.getAsLiveData();
    }

    public MutableLiveData<String> deleteMovie(MovieDownload movie) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        expressApi.deleteMovie(movie.getHash()).enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.postValue(response.body().getMessage());
                        new DeleteDownloadingMovieAsyncTask(expressDao).execute(movie);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                data.postValue(t.getMessage());
                Log.d(TAG, "DeleteMovie: onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<String> deleteDownloadRequestMovie(MovieDownloadRequest movie) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        expressApi.deleteDownloadRequestMovie(movie.getHash()).enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.postValue(response.body().getMessage());
                        new DeleteDownloadRequestMovieAsyncTask(expressDao).execute(movie);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                data.postValue(t.getMessage());
                Log.d(TAG, "DeleteDownloadRequestMovie: onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    private static class DeleteDownloadingMovieAsyncTask extends AsyncTask<MovieDownload, Void, Void> {
        private ExpressDao expressDao;

        public DeleteDownloadingMovieAsyncTask(ExpressDao expressDao) {
            this.expressDao = expressDao;
        }

        @Override
        protected Void doInBackground(MovieDownload... movieDownloads) {
            expressDao.deleteDownloadingMovie(movieDownloads[0]);
            return null;
        }
    }

    private static class DeleteDownloadRequestMovieAsyncTask extends AsyncTask<MovieDownloadRequest, Void, Void> {
        private ExpressDao expressDao;

        public DeleteDownloadRequestMovieAsyncTask(ExpressDao expressDao) {
            this.expressDao = expressDao;
        }

        @Override
        protected Void doInBackground(MovieDownloadRequest... movieDownloadRequests) {
            expressDao.deleteDownloadRequestMovie(movieDownloadRequests[0]);
            return null;
        }
    }

    // Async Task to delete the movies Table
    private static class DeleteMoviesTableAsyncTask extends AsyncTask<Void, Void, Void> {
        private MoviesDao moviesDao;

        public DeleteMoviesTableAsyncTask(MoviesDao moviesDao) {
            this.moviesDao = moviesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            moviesDao.deleteMoviesTable();
            return null;
        }
    }
}

