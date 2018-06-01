package io.shellee.movies.ui.details;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.shellee.movies.MoviesApplication;
import io.shellee.movies.R;
import io.shellee.movies.databinding.ActivityMovieDetailBinding;
import io.shellee.movies.di.DaggerMovieDetailActivityComponent;
import io.shellee.movies.di.MovieDetailActivityComponent;
import io.shellee.movies.model.Movie;
import io.shellee.movies.util.NetworkUtils;
import io.shellee.movies.vm.MovieDetailViewModel;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extraMovieId";
    public static final String INSTANCE_MOVIE_ID = "instanceMovieId";
    public static final String EXTRA_API_KEY = "extraApiKey";

    private static final int DEFAULT_MOVIE_ID = -1;
    private int movieId = DEFAULT_MOVIE_ID;
    private ActivityMovieDetailBinding binding;

    MovieDetailActivityComponent component;
    MovieDetailViewModel viewModel;

    @Inject Picasso picasso;
    @Inject ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        inject();
        restoreSavedInstanceState(savedInstanceState);
        setupViewModel();
        processIntent();
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_MOVIE_ID)) {
            movieId = savedInstanceState.getInt(INSTANCE_MOVIE_ID, DEFAULT_MOVIE_ID);
        }
    }

    private void inject() {
        component = DaggerMovieDetailActivityComponent.builder()
                .moviesApplicationComponent(MoviesApplication.get(this).getApplicationComponent())
                .build();
        component.inject(this);
    }

    private void processIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_ID)) {
            if (movieId == DEFAULT_MOVIE_ID) {
                movieId = intent.getIntExtra(EXTRA_MOVIE_ID, DEFAULT_MOVIE_ID);
                viewModel.getMovie(movieId).observe(this, this::populateUI);
            }
        }
    }

    private void populateUI(Movie movie) {
        binding.title.setText(movie.getTitle());
        binding.synopsis.setText(movie.getSynopsis());
        binding.votes.setText(String.valueOf(movie.getVoteCount()));
        binding.releaseDate.setText(movie.getReleaseDate());
        picasso.load(NetworkUtils.buildUri(movie.getBackdrop(), getString(R.string.imageSize)))
                .into(binding.backdropIv);
        // Left the placeholder and error off here for visual effect

        picasso.load(NetworkUtils.buildUri(movie.getPoster(), getString(R.string.imageSize)))
                .error(R.drawable.ic_cinema)
                .placeholder(R.drawable.ic_cinema)
                .into(binding.posterIv);
    }

    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(MovieDetailViewModel.class);
    }
}
