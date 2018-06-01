package io.shellee.movies.service;

import io.shellee.movies.model.MovieApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("/3/movie/{sortOrder}")
    Call<MovieApiResponse> fetchMovies(
            @Path("sortOrder") String sortOrder,
            @Query("api_key") String apiKey);
}
