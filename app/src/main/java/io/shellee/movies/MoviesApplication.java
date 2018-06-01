package io.shellee.movies;

import android.app.Activity;
import android.app.Application;

import io.shellee.movies.di.DaggerMoviesApplicationComponent;
import io.shellee.movies.di.MoviesApplicationComponent;
import io.shellee.movies.di.modules.ContextModule;
import io.shellee.movies.di.modules.MovieServiceModule;

public class MoviesApplication extends Application {

    private MoviesApplicationComponent component;

    public static MoviesApplication get(Activity activity) {
        return (MoviesApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerMoviesApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .movieServiceModule(new MovieServiceModule(this))
                .build();

        component.inject(this);
    }

    public MoviesApplicationComponent getApplicationComponent() {
        return component;
    }
}
