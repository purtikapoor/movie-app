package com.my.movieapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static RetrofitClientInstance mInstance;
    private Retrofit retrofit;

    public GetDataService getData() {
        return retrofit.create(GetDataService.class);
    }


    public static GetDataService getClient(Type type, Object typeAdapter) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                // we remove from the response some wrapper tags from our movies array
                .registerTypeAdapter(type, typeAdapter)
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASE_URL);

        return builder.build().create(GetDataService.class);

    }
}
