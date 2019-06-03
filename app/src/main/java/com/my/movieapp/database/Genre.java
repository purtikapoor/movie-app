package com.my.movieapp.database;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "genre")
public class Genre extends BaseObservable {

    @PrimaryKey()
    @ColumnInfo(name = "id") @SerializedName(value="id") private Integer mId;

    @ColumnInfo(name = "name") @SerializedName(value="name") private String mName;



    // use for ordering the items in view
    public static DiffUtil.ItemCallback<Genre> DIFF_CALLBACK = new DiffUtil.ItemCallback<Genre>() {
        @Override
        public boolean areItemsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
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
    public String getName() {
        return mName;
    }
    public void setName(String mName) {
        this.mName = mName;
    }


}
