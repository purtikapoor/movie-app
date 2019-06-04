package com.my.movieapp.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.my.movieapp.database.Movie;
import com.my.movieapp.database.MovieDatabase;
import com.my.movieapp.network.MovieNetwork;
import com.my.movieapp.network.NetworkState;
import com.my.movieapp.network.paging.NetworkMovieDataSourceFactory;

import java.util.List;

import rx.schedulers.Schedulers;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private static MovieRepository instance;
    final private MovieNetwork network;
    final private MovieDatabase database;
    final private MediatorLiveData liveDataMerger;

    public MovieRepository(Context context) {

        NetworkMovieDataSourceFactory dataSourceFactory = new NetworkMovieDataSourceFactory();

        network = new MovieNetwork(dataSourceFactory, boundaryCallback);
        database = MovieDatabase.getInstance(context.getApplicationContext());
        // when we get new movies from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedMovies(), value -> {
            liveDataMerger.setValue(value); //I have changed this
            Log.d(TAG+" 123", value.toString());
        });

        // save the movies into db
        dataSourceFactory.getMovies().
                observeOn(Schedulers.io()).
                subscribe(movie -> {
                    List<Movie> movies = database.movieDao().getAllMovies();
                    if (!movies.isEmpty()) {
                        Movie m = database.movieDao().getMovieById(movie.getId());
                        if (m != null) {
                            movie.setIsFavourite(m.getIsFavourite());
                        }
                    }

                    database.movieDao().insertMovie(movie);
                });

    }

    private PagedList.BoundaryCallback<Movie> boundaryCallback = new PagedList.BoundaryCallback<Movie>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getMovies(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(database.getMovies());
            });
        }
    };

    public static MovieRepository getInstance(Context context){
        if(instance == null){
            instance = new MovieRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<Movie>> getAllMovies(){
        return  liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}
