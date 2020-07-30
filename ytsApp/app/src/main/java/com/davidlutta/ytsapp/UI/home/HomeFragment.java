package com.davidlutta.ytsapp.UI.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.allMovies.BrowseMoviesActivity;
import com.davidlutta.ytsapp.UI.home.viewmodel.HomeFragmentViewModel;
import com.davidlutta.ytsapp.adapters.MoviesAdapter;
import com.davidlutta.ytsapp.models.movies.Movies;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    public static final String OWNER = "HomeFragment";
    private static final String TAG = "HomeFragment";
    public static final int REFRESH_TIME = 10 * 60 * 60; // 10 minutes

    @BindView(R.id.popularMoviesRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeFragmentProgressBar)
    public ProgressBar progressBar;
    @BindView(R.id.browseMoviesBtn)
    public TextView browseMoviesBtn;
    private HomeFragmentViewModel mViewModel;
    private List<Movies> moviesList;
    private MoviesAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        browseMoviesBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), BrowseMoviesActivity.class)));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        mViewModel.getData(OWNER);
        subscribeViewModels();
    }

    private void subscribeViewModels() {
        mViewModel.getHighestRatedMovies().observe(getViewLifecycleOwner(), listResource -> {
            Log.d(TAG, "subscribeObservers: OnChanged: Status:" + listResource.status);
            if (listResource.data != null) {
                switch (listResource.status) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        Log.d(TAG, "subscribeViewModels: onChanged: Cache has been refreshed !");
                        moviesList = listResource.data;
                        setUpAdapter();
                    case ERROR:
                        Log.d(TAG, "subscribeViewModels: OnChanged Error: Cache cannot be refreshed");
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "subscribeViewModels: OnChanged Error: #Movies: " + listResource.data.size());
                            Toast.makeText(getContext(), listResource.message, Toast.LENGTH_SHORT).show();
                        }
                        moviesList = listResource.data;
                        setUpAdapter();
                }
            }
        });
    }

    private void setUpAdapter() {
        if (adapter == null) {
            adapter = new MoviesAdapter(getContext(), moviesList);
            // Add animations from: https://www.youtube.com/watch?v=5PMI_bHGehg
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }
}
