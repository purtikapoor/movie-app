package com.my.movieapp.database;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.my.movieapp.DataConverter;
import com.my.movieapp.database.paging.MovieDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.my.movieapp.Constants.DATA_BASE_NAME;
import static com.my.movieapp.Constants.NUMBERS_OF_THREADS;

@Database(entities = {Movie.class, Genre.class}, version = 1)
@TypeConverters({DataConverter.class})
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;
    public abstract MovieDao movieDao();
    public abstract GenreDao genreDao();
    private static final Object sLock = new Object();
    private LiveData<PagedList<Movie>> moviesPaged;

    public static MovieDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        MovieDataSourceFactory dataSourceFactory = new MovieDataSourceFactory(movieDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<Movie>> getMovies() {
        return moviesPaged;
    }
}