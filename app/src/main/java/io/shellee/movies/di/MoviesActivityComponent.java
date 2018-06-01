package io.shellee.movies.di;

import dagger.Component;
import io.shellee.movies.di.modules.MoviesAdapterModule;
import io.shellee.movies.di.modules.SharedPreferencesModule;
import io.shellee.movies.ui.posters.MoviesActivity;
import io.shellee.movies.di.scopes.ActivityScope;

@ActivityScope
@Component(modules = { SharedPreferencesModule.class, MoviesAdapterModule.class}, dependencies = MoviesApplicationComponent.class)
public interface MoviesActivityComponent {

    void inject(MoviesActivity moviesActivity);

}
