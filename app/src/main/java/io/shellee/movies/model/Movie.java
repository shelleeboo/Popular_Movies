package io.shellee.movies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Entity
@Data
public class Movie {

    @Expose
    @SerializedName("vote_count")
    private Integer voteCount;
    @Expose @PrimaryKey
    private Integer id;
    @Expose
    private Boolean video;
    @Expose
    @SerializedName("vote_average")
    private Float userRating;
    @Expose
    private String title;
    @Expose
    private Float popularity;
    @Expose
    @SerializedName("poster_path")
    private String poster;
    @Expose
    @SerializedName("original_language")
    private String originalLanguage;
    @Expose
    @SerializedName("original_title")
    private String originalTitle;
    @Ignore
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @Expose
    @SerializedName("backdrop_path")
    private String backdrop;
    @Expose
    @SerializedName("adult")
    private Boolean adult;
    @Expose
    @SerializedName("overview")
    private String synopsis;
    @Expose
    @SerializedName("release_date")
    private String releaseDate;
    private LocalTime timeToRefresh;

    public Movie() {
    }

    public Movie(Integer voteCount, Integer id, Boolean video, Float userRating, String title, Float popularity, String poster, String originalLanguage, String originalTitle, List<Integer> genreIds, String backdrop, Boolean adult, String synopsis, String releaseDate, LocalTime timeToRefresh) {
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.userRating = userRating;
        this.title = title;
        this.popularity = popularity;
        this.poster = poster;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdrop = backdrop;
        this.adult = adult;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.timeToRefresh = timeToRefresh;
    }
    @Ignore
    public Movie(Integer voteCount, Boolean video, Float userRating, String title, Float popularity, String poster, String originalLanguage, String originalTitle, List<Integer> genreIds, String backdrop, Boolean adult, String synopsis, String releaseDate, LocalTime timeToRefresh) {
        this.voteCount = voteCount;
        this.video = video;
        this.userRating = userRating;
        this.title = title;
        this.popularity = popularity;
        this.poster = poster;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdrop = backdrop;
        this.adult = adult;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.timeToRefresh = timeToRefresh;
    }



}


