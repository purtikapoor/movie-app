package com.my.movieapp.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM genre")
    List<Genre> getAllGenre();


    @Query("SELECT * FROM genre WHERE id =:id")
    Genre getGenreDetails(Integer id);

    /**
     * Insert a genre in the database. If the genre already exists, ignore it.
     *
     * @param genre the genre to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGenre(Genre genre);


}
