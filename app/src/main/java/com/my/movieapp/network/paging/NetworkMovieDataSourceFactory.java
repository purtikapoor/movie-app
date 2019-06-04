package com.my.movieapp.network.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.my.movieapp.database.Movie;

import rx.subjects.ReplaySubject;

/*
    Responsible for creating the DataSource so we can give it to the PagedList.
 */
public class NetworkMovieDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetworkMovieDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetworkMoviePageKeyedDataSource> networkStatus;
    private NetworkMoviePageKeyedDataSource moviesPageKeyedDataSource;
    public NetworkMovieDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        moviesPageKeyedDataSource = new NetworkMoviePageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(moviesPageKeyedDataSource);
        return moviesPageKeyedDataSource;
    }

    public MutableLiveData<NetworkMoviePageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Movie> getMovies() {
        return moviesPageKeyedDataSource.getMovies();
    }

}
