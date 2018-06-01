package io.shellee.movies.ui.posters;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import io.shellee.movies.MoviesApplication;
import io.shellee.movies.R;
import io.shellee.movies.databinding.ActivityMoviesBinding;
import io.shellee.movies.di.DaggerMoviesActivityComponent;
import io.shellee.movies.di.MoviesActivityComponent;
import io.shellee.movies.di.MoviesApplicationComponent;
import io.shellee.movies.di.modules.ActivityContextModule;
import io.shellee.movies.service.MovieOrder;
import io.shellee.movies.ui.details.MovieDetailActivity;
import io.shellee.movies.ui.settings.SettingsActivity;
import io.shellee.movies.vm.MoviesViewModel;

public class MoviesActivity extends AppCompatActivity
        implements MoviesAdapter.ItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = MoviesActivity.class.getSimpleName();
    private static final int NUM_OF_GRID_COLUMNS_DEFAULT = 2;
    private static final int NUM_OF_GRID_COLUMNS_LANDSCAPE = 3;

    private ActivityMoviesBinding binding;
    private String apiKey;
    private MoviesViewModel moviesViewModel;

    MoviesApplicationComponent component;
    MoviesActivityComponent activityComponent;

    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject MoviesAdapter adapter;
    @Inject SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);

        inject();
        setupRecyclerView();
        setupViewModel();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }


    private void inject(){
        component = MoviesApplication.get(this).getApplicationComponent();
        activityComponent = DaggerMoviesActivityComponent.builder()
                .activityContextModule(new ActivityContextModule(this, this))
                .moviesApplicationComponent(component)
                .build();
        activityComponent.inject(this);
    }

    private void setupRecyclerView() {
        //if landscape set number of grid columns to 3 otherwise it's 2
        binding.rvMovies.setLayoutManager(
                new GridLayoutManager(this,
                        isLandscape()
                                ? NUM_OF_GRID_COLUMNS_LANDSCAPE
                                : NUM_OF_GRID_COLUMNS_DEFAULT));
        adapter.setImageSize(getString(R.string.imageSize));
        binding.rvMovies.setAdapter(adapter);
        binding.rvMovies.setHasFixedSize(true);
    }

    private boolean isLandscape(){
        int orientation = getResources().getConfiguration().orientation;

        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setupViewModel(){

        apiKey = getString(R.string.API_KEY);
        moviesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MoviesViewModel.class);
        //todo don't reload if there is no network connection.
        loadViewModel(getMovieOrderPreference() ,true);
    }

    private void loadViewModel(MovieOrder movieOrder, boolean resetList) {

        moviesViewModel
                .getMovies(movieOrder, apiKey, resetList)
                .observe(this, movies -> {
                    Log.d(TAG, "retrieve Movies: " + (movies != null));
                    adapter.setMoviePosterItems(movies);
                });
    }

    private MovieOrder getMovieOrderPreference(){
        String preference = sharedPreferences.getString(getString(R.string.movieOrder),MovieOrder.popular.getValue());
        return MovieOrder.valueOf(preference);
    }
    @Override
    public void onItemClickListener(int movieId) {
        Log.d(TAG, "onItemClickListener: An item has been clicked : " + movieId);
        Intent intent = new Intent(getBaseContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieId);
        intent.putExtra(MovieDetailActivity.EXTRA_API_KEY, apiKey);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.action_settings){
            Intent settingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if( key.equals(getString(R.string.movieOrder))){
           MovieOrder movieOrder = getMovieOrderPreference();
           loadViewModel(movieOrder, true);
        }

    }
}
