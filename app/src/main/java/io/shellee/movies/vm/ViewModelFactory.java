package io.shellee.movies.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.shellee.movies.repository.MovieRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final MovieRepository repo;

    public ViewModelFactory(MovieRepository repo) {
        this.repo = repo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            return (T) new MoviesViewModel(repo);
        } else if (modelClass.isAssignableFrom(MovieDetailViewModel.class)) {
            return (T) new MovieDetailViewModel(repo);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
