package com.davidlutta.ytsapp.UI.download.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.davidlutta.ytsapp.models.download.MovieDownload;
import com.davidlutta.ytsapp.models.download.MovieDownloadRequest;
import com.davidlutta.ytsapp.repositories.MoviesRepository;
import com.davidlutta.ytsapp.util.Resource;

import java.util.List;

public class DownloadFragmentViewModel extends AndroidViewModel {
    private static final String TAG = "DownloadFragmentViewModel";
    private MoviesRepository moviesRepository;
    private MediatorLiveData<Resource<List<MovieDownload>>> mMovieDownloadingMoviesList = new MediatorLiveData<>();
    private MediatorLiveData<Resource<List<MovieDownloadRequest>>> mMovieDownloadRequestList = new MediatorLiveData<>();

    public DownloadFragmentViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = MoviesRepository.getInstance(application);
    }

    /*public LiveData<List<MovieDownload>> getDownloadingMovies() {
        return moviesRepository.getDownloadingMovies();
    }*/

    /*public LiveData<List<MovieDownload>> getDownloadRequestMovies() {
      return moviesRepository.getDownloadRequestMovies();
  }*/

    public LiveData<Resource<List<MovieDownload>>> getDownloadingMovies() {
        return mMovieDownloadingMoviesList;
    }

    public LiveData<Resource<List<MovieDownloadRequest>>> getDownloadRequestMovies(){
        return mMovieDownloadRequestList;
    }

    /*
    *     public void getData(String owner) {
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
    * */
    @SuppressLint("LongLogTag")
    public void fetchDownloadingMovieData(String owner) {
        final LiveData<Resource<List<MovieDownload>>> repoSource = moviesRepository.getDownloadingMovies(owner);
        mMovieDownloadingMoviesList.addSource(repoSource, listResource -> {
            if (listResource != null) {
                mMovieDownloadingMoviesList.setValue(listResource);
                if (listResource.status == Resource.Status.SUCCESS) {
                    if (listResource.data != null) {
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "fetchData: ==================================== Exhausted ====================================");
                        }
                    }
                    mMovieDownloadingMoviesList.removeSource(repoSource);
                } else if (listResource.status == Resource.Status.ERROR) {
                    mMovieDownloadingMoviesList.removeSource(repoSource);
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    public void fetchDownloadingRequestMovieData(String owner) {
        final LiveData<Resource<List<MovieDownloadRequest>>> repoSource = moviesRepository.getDownloadRequestMovies(owner);
        mMovieDownloadRequestList.addSource(repoSource, listResource ->{
            if (listResource != null) {
                mMovieDownloadRequestList.setValue(listResource);
                if (listResource.status == Resource.Status.SUCCESS) {
                    if (listResource.data != null) {
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "fetchData: ==================================== Exhausted ====================================");
                        }
                    }
                    mMovieDownloadRequestList.removeSource(repoSource);
                } else if (listResource.status == Resource.Status.ERROR) {
                    mMovieDownloadRequestList.removeSource(repoSource);
                }
            }
        });

    }

    public LiveData<String> deleteMovie(MovieDownload movie) {
        return moviesRepository.deleteMovie(movie);
    }


    public LiveData<String> deleteDownloadRequestMovies(MovieDownloadRequest movie) {
        return moviesRepository.deleteDownloadRequestMovie(movie);
    }
}
