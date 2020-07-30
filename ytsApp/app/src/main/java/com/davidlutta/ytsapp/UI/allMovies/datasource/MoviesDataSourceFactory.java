package com.davidlutta.ytsapp.UI.allMovies.datasource;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;


public class MoviesDataSourceFactory extends DataSource.Factory {
    public static final String TAG = "MoviesDataSourceFactory";
    MoviesDataSource moviesDataSource;
    MutableLiveData<MoviesDataSource> mutableLiveData;

    public MoviesDataSourceFactory() {
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        moviesDataSource = new MoviesDataSource();
        Log.d(TAG, "create: " + moviesDataSource);
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }

    public MutableLiveData<MoviesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
