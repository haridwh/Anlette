package com.example.skday.anlette.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by irfanandarafifsatrio on 11/16/17.
 */

public class AnlPhoto implements Parcelable {
    private Bitmap bitmap;
    private String name;

    public AnlPhoto(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    protected AnlPhoto(Parcel in) {
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        name = in.readString();
    }

    public static final Creator<AnlPhoto> CREATOR = new Creator<AnlPhoto>() {
        @Override
        public AnlPhoto createFromParcel(Parcel in) {
            return new AnlPhoto(in);
        }

        @Override
        public AnlPhoto[] newArray(int size) {
            return new AnlPhoto[size];
        }
    };

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bitmap, flags);
        dest.writeString(name);
    }
}
