package io.shellee.movies.vm;

import android.arch.lifecycle.LiveData;

import io.shellee.movies.model.Movie;
import io.shellee.movies.repository.MovieRepository;

public class MovieDetailViewModel extends BaseViewModel {
    private LiveData<Movie> movie;

    public MovieDetailViewModel(MovieRepository movieRepository) {
        super(movieRepository);
    }

    public LiveData<Movie> getMovie(Integer movieId) {
        movie = movieRepository.fetchMovie(movieId);
        return movie;
    }
}
