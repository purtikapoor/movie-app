package com.my.movieapp.network.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.my.movieapp.database.Movie;
import com.my.movieapp.network.GetDataService;
import com.my.movieapp.network.MovieJsonDeserializer;
import com.my.movieapp.network.NetworkState;
import com.my.movieapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

import static com.my.movieapp.Constants.API_KEY;
import static com.my.movieapp.Constants.LANGUAGE;
import static com.my.movieapp.Constants.MOVIE_ARRAY_LIST_CLASS_TYPE;

public class NetworkMoviePageKeyedDataSource extends PageKeyedDataSource<String, Movie> {

    private static final String TAG = NetworkMoviePageKeyedDataSource.class.getSimpleName();
    private final GetDataService moviesService;
    private final MutableLiveData networkState;
    private final ReplaySubject<Movie> moviesObservable;

    NetworkMoviePageKeyedDataSource() {
        moviesService = RetrofitClientInstance.getClient(MOVIE_ARRAY_LIST_CLASS_TYPE, new MovieJsonDeserializer());
        networkState = new MutableLiveData();
        moviesObservable = ReplaySubject.create();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Movie> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        Call<ArrayList<Movie>> callBack = moviesService.getAllMovies(API_KEY, LANGUAGE, 1);
        callBack.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(moviesObservable::onNext);
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Movie> getMovies() {
        return moviesObservable;
    }


    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Movie> callback) {
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        Call<ArrayList<Movie>> callBack = moviesService.getAllMovies(API_KEY, LANGUAGE,page.get());
        callBack.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(),Integer.toString(page.get()+1));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(moviesObservable::onNext);
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(),Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Movie> callback) {

    }
}
