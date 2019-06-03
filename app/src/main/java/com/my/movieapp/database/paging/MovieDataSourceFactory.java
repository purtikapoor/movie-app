package com.my.movieapp.database.paging;


import androidx.paging.DataSource;

import com.my.movieapp.database.MovieDao;


public class MovieDataSourceFactory extends DataSource.Factory {

    private static final String TAG = MovieDataSourceFactory.class.getSimpleName();
    private MoviePagedKeyDataSource moviePagedKeyDataSource;

    public MovieDataSourceFactory(MovieDao dao) {
        moviePagedKeyDataSource = new MoviePagedKeyDataSource(dao);
    }

    @Override
    public DataSource create() {
        return moviePagedKeyDataSource;
    }

}