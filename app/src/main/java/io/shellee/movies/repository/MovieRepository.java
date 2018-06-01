package io.shellee.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


import javax.inject.Inject;

import io.shellee.movies.model.Movie;
import io.shellee.movies.model.MovieApiResponse;
import io.shellee.movies.service.MovieOrder;
import io.shellee.movies.service.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private final MovieService moviesService;
    private final MovieDao moviesDao;

    @Inject
    public MovieRepository(MovieService movieService, MovieDao movieDao) {
        this.moviesDao = movieDao;
        this.moviesService = movieService;
    }

    private void refreshMovie(MovieOrder movieOrder, String apiKey , boolean reset) {
        final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
        Log.d(TAG, "refreshMovie:Starting. ");

         if (reset || isStale()) {

            moviesService.fetchMovies(movieOrder.getValue(), apiKey).enqueue(new Callback<MovieApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                    Log.d(TAG, "onResponse: " + call.request().url().toString());
                    if (response.isSuccessful()) {
                        MovieApiResponse apiResponse = response.body();
                        Log.d(TAG, "onResponse: " + (apiResponse != null ? apiResponse.movies.size() : 0));
                        movies.setValue(Objects.requireNonNull(apiResponse).movies);
                        updateMovieDatabase(movies, reset);

                    } else {
                        int statusCode = response.code();
                        Log.d(TAG, "onResponse: " + statusCode);
                        // handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {

                    Log.d(TAG, "onFailure: Call to movie DB failed. ");
                    Log.d(TAG, call.request().url().toString());
                    Log.d(TAG, "onFailure:  " + t.getMessage());
                }
            });

        }

    }

    private void updateMovieDatabase(MutableLiveData<List<Movie>> movies, boolean reset) {

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (reset) moviesDao.deleteAll();
            moviesDao.save(Objects.requireNonNull(movies.getValue()).toArray(new Movie[movies.getValue().size()]));
        });

    }

    private boolean isStale() {
        final boolean[] currentList = {false};
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean currentList2 = moviesDao.hasMovies(LocalTime.now().minusMinutes(1).toNanoOfDay());
            Log.d(TAG, "run: isCurrent " + currentList2);
            currentList[0] = currentList2;
        });
        return currentList[0];
    }

    public LiveData<Movie> fetchMovie(Integer movieId) {
        return moviesDao.findById(movieId);
    }

    public LiveData<List<Movie>> fetchMovies(MovieOrder movieOrder, String apiKey, boolean reset) {
        Log.d(TAG, "fetchMovies: It's fetching the data.");
        refreshMovie(movieOrder, apiKey, reset);
        switch (movieOrder) {
            case top_rated:
                return moviesDao.loadMoviesByUserRating();
            case popular:
                return moviesDao.loadMoviesByPopularity();
            default:
                throw new IllegalArgumentException("Not Valid Movie Order Found");
        }
    }
}

