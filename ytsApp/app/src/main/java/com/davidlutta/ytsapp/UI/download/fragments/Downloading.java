package com.davidlutta.ytsapp.UI.download.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.download.adapter.MovieAdapter;
import com.davidlutta.ytsapp.UI.download.viewmodel.DownloadFragmentViewModel;
import com.davidlutta.ytsapp.models.download.MovieDownload;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class Downloading extends Fragment {
    public static final String OWNER = "Downloading";
    private static final String TAG = "Downloading";

    @BindView(R.id.movieDownloadsRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.downloadFragmentProgressBar)
    public ProgressBar progressBar;
    private DownloadFragmentViewModel mViewModel;
    private MovieAdapter adapter;

    private BroadcastReceiver refreshDataBroadcastReceiver;

    public Downloading() {
        // Required empty public constructor
    }

    public static Downloading newInstance() {
        return new Downloading();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloading, container, false);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(this).get(DownloadFragmentViewModel.class);
        mViewModel.fetchDownloadingMovieData(OWNER);
        setUpAdapter();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
                //for drag and drop functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MovieDownload currentMovie = adapter.getMovie(viewHolder.getAdapterPosition());
                if (currentMovie.getPercentage() == 100.0) {
                    mViewModel.deleteMovie(adapter.getMovie(viewHolder.getAdapterPosition())).observe(getViewLifecycleOwner(), s -> Toasty.info(Objects.requireNonNull(getContext()), s, Toasty.LENGTH_SHORT, true).show());
                }else {
                    Toasty.error(Objects.requireNonNull(getContext()), "Unable to delete movie that is in progress", Toasty.LENGTH_SHORT, true).show();
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }
        }).attachToRecyclerView(recyclerView);
        subscribeViewModels();
        return view;
    }

    private void subscribeViewModels() {
        mViewModel.getDownloadingMovies().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource.data != null) {
                switch (listResource.status) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;

                    case SUCCESS:
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "subscribeObservers: onChanged: Cache has been refreshed !");
                        List<MovieDownload> movieDownloadList = listResource.data;
                        Collections.reverse(movieDownloadList);
                        adapter.submitList(movieDownloadList);
                        break;

                    case ERROR:
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "subscribeObservers: OnChanged Error: Cache cannot be refreshed");
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "subscribeObservers: OnChanged Error: #Posts: " + listResource.data.size());
                        }
                        List<MovieDownload> movieList = listResource.data;
                        Collections.reverse(movieList);
                        adapter.submitList(movieList);
                        break;
                }
            }
        });
    }

    private void setUpAdapter() {
        if (adapter == null) {
            adapter = new MovieAdapter(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshData() {
        progressBar.setVisibility(View.INVISIBLE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        refreshDataBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mViewModel.fetchDownloadingMovieData(OWNER);
            }
        };
        if (getActivity() != null) {
            getActivity().registerReceiver(refreshDataBroadcastReceiver, intentFilter);
        } else {
            Log.d(TAG, "refreshData: NO ACTIVITY FOUND");
        }
    }

    private void unregisterReceiver() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(refreshDataBroadcastReceiver);
        } else {
            Log.d(TAG, "onPause: NO ACTIVITY FOUND");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        unregisterReceiver();
    }
}