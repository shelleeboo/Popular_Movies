package io.shellee.movies.di;


import dagger.Component;
import io.shellee.movies.di.scopes.ActivityScope;
import io.shellee.movies.ui.details.MovieDetailActivity;

@ActivityScope
@Component(dependencies = MoviesApplicationComponent.class)
public interface MovieDetailActivityComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
