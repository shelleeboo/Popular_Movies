package io.shellee.movies.di;


import android.arch.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import dagger.Component;
import io.shellee.movies.MoviesApplication;
import io.shellee.movies.di.modules.PicassoModule;
import io.shellee.movies.di.modules.ViewModelFactoryModule;
import io.shellee.movies.di.scopes.MoviesApplicationScope;

/**
 * This is the public interface into the dependency graph at the application level.
 */
@MoviesApplicationScope
@Component(modules = {PicassoModule.class, ViewModelFactoryModule.class})
public interface MoviesApplicationComponent {

    ViewModelProvider.Factory viewModelFactory();
    Picasso picasso();

    void inject(MoviesApplication moviesApplication);
}
