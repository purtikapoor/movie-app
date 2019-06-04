package com.my.movieapp;

import com.my.movieapp.database.Genre;
import com.my.movieapp.database.Movie;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Constants {

    // network
    public static final String API_KEY = "baa2758afc2563fff8f747b255da5479";
    public static final String MOVIES_ARRAY_DATA_TAG = "results";
    public static final String GENRES_ARRAY_DATA_TAG = "genres";
    public static final Type MOVIE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Movie>()).getClass();
    public static final Type GENRE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Genre>()).getClass();
    private static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/";
    public static final String SMALL_IMAGE_URL_PREFIX = IMAGE_URL_PREFIX + "w300";
    public static final String BIG_IMAGE_URL_PREFIX = IMAGE_URL_PREFIX + "w500";
    public static final String API_KEY_REQUEST_PARAM = "api_key";
    public static final String LANGUAGE_REQUEST_PARAM = "language";
    public static final String PAGE_REQUEST_PARAM = "page";
    public static final String LANGUAGE = "en";
    public static final int LOADING_PAGE_SIZE = 20;
    // DB
    public static final String DATA_BASE_NAME = "Movies";
    public static final int NUMBERS_OF_THREADS = 3;

    public static String getLanguageFromCode(String code){

        Locale loc = new Locale(code);
        return loc.getDisplayLanguage(loc);

    }

    public static String changeDateFormat(String sourceDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        try {
            sDate = dateFormat.parse(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        return targetFormat.format(sDate);
    }
}
