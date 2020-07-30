package com.davidlutta.ytsapp.database.yts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.davidlutta.ytsapp.models.movies.Movies;

import java.util.List;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovies(List<Movies> movies);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);*/

    @Query("SELECT * FROM movies")
    LiveData<List<Movies>> getMovies();

    /*@Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> getMovie(String id);*/

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    LiveData<List<Movies>> searchMovie(String query);

    @Query("DELETE FROM movies")
    void deleteMoviesTable();

    /*@Query("DELETE FROM movie")
    void deleteMovieTable();*/
}
