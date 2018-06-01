package io.shellee.movies.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.shellee.movies.R;
import io.shellee.movies.di.scopes.MoviesApplicationScope;
import io.shellee.movies.service.MovieService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module( includes = ContextModule.class)
public class MovieServiceModule {
    private final String BASE_URL;

    public MovieServiceModule(Context context) {
        BASE_URL =  context.getResources().getString(R.string.BASE_URL);
    }

    @Provides
    @MoviesApplicationScope
    public MovieService movieService(Retrofit retrofit){
        return retrofit.create(MovieService.class);
    }

    @Provides
    @MoviesApplicationScope
    public Retrofit retrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @MoviesApplicationScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @MoviesApplicationScope
    HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //TODO SET LOGGING BASED ON PREFERENCES
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

}
