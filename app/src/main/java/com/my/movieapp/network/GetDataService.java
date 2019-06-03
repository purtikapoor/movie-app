package com.my.movieapp.network;

import com.my.movieapp.database.Genre;
import com.my.movieapp.database.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.my.movieapp.Constants.API_KEY_REQUEST_PARAM;
import static com.my.movieapp.Constants.LANGUAGE_REQUEST_PARAM;
import static com.my.movieapp.Constants.PAGE_REQUEST_PARAM;

public interface GetDataService {


    @GET("movie/now_playing")
    Call<ArrayList<Movie>> getAllMovies(@Query(API_KEY_REQUEST_PARAM) String apiKey,
                                        @Query(LANGUAGE_REQUEST_PARAM) String language,
                                        @Query(PAGE_REQUEST_PARAM) int page);
    @GET("genre/movie/list")
    Call<ArrayList<Genre>> getAllGenres(@Query(API_KEY_REQUEST_PARAM) String apiKey);
}

