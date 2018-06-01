package io.shellee.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.shellee.movies.model.Movie;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

    @Insert(onConflict=REPLACE)
    void save(Movie... movie);

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    LiveData<List<Movie>> loadMoviesByPopularity();

    @Query("SELECT * FROM movie ORDER BY userRating DESC")
    LiveData<List<Movie>> loadMoviesByUserRating();

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> findById(Integer id);

    @Query("SELECT * FROM movie WHERE timeToRefresh > :refreshMax LIMIT 1")
    boolean hasMovies(Long refreshMax);

    @Query("DELETE FROM movie")
    void deleteAll();
}
