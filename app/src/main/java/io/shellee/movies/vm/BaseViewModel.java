package io.shellee.movies.vm;

import android.arch.lifecycle.ViewModel;

import io.shellee.movies.repository.MovieRepository;

public class BaseViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    protected MovieRepository movieRepository;


    public BaseViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
}
