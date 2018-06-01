package io.shellee.movies.vm;


import android.arch.lifecycle.LiveData;

import java.util.List;

import io.shellee.movies.service.MovieOrder;
import io.shellee.movies.model.Movie;
import io.shellee.movies.repository.MovieRepository;


public class MoviesViewModel extends BaseViewModel {

    private LiveData<List<Movie>> movies;

    public MoviesViewModel(MovieRepository movieRepository){
        super(movieRepository);
    }

    public LiveData<List<Movie>> getMovies(MovieOrder movieOrder, String apiKey, boolean reset) {

        movies =  movieRepository.fetchMovies(movieOrder, apiKey, reset);
        return movies;
    }
}
