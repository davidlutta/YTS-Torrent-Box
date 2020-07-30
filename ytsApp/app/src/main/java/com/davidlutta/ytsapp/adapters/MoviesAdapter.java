package com.davidlutta.ytsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.UI.movie.MovieActivity;
import com.davidlutta.ytsapp.models.movies.Movies;

import java.util.List;
import java.util.Objects;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private static final String TAG = "MoviesAdapter";
    private Context mContext;
    private List<Movies> moviesList;

    public MoviesAdapter(Context mContext, List<Movies> moviesList) {
        this.mContext = mContext;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movies movies = getSelectedMovie(position);
        if (movies != null) {
            String title = movies.getTitle();
            String rating = movies.getRating().toString() + " /10";
            String poster = movies.getMediumCoverImage();
            holder.title.setText(title);
            holder.rating.setText(rating);
            Glide.with(mContext)
                    .load(poster)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.placeholder)
                    .into(new ImageViewTarget<Drawable>(holder.poster) {
                        @Override
                        protected void setResource(@Nullable Drawable resource) {
                            if (resource != null) {
                                setImage(resource);
                            } else {
                                Log.d(TAG, "setResource: No Resource Found");
                            }
                            if (!hasAlreadyExtractedColor()) {
                                if (resource != null) {
                                    extractColors(resource);
                                } else {
                                    Log.d(TAG, "setResource: No Resource Found");
                                }
                            }
                        }

                        private boolean hasAlreadyExtractedColor() {
                            return movies.getBackgroundColor() != 0;
                        }

                        private void setImage(Drawable resource) {
                            holder.poster.setImageDrawable(resource.getCurrent());
                        }

                        private void extractColors(Drawable resource) {
                            Bitmap b = ((BitmapDrawable) resource.getCurrent()).getBitmap();
                            Palette palette = Palette.from(b).generate();
                            extractBackgroundColors(palette);
                        }

                        private void extractBackgroundColors(Palette palette) {
                            int defaultColor = mContext.getResources().getColor(R.color.colorPrimary);
                            int color = palette.getDominantColor(defaultColor);
                            movies.setBackgroundColor(color);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private Movies getSelectedMovie(int position) {
        if (moviesList.size() > 0) {
            return moviesList.get(position);
        }
        return null;
    }

    public class MoviesViewHolder extends BaseViewHolder {
        private ImageView poster;
        private TextView title;
        private TextView rating;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.itemBackgroundImageView);
            title = itemView.findViewById(R.id.itemTitleTextView);
            rating = itemView.findViewById(R.id.itemRatingTextView);
        }

        @Override
        protected void onClickItem() {
            int position = getAdapterPosition();
            Movies movies = getSelectedMovie(position);
            if (movies != null) {
                Intent intent = new Intent(itemView.getContext(), MovieActivity.class).putExtra(MovieActivity.TAG, (Parcelable) movies);
//                Intent intent = new Intent(itemView.getContext(), MovieActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) itemView.getContext(), poster, Objects.requireNonNull(ViewCompat.getTransitionName(poster)));
                intent.putExtra("id", movies.getId().toString());
                itemView.getContext().startActivity(intent, options.toBundle());
            }
        }
    }
}
