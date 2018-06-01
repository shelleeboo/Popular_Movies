package io.shellee.movies.ui.posters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.shellee.movies.R;
import io.shellee.movies.databinding.MoviesGridItemBinding;
import io.shellee.movies.model.Movie;
import io.shellee.movies.util.NetworkUtils;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviePosterViewHolder>{
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private final ItemClickListener listener;

    private final Picasso picasso;
    private String imageSize;


  @Inject
    public MoviesAdapter(ItemClickListener listener, Picasso picasso) {
        this.listener = listener;
        this.picasso = picasso;
    }
    private List<Movie> moviePosterItems = new ArrayList<>();

    public void setMoviePosterItems(List<Movie> moviePosterItems) {
        Log.d(TAG, "Movie List has items: " + !moviePosterItems.isEmpty());
        this.moviePosterItems = moviePosterItems;
        notifyDataSetChanged();
    }
    public void setImageSize(String imageSize){
        this.imageSize = imageSize;
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutId = R.layout.movies_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        MoviesGridItemBinding binding = DataBindingUtil
                .inflate(inflater
                        , layoutId
                        , parent
                        , shouldAttachToParentImmediately
                );
     return new MoviePosterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
         holder.bind(moviePosterItems.get(position));
    }

    public interface ItemClickListener {
        void onItemClickListener(int movieId);
    }

    @Override
    public int getItemCount() {
        return moviePosterItems.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final String TAG = MoviePosterViewHolder.class.getSimpleName();
        private final MoviesGridItemBinding binding;

        MoviePosterViewHolder(MoviesGridItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
            binding.rel.setOnClickListener(this);
         }
        void bind(Movie movie){

            Uri moviePoster = NetworkUtils.buildUri(movie.getPoster(), imageSize);

            Log.d(TAG, "bind: " + moviePoster.toString());

            picasso.load(moviePoster)
                    .error(R.drawable.ic_cinema)
                    .placeholder(R.drawable.ic_cinema)
                    .into(binding.ivMoviePoster);
            binding.txTitle.setText(movie.getTitle());

        }


        @Override
        public void onClick(View view) {
            int movieId = moviePosterItems.get(getAdapterPosition()).getId();
            listener.onItemClickListener(movieId);
        }
    }

 }
