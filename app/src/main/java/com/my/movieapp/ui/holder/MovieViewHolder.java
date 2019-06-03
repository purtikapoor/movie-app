package com.my.movieapp.ui.holder;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.movieapp.Constants;
import com.my.movieapp.database.MovieDatabase;
import com.my.movieapp.ui.ItemClickListener;
import com.my.movieapp.R;
import com.my.movieapp.database.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import static com.my.movieapp.Constants.SMALL_IMAGE_URL_PREFIX;


public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTitle;
        private TextView textViewLanguage;
        private TextView textViewVoteAvg;
        private ImageView imageViewPoster;
        private ImageView imageViewFavourite;

        private Movie movie;
        private ItemClickListener itemClickListener;

        public MovieViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewLanguage = itemView.findViewById(R.id.text_view_language);
            textViewVoteAvg = itemView.findViewById(R.id.text_view_vote_average);
            imageViewPoster = itemView.findViewById(R.id.image_view_poster);
            imageViewFavourite = itemView.findViewById(R.id.image_view_favourite);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);

        }

    public void bindTo(Movie movie) {
        this.movie = movie;
        textViewTitle.setText(movie.getTitle());
        textViewVoteAvg.setText(String.format("%1$,.2f", movie.getVoteAverage()));

        textViewLanguage.setText(movie.getLanguage());
        textViewVoteAvg.setText(String.valueOf(movie.getVoteAverage()));

        if(movie.getIsFavourite()) {
            imageViewFavourite.setImageResource(R.drawable.ic_favorite_red_18dp);
        }
        if(movie.getPosterPath() != null) {
            String poster = SMALL_IMAGE_URL_PREFIX + movie.getPosterPath();
            Picasso.get().load(poster).placeholder(R.drawable.ic_image_place_holder).into(imageViewPoster);
        }
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(movie); // call the onClick in the OnItemClickListener
        }
    }

}
