package com.davidlutta.ytsapp.UI.movie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.home.viewmodel.HomeFragmentViewModel;
import com.davidlutta.ytsapp.adapters.TorrentsAdapter;
import com.davidlutta.ytsapp.models.movies.Movies;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity {
    public static final String TAG = "MovieActivity";

    @BindView(R.id.titleTextView)
    public TextView titleTextView;
    @BindView(R.id.genreTextView)
    public TextView genreTextView;
    @BindView(R.id.ratingTextView)
    public TextView ratingTextView;
    @BindView(R.id.overviewTextView)
    public TextView overviewTextView;
    @BindView(R.id.backgroundImageView)
    public ImageView poster;
    @BindView(R.id.YOPTextView)
    public TextView releaseDateTextView;
    @BindView(R.id.runtimeTextView)
    public TextView runtimeTextView;
    @BindView(R.id.downloadMovieButton)
    public FloatingActionButton downloadButton;

    private HomeFragmentViewModel viewModel;
    private Movies currentMovie;
    private TorrentsAdapter torrentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        if (getIntent().hasExtra(TAG)) {
            currentMovie = getIntent().getParcelableExtra(TAG);
            populateData();
        }
        downloadButton.setOnClickListener(onClick -> {
            BottomSheetDialog bottomSheetDialog =
                    new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContainer));
            RecyclerView torrentsRecyclerView = (RecyclerView) bottomSheetView.findViewById(R.id.torrentsRecyclerView);

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
            setUpTorrentAdapter(torrentsRecyclerView);
        });
    }

    /*private void subscribeViewModel() {
        if (getIntent().hasExtra("id")) {
            String id = getIntent().getExtras().getString("id");
            if (id != null) {
                viewModel.getMovie(id).observe(this, movieResource -> {
                    if (movieResource != null) {
                        if (movieResource.data != null) {
                            switch (movieResource.status) {
                                case SUCCESS:
                                    Log.d(TAG, "subscribeViewModel: Cache Refreshed");
                                    currentMovie = movieResource.data;
                                    populateData();
                                    break;
                                case ERROR:
                                    if (movieResource.message != null) {
                                        Log.d(TAG, "subscribeViewModel: ERROR: Title" + movieResource.data.getTitle());
                                        Log.d(TAG, "subscribeViewModel: ERROR: message" + movieResource.message);
                                        Toasty.info(getApplicationContext(), movieResource.message, Toasty.LENGTH_SHORT).show();
                                        currentMovie = movieResource.data;
                                        populateData();
                                    }
                                    break;
                            }
                        }
                    }
                });
            }
        }
    }*/

    @SuppressLint("SetTextI18n")
    private void populateData() {
        if (currentMovie != null) {
            Window window = this.getWindow();
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // changing the color
            window.setStatusBarColor(currentMovie.getBackgroundColor());
            titleTextView.setText(currentMovie.getTitle());
            ratingTextView.setText(String.format("Rating: %s/10", currentMovie.getRating().toString()));
            if (currentMovie.getGenres() != null) {
                genreTextView.setText(currentMovie.getGenres().toString());
            } else {
                genreTextView.setText("Unknown");
            }
            overviewTextView.setText(currentMovie.getDescriptionFull());
            releaseDateTextView.setText(String.format("Year of Release: %s", currentMovie.getYear().toString()));
            if (currentMovie.getRuntime() == 0) {
                runtimeTextView.setText("Unknown");
            } else {
                runtimeTextView.setText(String.format("Runtime: %s mins", currentMovie.getRuntime().toString()));
            }
            Glide.with(this)
                    .load(currentMovie.getLargeCoverImage())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(poster);
            // TODO: 7/20/20 Display error image if no image is loaded
        }
    }

    private void setUpTorrentAdapter(RecyclerView torrentsRecyclerView) {
        torrentsAdapter = new TorrentsAdapter(currentMovie.getTorrents(), currentMovie);
        torrentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        torrentsRecyclerView.setAdapter(torrentsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        torrentsAdapter = null;
    }
}
