package com.davidlutta.ytsapp.UI.allMovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.davidlutta.ytsapp.UI.allMovies.datasource.MoviesDataSource;
import com.davidlutta.ytsapp.UI.allMovies.datasource.MoviesDataSourceFactory;
import com.davidlutta.ytsapp.models.movies.Movies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesViewModel extends AndroidViewModel {
    private MoviesDataSourceFactory moviesDataSourceFactory;
    private MutableLiveData<MoviesDataSource> dataSourceMutableLiveData;
    private LiveData<PagedList<Movies>> pagedListLiveData;
    private Executor executor;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDataSourceFactory = new MoviesDataSourceFactory();
        dataSourceMutableLiveData = moviesDataSourceFactory.getMutableLiveData();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();
        executor = Executors.newFixedThreadPool(5);
        pagedListLiveData = new LivePagedListBuilder<Integer, Movies>(moviesDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Movies>> getPagedListLiveData() {
        if (pagedListLiveData != null) {
            return pagedListLiveData;
        }else
            return null;
    }
}
