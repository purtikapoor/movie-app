package com.my.movieapp.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> getAllMovies();


    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovieById(Integer id);

    @Query("SELECT is_favourite FROM movie WHERE id = :id")
    boolean getIsFavouriteFromDb(Integer id);


    /**
     * Updating isFavourite
     * By movieID
     */
    @Query("UPDATE movie SET is_favourite=:favourite WHERE id = :id")
    void update(boolean favourite, Integer id);

    /**
     * Insert a movie in the database. If the movie already exists, ignore it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);



}
