package io.shellee.movies.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.di.scopes.MoviesApplicationScope;


/**
 * This is pretty much a standard ContextModule.
 */
@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context) { this.context = context; }

    @Provides
    @MoviesApplicationScope
    public Context context(){ return context; }

}
