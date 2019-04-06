package com.earchive.enigmatic.egzamsarchive.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Bartek on 2016-04-28.
 */
public class University implements Parcelable {

    private String shortName;

    private String fullName;

    public University() {
    }

    public University(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    protected University(Parcel in) {
        shortName = in.readString();
        fullName = in.readString();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }


    public static final Creator<University> CREATOR = new Creator<University>() {
        @Override
        public University createFromParcel(Parcel in) {
            return new University(in);
        }

        @Override
        public University[] newArray(int size) {
            return new University[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortName);
        dest.writeString(fullName);
    }
}
