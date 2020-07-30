package com.davidlutta.ytsapp.UI.search.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.davidlutta.ytsapp.models.movies.Movies;
import com.davidlutta.ytsapp.repositories.MoviesRepository;

import java.util.List;

public class SearchFragmentViewModel extends AndroidViewModel {
    private MoviesRepository moviesRepository;

    public SearchFragmentViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = MoviesRepository.getInstance(application);
    }

    public LiveData<List<Movies>> searchMovie(String query) {
        return moviesRepository.searchMovie(query);
    }
}
