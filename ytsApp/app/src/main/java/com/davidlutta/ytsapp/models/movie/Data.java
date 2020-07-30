
package com.davidlutta.ytsapp.models.movie;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("movie")
    @Expose
    private Movie movie;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1633348331534072536L;

    protected Data(Parcel in) {
        this.movie = ((Movie) in.readValue((Movie.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param movie
     */
    public Data(Movie movie) {
        super();
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(movie);
    }

    public int describeContents() {
        return  0;
    }

}
