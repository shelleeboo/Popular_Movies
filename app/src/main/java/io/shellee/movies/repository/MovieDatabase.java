package io.shellee.movies.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import javax.inject.Singleton;

import io.shellee.movies.model.Movie;

@Singleton
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(LocalTimeConverter.class)
public abstract class MovieDatabase extends RoomDatabase{
    public abstract MovieDao movieDao();
}
