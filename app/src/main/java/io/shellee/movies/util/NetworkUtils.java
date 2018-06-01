package io.shellee.movies.util;

import android.net.Uri;

public class NetworkUtils {

    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";

       public static Uri buildUri(String imageFileName, String imageSize){

        return Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(imageSize)
                .appendPath(imageFileName.substring(1))
                .build();

    }



}
