package com.davidlutta.ytsapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.ytsapp.R;
import com.davidlutta.ytsapp.api.ExpressApi;
import com.davidlutta.ytsapp.api.RetrofitService;
import com.davidlutta.ytsapp.models.download.MovieData;
import com.davidlutta.ytsapp.models.download.MovieDataResponse;
import com.davidlutta.ytsapp.models.movies.Movies;
import com.davidlutta.ytsapp.models.movies.Torrent;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TorrentsAdapter extends RecyclerView.Adapter<TorrentsAdapter.TorrentsViewHolder> {
    public static final String TAG = "TorrentsAdapter";
    private List<Torrent> torrentList;
    private Movies movie;

    public TorrentsAdapter(List<Torrent> torrentList, Movies movie) {
        this.torrentList = torrentList;
        this.movie = movie;
    }

    @NonNull
    @Override
    public TorrentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.torrent_item, parent, false);
        return new TorrentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TorrentsViewHolder holder, int position) {
        Torrent torrent = getSelectedTorrent(position);
        if (torrent != null) {
            holder.downloadTextView.setText(String.format("Download %s %s", torrent.getQuality(), torrent.getType()));
        }
    }

    @Override
    public int getItemCount() {
        return torrentList.size();
    }

    public Torrent getSelectedTorrent(int position) {
        if (torrentList.size() > 0) {
            return torrentList.get(position);
        }
        return null;
    }

    public class TorrentsViewHolder extends BaseViewHolder {
        private TextView downloadTextView;
        private ExpressApi expressApi;

        public TorrentsViewHolder(@NonNull View itemView) {
            super(itemView);
            downloadTextView = (TextView) itemView.findViewById(R.id.downloadTextView);
            expressApi = RetrofitService.getExpressApi();
        }

        @Override
        protected void onClickItem() {
            String title = String.format("%s [%s %s]", movie.getTitle(), selectedTorrent.getType(), selectedTorrent.getQuality());
            MovieData data = new MovieData(selectedTorrent.getHash(), title, movie.getMediumCoverImage(), selectedTorrent.getUrl());
            Toasty.info(itemView.getContext(), "Uploading to Server...", Toasty.LENGTH_SHORT, true).show();
            expressApi.downloadMovie(data).enqueue(new Callback<MovieDataResponse>() {
                @Override
                public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                    if (response.isSuccessful()) {
                        Toasty.success(itemView.getContext(), "Success Uploading to Server", Toasty.LENGTH_SHORT, true).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                    Toasty.error(itemView.getContext(), "Failed to upload to server", Toasty.LENGTH_SHORT, true).show();
                    System.out.println("------------------------------------------------------");
                    Log.d(TAG, "TorrentsViewHolder: onFailure: " + t.getMessage());
                    System.out.println("------------------------------------------------------");
                    t.printStackTrace();
                }
            });
        }
    }
}
