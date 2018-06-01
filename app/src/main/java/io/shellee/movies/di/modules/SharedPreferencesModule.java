package io.shellee.movies.di.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.ui.posters.MoviesActivity;

@Module(includes = ActivityContextModule.class)
public class SharedPreferencesModule {

    @Provides
    public SharedPreferences sharedPreferences(MoviesActivity activity){
        return PreferenceManager.getDefaultSharedPreferences(activity);
    }
}
