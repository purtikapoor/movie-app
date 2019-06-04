package com.my.movieapp.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.movieapp.R;
import com.my.movieapp.database.Movie;
import com.my.movieapp.ui.ItemClickListener;
import com.my.movieapp.ui.adapter.MovieAdapter;
import com.my.movieapp.view_model.MovieDetailsViewModel;
import com.my.movieapp.view_model.MovieListViewModel;


public class MoviesListFragment extends Fragment implements ItemClickListener {

    protected MovieListViewModel viewModel;
    private MovieDetailsViewModel detailsViewModel;

    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewModel = ViewModelProviders.of(getActivity()).get(MovieListViewModel.class);
        observersRegisters();
    }

    private void observersRegisters() {

        final MovieAdapter pageListAdapter = new MovieAdapter(this);
        viewModel.getMovies().observe(this, pageListAdapter::submitList);
        viewModel.getNetworkState().observe(this, networkState -> {
            pageListAdapter.setNetworkState(networkState);
        });

        recyclerView.setAdapter(pageListAdapter);
        detailsViewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(Movie movie) {
        detailsViewModel.getMovie().postValue(movie);
        if (!detailsViewModel.getMovie().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }
}