package io.shellee.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieApiResponse {

    public Long page;
    @SerializedName("total_results")
    public Long totalResults;
    @SerializedName("total_pages")
    public Long totalPages;
    @Expose
    @SerializedName("results")
    public List<Movie> movies;

}

