package com.davidlutta.ytsapp.UI.download.adapter;

import android.annotation.SuppressLint;
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

import java.util.Objects;

public class MovieAdapter extends ListAdapter<MovieDownload, MovieAdapter.MovieViewHolder> {
    private Context context;

    public MovieAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    public static final DiffUtil.ItemCallback<MovieDownload> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieDownload>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieDownload oldItem, @NonNull MovieDownload newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieDownload oldItem, @NonNull MovieDownload newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_two, parent, false);
        return new MovieViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieDownload currentMovie = getMovie(position);
        if (currentMovie != null) {
            holder.titleTextView.setText(currentMovie.getTitle());
            String percentage = currentMovie.getPercentage().toString() + "% downloaded";
            if (currentMovie.getPercentage() == 100.0) {
                holder.statusCard.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.jadeGreen));
            }
            holder.percentageTextView.setText(percentage);
            holder.statusTextView.setText(String.format("Status: %s", currentMovie.getProgress()));
            Glide.with(context)
                    .load(currentMovie.getPoster())
                    .placeholder(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.poster);
        }
    }


    public MovieDownload getMovie(int position) {
        if (getCurrentList().size() > 0) {
            return getItem(position);
        }
        return null;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView statusTextView;
        private TextView percentageTextView;
        private ImageView poster;
        private CardView statusCard;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.downloadMovieTitle);
            statusTextView = itemView.findViewById(R.id.downloadMovieStatusTextView);
            poster = itemView.findViewById(R.id.downloadMovieImageView);
            percentageTextView = itemView.findViewById(R.id.downloadMoviePercentageTextView);
            statusCard = itemView.findViewById(R.id.downloadMovieStatusCard);
        }
    }
}
