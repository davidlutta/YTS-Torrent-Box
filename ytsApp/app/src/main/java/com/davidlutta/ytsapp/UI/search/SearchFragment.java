package com.davidlutta.ytsapp.UI.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.search.viewmodel.SearchFragmentViewModel;
import com.davidlutta.ytsapp.adapters.MoviesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static final String SEARCH_QUERY_KEY = "searchQuery";

    @BindView(R.id.searchRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.searchFragmentSearchView)
    public SearchView searchView;
    @BindView(R.id.noMovieFound)
    public TextView noMovieFound;
    private MoviesAdapter adapter;
    private SearchFragmentViewModel mViewModel;
    private String searchQuery = null;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
//        searchView = view.findViewById(R.id.searchFragmentSearchView);
        searchView.setOnQueryTextListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchFragmentViewModel.class);
    }

    private void subscribeViewModel(String query) {
        mViewModel.searchMovie(query).observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && adapter == null) {
                noMovieFound.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new MoviesAdapter(getContext(), movies);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else {
                // TODO: 7/20/20 ADD FUNCTIONALITY TO SHOW MOVIES WHEN THE PREVIOUS QUERY WAS UNSUCCESSFUL
                recyclerView.setVisibility(View.INVISIBLE);
                noMovieFound.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchQuery = query;
        subscribeViewModel(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }
}
