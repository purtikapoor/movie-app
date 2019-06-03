package com.my.movieapp;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter
    public String fromGenreIdsArrayList(List<Integer> genreIds) {
        if (genreIds == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>(){}.getType();
        return gson.toJson(genreIds, type);
    }

    @TypeConverter
    public List<Integer> toGenreIdsArrayList(String genreIdsString) {
        if (genreIdsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(genreIdsString, type);
    }
}
