package io.shellee.movies.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.di.scopes.MoviesApplicationScope;
import io.shellee.movies.repository.MovieDao;
import io.shellee.movies.repository.MovieDatabase;
import io.shellee.movies.repository.MovieRepository;
import io.shellee.movies.service.MovieService;
import io.shellee.movies.vm.ViewModelFactory;

@Module(includes = {MovieServiceModule.class, ContextModule.class})
public class ViewModelFactoryModule {

    @Provides
    @MoviesApplicationScope
    public MovieRepository movieRepository(MovieService movieService, MovieDao movieDao){
        return new MovieRepository(movieService,movieDao);
    }

    @Provides
    @MoviesApplicationScope
    public MovieDao movieDao(MovieDatabase database){
        return database.movieDao();
    }

    @Provides
    @MoviesApplicationScope
    public MovieDatabase movieDatabase(Context context){
        return Room.databaseBuilder(context
                , MovieDatabase.class
                , "movies.db"
        ).build();
    }
    @Provides
    @MoviesApplicationScope
    public ViewModelProvider.Factory viewModelFactory(MovieRepository repository){
        return new ViewModelFactory(repository);
    }
}
