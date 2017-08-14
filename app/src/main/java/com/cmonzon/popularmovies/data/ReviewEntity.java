package com.cmonzon.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author cmonzon
 */
public class ReviewEntity implements Parcelable {

    private String author;

    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    public ReviewEntity() {
    }

    protected ReviewEntity(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    public static final Creator<ReviewEntity> CREATOR = new Creator<ReviewEntity>() {
        @Override
        public ReviewEntity createFromParcel(Parcel source) {
            return new ReviewEntity(source);
        }

        @Override
        public ReviewEntity[] newArray(int size) {
            return new ReviewEntity[size];
        }
    };
}
