package com.davidlutta.ytsapp.UI.download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.models.download.MovieDownload;
import com.davidlutta.ytsapp.models.download.MovieDownloadRequest;

import java.util.Objects;

public class DownloadRequestMovieAdapter extends ListAdapter<MovieDownloadRequest, DownloadRequestMovieAdapter.MovieViewHolder> {
    private Context context;

    public DownloadRequestMovieAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    public static final DiffUtil.ItemCallback<MovieDownloadRequest> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieDownloadRequest>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieDownloadRequest oldItem, @NonNull MovieDownloadRequest newItem) {
            return oldItem.getHash().equals(newItem.getHash());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieDownloadRequest oldItem, @NonNull MovieDownloadRequest newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_three, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieDownloadRequest currentMovie = getMovie(position);
        if (currentMovie != null) {
            holder.titleTextView.setText(currentMovie.getTitle());
            Glide.with(context)
                    .load(currentMovie.getPoster())
                    .placeholder(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.poster);
        }
    }

    public MovieDownloadRequest getMovie(int position) {
        if (getCurrentList().size() > 0) {
            return getItem(position);
        }
        return null;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView poster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.downloadMovieTitle);
            poster = itemView.findViewById(R.id.downloadMovieImageView);
        }
    }
}
