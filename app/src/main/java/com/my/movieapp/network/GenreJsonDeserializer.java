package com.my.movieapp.network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.my.movieapp.Constants;
import com.my.movieapp.database.Genre;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GenreJsonDeserializer implements JsonDeserializer {

    private static String TAG = GenreJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Genre> genre = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray genreJsonArray = jsonObject.getAsJsonArray(Constants.GENRES_ARRAY_DATA_TAG);
            genre = new ArrayList<>(genreJsonArray.size());
            for (int i = 0; i < genreJsonArray.size(); i++) {
                // adding the converted wrapper to our container
                Genre dematerialized = context.deserialize(genreJsonArray.get(i), Genre.class);
                genre.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Genre element: %s", json.toString()));
        }
        return genre;
    }
}

