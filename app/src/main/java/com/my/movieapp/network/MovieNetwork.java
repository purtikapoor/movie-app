package com.my.movieapp.network;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.my.movieapp.database.Movie;
import com.my.movieapp.network.paging.NetworkMovieDataSourceFactory;
import com.my.movieapp.network.paging.NetworkMoviePageKeyedDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.my.movieapp.Constants.LOADING_PAGE_SIZE;
import static com.my.movieapp.Constants.NUMBERS_OF_THREADS;

public class MovieNetwork {

    final private static String TAG = MovieNetwork.class.getSimpleName();
    final private LiveData<PagedList<Movie>> moviesPaged;
    final private LiveData<NetworkState> networkState;

    public MovieNetwork(NetworkMovieDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Movie> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NetworkMoviePageKeyedDataSource, LiveData<NetworkState>>)
                        NetworkMoviePageKeyedDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }

    public LiveData<PagedList<Movie>> getPagedMovies(){
        return moviesPaged;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
