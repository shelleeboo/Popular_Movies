package io.shellee.movies.di.modules;

import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.di.scopes.MoviesApplicationScope;

@Module(includes = ContextModule.class)
public class PicassoModule {

    @Provides
    @MoviesApplicationScope
    public Picasso picasso(Context context){
        return new Picasso.Builder(context).build();
    }
}
