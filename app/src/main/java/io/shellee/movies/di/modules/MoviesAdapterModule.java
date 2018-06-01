package io.shellee.movies.di.modules;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.di.scopes.ActivityScope;
import io.shellee.movies.ui.posters.MoviesActivity;
import io.shellee.movies.ui.posters.MoviesAdapter;

@Module(includes = {ActivityContextModule.class})
public class MoviesAdapterModule {

    @Provides
    @ActivityScope
    public MoviesAdapter moviesAdapter(MoviesAdapter.ItemClickListener itemClickListener, Picasso picasso){
        return new MoviesAdapter(itemClickListener, picasso);
    }

    @Provides
    @ActivityScope
    public MoviesAdapter.ItemClickListener itemClickListener(MoviesActivity moviesActivity) {
        return moviesActivity;
    }
}
