package com.davidlutta.ytsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.davidlutta.ytsapp.database.express.ExpressDao;
import com.davidlutta.ytsapp.database.yts.MoviesDao;
import com.davidlutta.ytsapp.models.download.MovieDownload;
import com.davidlutta.ytsapp.models.download.MovieDownloadRequest;
import com.davidlutta.ytsapp.models.movies.Movies;
import com.davidlutta.ytsapp.util.Converters;

@Database(entities = {Movies.class, MovieDownload.class, MovieDownloadRequest.class}, version = 5, exportSchema = false)
// change version number everytime you add an entity
@TypeConverters({Converters.class})
public abstract class MoviesDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "MovieDatabase";
    public static final Object LOCK = new Object();
    private static MoviesDatabase instance;

    public static MoviesDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        MoviesDatabase.class,
                        DATABASE_NAME
                ).fallbackToDestructiveMigration().build();
            }
        }
        return instance;
    }

    public abstract MoviesDao getMoviesDao();

    public abstract ExpressDao getDownloadingMoviesDao();
}
