package com.my.movieapp.database;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.my.movieapp.Constants;
import com.my.movieapp.DataConverter;

import java.util.List;

@Entity(tableName = "movie")
public class Movie extends BaseObservable {



    //On click (Movie title, language, Vote average, vote count, Poster image,
    //favourite if marked by user, release date, popularity, genre, overview, adult)

        @PrimaryKey()
        @ColumnInfo(name = "id") @SerializedName(value="id") private Integer mId;

        @ColumnInfo(name = "title") @SerializedName(value="title") private String mTitle;
        @ColumnInfo(name = "language") @SerializedName(value="original_language") private String mLanguage;
        @ColumnInfo(name = "poster_path") @SerializedName(value="poster_path") private String mPosterPath;
        @ColumnInfo(name = "backdrop_path") @SerializedName(value="backdrop_path") private String mBackdropPath;
        @ColumnInfo(name = "vote_average") @SerializedName(value="vote_average") private Float mVoteAverage;
        @ColumnInfo(name = "vote_count") @SerializedName(value="vote_count") private int mVoteCount;
        @ColumnInfo(name = "is_favourite") @SerializedName(value="is_favourite") private boolean mIsFavourite;
        @ColumnInfo(name = "release_date") @SerializedName(value="release_date") private String mReleaseDate;
        @ColumnInfo(name = "popularity") @SerializedName(value="popularity") private Float mPopularity;
        @ColumnInfo(name = "adult") @SerializedName(value="adult") private boolean mAdult;
        @ColumnInfo(name = "overview") @SerializedName(value="overview") private String mOverview;

        @TypeConverters(DataConverter.class)
        @ColumnInfo(name = "genre_ids") @SerializedName(value="genre_ids") private List<Integer> mGenreIds;


        // use for ordering the items in view
        public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                        return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                        return oldItem.getId().equals(newItem.getId());
                }
        };

        @Bindable
        public Integer getId() {
                return mId;
        }
        public void setId(Integer mId) {
                this.mId = mId;
        }

        @Bindable
        public int getVoteCount() {
                return mVoteCount;
        }
        public void setVoteCount(int mVoteCount) {
                this.mVoteCount = mVoteCount;
        }

        @Bindable
        @NonNull
        public boolean getIsFavourite() {
                return mIsFavourite;
        }
        public void setIsFavourite(boolean mIsFavourite) {
                this.mIsFavourite = mIsFavourite;
        }

        @Bindable
        public Float getVoteAverage() {
                return mVoteAverage;
        }
        public void setVoteAverage(Float mVoteAverage) {
                this.mVoteAverage = mVoteAverage;
        }

        @Bindable
        public String getLanguage() {
                return Constants.getLanguageFromCode(mLanguage);
        }
        public void setLanguage(String mLanguage) {
                this.mLanguage = mLanguage;
        }

        @Bindable
        public String getTitle() {
                return mTitle;
        }
        public void setTitle(String mTitle) {
                this.mTitle = mTitle;
        }

        @Bindable
        public Float getPopularity() {
                return mPopularity;
        }
        public void setPopularity(Float mPopularity) {
                this.mPopularity = mPopularity;
        }

        @Bindable
        public String getPosterPath() {
                return mPosterPath;
        }
        public void setPosterPath(String mPosterPath) {
                this.mPosterPath = mPosterPath;
        }

        @Bindable
        public List<Integer> getGenreIds() {
                return mGenreIds;
        }
        public void setGenreIds(List<Integer> mGenreIds) {
                this.mGenreIds = mGenreIds;
        }

        @Bindable
        public String getBackdropPath() {
                return mBackdropPath;
        }
        public void setBackdropPath(String mBackdropPath) {
                this.mBackdropPath = mBackdropPath;
        }

        @Bindable
        public boolean getAdult() {
                return mAdult;
        }
        public void setAdult(boolean mAdult) {
                this.mAdult = mAdult;
        }

        @Bindable
        public String getOverview() {
                return mOverview;
        }
        public void setOverview(String mOverview) {
                this.mOverview = mOverview;
        }

        @Bindable
        public String getReleaseDate() {
                return Constants.changeDateFormat(mReleaseDate);
        }
        public void setReleaseDate(String mReleaseDate) {
                this.mReleaseDate = mReleaseDate;
        }
}
