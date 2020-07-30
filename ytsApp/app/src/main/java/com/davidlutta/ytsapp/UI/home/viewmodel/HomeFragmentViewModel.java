package com.davidlutta.ytsapp.UI.home.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.davidlutta.ytsapp.models.movie.Movie;
import com.davidlutta.ytsapp.models.movies.Movies;
import com.davidlutta.ytsapp.repositories.MoviesRepository;
import com.davidlutta.ytsapp.util.Resource;

import java.util.List;

public class HomeFragmentViewModel extends AndroidViewModel {
    private static final String TAG = "HomeFragmentViewModel";

    private MoviesRepository moviesRepository;
    private MediatorLiveData<Resource<List<Movies>>> mMovieList = new MediatorLiveData<>();

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = MoviesRepository.getInstance(application);
    }

    /*public LiveData<List<Movies>> getHighestRatedMovies() {
        return moviesRepository.getPopularMovies();
    }*/

    public LiveData<Resource<List<Movies>>> getHighestRatedMovies() {
        return mMovieList;
    }

    public void getData(String owner) {
        final LiveData<Resource<List<Movies>>> repoSource = moviesRepository.getPopularMovieList(owner);
        mMovieList.addSource(repoSource, listResource -> {
            if (listResource != null) {
                mMovieList.setValue(listResource);
                if (listResource.status == Resource.Status.SUCCESS) {
                    if (listResource.data != null) {
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "getData: ======== Exhausted ========");
                        }
                    }
                    mMovieList.removeSource(repoSource);
                } else if (listResource.status == Resource.Status.ERROR) {
                    mMovieList.removeSource(repoSource);
                }
            }
        });
    }

    /*public LiveData<Resource<Movie>> getMovie(String movieId) {
        return moviesRepository.getMovie(movieId);
    }*/
}
