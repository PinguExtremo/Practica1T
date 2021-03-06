package com.example.practica1t.common;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private double latitude;
    private double longitude;


    public Location(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public Location() {

    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return longitude;
    }

    public void setAltitude(double altitude) {
        this.longitude = altitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
