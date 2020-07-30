package com.davidlutta.ytsapp.UI.allMovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.paging.PagedListAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.movie.MovieActivity;
import com.davidlutta.ytsapp.adapters.BaseViewHolder;
import com.davidlutta.ytsapp.models.movies.Movies;

public class MoviesAdapter extends PagedListAdapter<Movies,MoviesAdapter.MoviesViewHolder> {
    private Context mContext;

    public MoviesAdapter(Context mContext) {
        super(Movies.CALLBACK);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public Movies getSelectedMovie(int position) {
        if (getCurrentList() != null) {
            if (getCurrentList().size() > 0) {
                return getItem(position);
            }
        }
        return null;
    }

    public class MoviesViewHolder extends BaseViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView ratingTextView;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.itemBackgroundImageView);
            titleTextView = itemView.findViewById(R.id.itemTitleTextView);
            ratingTextView = itemView.findViewById(R.id.itemRatingTextView);
        }

        @Override
        protected void onClickItem() {
            int position = getAdapterPosition();
            Movies selectedMovie = getSelectedMovie(position);
            if (selectedMovie != null) {
                Intent intent = new Intent(itemView.getContext(), MovieActivity.class).putExtra(MovieActivity.TAG, (Parcelable) selectedMovie);
//                intent.putExtra("id", selectedMovies.getId().toString());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) itemView.getContext(), posterImageView, ViewCompat.getTransitionName(posterImageView));
                itemView.getContext().startActivity(intent, options.toBundle());
            }
        }

        public void onBind(Movies movies) {
            if (movies != null) {
                String title = movies.getTitle();
                String rating = movies.getRating().toString()+" /10";
                String poster = movies.getMediumCoverImage();
                titleTextView.setText(title);
                ratingTextView.setText(rating);
                Glide.with(mContext)
                        .load(poster)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.placeholder)
                        .into(posterImageView);
            }
        }
    }
}
