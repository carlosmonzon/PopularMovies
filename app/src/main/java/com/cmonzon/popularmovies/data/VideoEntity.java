package com.cmonzon.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author cmonzon
 */
public class VideoEntity  implements Parcelable{

    private String name;

    private String site;

    private int size;

    private String key;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.key);
        dest.writeString(this.type);
    }

    public VideoEntity() {
    }

    protected VideoEntity(Parcel in) {
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.key = in.readString();
        this.type = in.readString();
    }

    public static final Creator<VideoEntity> CREATOR = new Creator<VideoEntity>() {
        @Override
        public VideoEntity createFromParcel(Parcel source) {
            return new VideoEntity(source);
        }

        @Override
        public VideoEntity[] newArray(int size) {
            return new VideoEntity[size];
        }
    };
}
