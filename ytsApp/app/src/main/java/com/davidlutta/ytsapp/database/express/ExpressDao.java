package com.davidlutta.ytsapp.database.express;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.davidlutta.ytsapp.models.download.MovieDownload;
import com.davidlutta.ytsapp.models.download.MovieDownloadRequest;

import java.util.List;

@Dao
public interface ExpressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDownloadingMovie(List<MovieDownload> movies);

    @Query("SELECT * FROM downloadingMovies")
    LiveData<List<MovieDownload>> getDownloadingMovies();

    @Delete
    void deleteDownloadingMovie(MovieDownload movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDownloadRequestMovie(List<MovieDownloadRequest> movies);

    @Query("SELECT * FROM downloadRequestMovies")
    LiveData<List<MovieDownloadRequest>> getDownloadRequestMovies();

    @Delete
    void deleteDownloadRequestMovie(MovieDownloadRequest movie);
}
