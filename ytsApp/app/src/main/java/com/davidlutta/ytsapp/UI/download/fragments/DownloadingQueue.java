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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.download.adapter.DownloadRequestMovieAdapter;
import com.davidlutta.ytsapp.UI.download.viewmodel.DownloadFragmentViewModel;
import com.davidlutta.ytsapp.models.download.MovieDownload;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class DownloadingQueue extends Fragment {
    public static final String OWNER = "DownloadingQueue";
    public static final String TAG = "DownloadingQueue";

    @BindView(R.id.movieDownloadRequestRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.downloadRequestFragmentProgressBar)
    public ProgressBar progressBar;
    private DownloadFragmentViewModel mViewModel;
    private DownloadRequestMovieAdapter adapter;

    private BroadcastReceiver refreshDataBroadcastReceiver;

    public DownloadingQueue() {
    }

    public static DownloadingQueue newInstance() {
        return new DownloadingQueue();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloading_queue, container, false);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(this).get(DownloadFragmentViewModel.class);
        setUpAdapter();
        mViewModel.fetchDownloadingRequestMovieData(OWNER);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
                //for drag and drop functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.deleteDownloadRequestMovies(adapter.getMovie(viewHolder.getAdapterPosition())).observe(getViewLifecycleOwner(), s -> {
                    Toasty.info(Objects.requireNonNull(getContext()), s, Toasty.LENGTH_SHORT, true).show();
                });
            }
        }).attachToRecyclerView(recyclerView);
        subscribeViewModels();
        return view;
    }

    private void subscribeViewModels() {
        mViewModel.getDownloadRequestMovies().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource.data != null) {
                switch (listResource.status) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "subscribeObservers: onChanged: Cache has been refreshed !");
                        adapter.submitList(listResource.data);
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "subscribeObservers: OnChanged Error: Cache cannot be refreshed");
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "subscribeObservers: OnChanged Error: #Posts: " + listResource.data.size());
                        }
                        adapter.submitList(listResource.data);
                        break;
                }
            }
        });
    }


    private void setUpAdapter() {
        if (adapter == null) {
            adapter = new DownloadRequestMovieAdapter(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void updateData() {
        progressBar.setVisibility(View.INVISIBLE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        refreshDataBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mViewModel.fetchDownloadingRequestMovieData(OWNER);
            }
        };
        if (getActivity() != null) {
            Log.d(TAG, "updateData: Registering Broadcast Receiver");
            getActivity().registerReceiver(refreshDataBroadcastReceiver, intentFilter);
        } else {
            Log.d(TAG, "updateData: NO ACTIVITY FOUND !");
        }
    }

    private void unregisterReceiver() {
        if (getActivity() != null) {
            Log.d(TAG, "unregisterReceiver: Unregistering Broadcast Receiver");
            getActivity().unregisterReceiver(refreshDataBroadcastReceiver);
        } else {
            Log.d(TAG, "unregisterReceiver: NO ACTIVITY FOUND !");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
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
    }
}