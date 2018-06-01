package io.shellee.movies.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.di.qualifiers.ActivityContext;
import io.shellee.movies.di.scopes.ActivityScope;
import io.shellee.movies.ui.posters.MoviesActivity;

@Module
public class ActivityContextModule {
    private final MoviesActivity activity;
    private final Context context;

    public ActivityContextModule(MoviesActivity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Provides
    @ActivityScope
    public MoviesActivity moviesActivity(){
        return activity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context context(){
        return context;
    }

}
