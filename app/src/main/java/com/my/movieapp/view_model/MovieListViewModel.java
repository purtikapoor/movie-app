package com.my.movieapp.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.my.movieapp.repository.GenreRepository;
import com.my.movieapp.repository.MovieRepository;
import com.my.movieapp.database.Movie;
import com.my.movieapp.network.NetworkState;

import java.util.ArrayList;

public class MovieListViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private GenreRepository genreRepository;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance(application);
        genreRepository = GenreRepository.getInstance(application);
    }

    public LiveData<PagedList<Movie>> getMovies() {
        return repository.getAllMovies();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}

