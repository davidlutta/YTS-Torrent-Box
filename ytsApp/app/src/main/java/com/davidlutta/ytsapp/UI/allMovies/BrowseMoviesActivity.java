package com.davidlutta.ytsapp.UI.allMovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.allMovies.adapter.MoviesAdapter;
import com.davidlutta.ytsapp.UI.allMovies.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseMoviesActivity extends AppCompatActivity {
    @BindView(R.id.browserMoviesRecyclerView)
    public RecyclerView recyclerView;
    private MoviesViewModel mViewModel;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_movies);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        setUpAdapter();
        subscribeViewModels();
    }

    private void subscribeViewModels() {
        mViewModel.getPagedListLiveData().observe(this, movies -> {
            moviesAdapter.submitList(movies);
        });
    }

    private void setUpAdapter() {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setAdapter(moviesAdapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}