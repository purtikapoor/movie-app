package com.my.movieapp.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.my.movieapp.Constants;
import com.my.movieapp.view_model.MovieDetailsViewModel;
import com.my.movieapp.R;

import com.my.movieapp.databinding.MovieDetailsBinding;
import com.squareup.picasso.Picasso;

import static com.my.movieapp.Constants.BIG_IMAGE_URL_PREFIX;

public class MovieDetailsFragment extends Fragment {

    private MovieDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        MovieDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
        View view = binding.getRoot();
        viewModel.getMovie().observe(this, binding::setMovie);

        binding.setMovieDetailsViewModel(viewModel);

        return view;
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        if(url != null) {
            Picasso.get().load(BIG_IMAGE_URL_PREFIX + url).into(view);
        }
    }


    public String getLanguage(String languageCode) {
        return Constants.getLanguageFromCode(languageCode);
    }
}
