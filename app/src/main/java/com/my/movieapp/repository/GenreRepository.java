package com.my.movieapp.repository;

import android.content.Context;
import android.util.Log;

import com.my.movieapp.database.Genre;
import com.my.movieapp.database.MovieDatabase;
import com.my.movieapp.network.GenreJsonDeserializer;
import com.my.movieapp.network.GetDataService;
import com.my.movieapp.network.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.my.movieapp.Constants.API_KEY;
import static com.my.movieapp.Constants.GENRE_ARRAY_LIST_CLASS_TYPE;

public class GenreRepository implements Callback<ArrayList<Genre>> {

    private static final String TAG = com.my.movieapp.repository.GenreRepository.class.getSimpleName();
    private static com.my.movieapp.repository.GenreRepository instance;
    GetDataService getDataService;

    final private MovieDatabase database;

    @Override
    public void onResponse(Call<ArrayList<Genre>> call, Response<ArrayList<Genre>> response) {
        if(response.isSuccessful()) {
            Log.e("ERROR", "GOT RESPONSE");
            ArrayList<Genre> changesList = response.body();

            Runnable r = () -> changesList.forEach(genre -> database.genreDao().insertGenre(genre));;

            Thread newThread= new Thread(r);
            newThread.start();
        } else {
            Log.e("ERROR", response.message());
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Genre>> call, Throwable t) {
        Log.e("ERROR", t.getMessage());
    }

    private GenreRepository(Context context) {

        database = MovieDatabase.getInstance(context.getApplicationContext());

        getDataService = RetrofitClientInstance.getClient(GENRE_ARRAY_LIST_CLASS_TYPE, new GenreJsonDeserializer());
        Call<ArrayList<Genre>> call = getDataService.getAllGenres(API_KEY);
        call.enqueue(this);

        getDataService.getAllGenres(API_KEY);
    }

    public static com.my.movieapp.repository.GenreRepository getInstance(Context context){
        if(instance == null){
            instance = new com.my.movieapp.repository.GenreRepository(context);
        }
        return instance;
    }

}
