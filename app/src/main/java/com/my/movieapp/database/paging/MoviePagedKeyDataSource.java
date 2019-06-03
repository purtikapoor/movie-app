package com.my.movieapp.database.paging;

import com.my.movieapp.database.Movie;
import com.my.movieapp.database.MovieDao;
import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class MoviePagedKeyDataSource extends PageKeyedDataSource<String, Movie> {

        public static final String TAG = MoviePagedKeyDataSource.class.getSimpleName();
        private final MovieDao movieDao;

        public MoviePagedKeyDataSource(MovieDao dao) {
                movieDao = dao;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Movie> callback) {
            Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
            List<Movie> movies = movieDao.getAllMovies();
            if(movies.size() != 0) {
                callback.onResult(movies, "0", "1");
            }
        }

        @Override
        public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Movie> callback) {
        }

        @Override
        public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Movie> callback) {
        }

}
