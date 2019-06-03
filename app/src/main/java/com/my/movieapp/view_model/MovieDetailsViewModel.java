package com.my.movieapp.view_model;


import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.my.movieapp.R;
import com.my.movieapp.database.Genre;
import com.my.movieapp.database.Movie;
import com.my.movieapp.database.MovieDatabase;
import com.my.movieapp.repository.GenreRepository;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MovieDetailsViewModel extends AndroidViewModel {
    final private MutableLiveData movie;
//    private GenreRepository repository;
    final private MovieDatabase database;;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movie = new MutableLiveData<Movie>();
//        repository = GenreRepository.getInstance(application);
        database = MovieDatabase.getInstance(application);
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }

    public void setOnClick(View view, Movie movie) {
        Log.e("clicked", "heart");

        boolean isFavourite = !movie.getIsFavourite();
        movie.setIsFavourite(isFavourite);

        ((ImageView)view).setImageResource(isFavourite ? R.drawable.ic_favorite_red_24dp : R.drawable.ic_favorite_gray_24dp);

        Runnable r = () -> database.movieDao().update(isFavourite, movie.getId());

        Thread newThread= new Thread(r);
        newThread.start();
    }

    /*
    public String mapGenreNames(Movie movie) {
        Log.e("working", "genre");

        Runnable r = new Runnable(){
            @Override
            public void run() {
                ArrayList<Integer> genreIds = movie.getGenres();

                ArrayList<Genre> allGenreArrayList = new ArrayList<Genre>(database.genreDao().getAllGenre());

                allGenreArrayList
                    .stream()
                    .filter(genre -> genreIds.contains(genre.getGenreId()))
                    .map(genre -> String.valueOf(genre.getName()))
                    .collect(Collectors.joining(", "));
            }
        };
        Thread newThread= new Thread(r);
        newThread.start();

    }
     */

    public String mapGenreNames(Movie movie) {

        try {
            ArrayList<Integer> genreIds = new ArrayList<Integer>(movie.getGenreIds());
            Callable<ArrayList<Genre>> callable = new Callable<ArrayList<Genre>>() {

                @Override
                public ArrayList<Genre> call() throws Exception {
                    return new ArrayList<Genre>(database.genreDao().getAllGenre());
                }
            };

            Future<ArrayList<Genre>> future = Executors.newSingleThreadExecutor().submit(callable);

            return future.get()
                    .stream()
                    .filter(genre -> genreIds.contains(genre.getId()))
                    .map(genre -> String.valueOf(genre.getName()))
                    .collect(Collectors.joining(", "));

        } catch (Exception e) {
            return null;
        }
    }

}