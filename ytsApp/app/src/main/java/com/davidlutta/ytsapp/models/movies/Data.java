
package com.davidlutta.ytsapp.models.movies;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("movie_count")
    @Expose
    private Long movieCount;
    @SerializedName("limit")
    @Expose
    private Long limit;
    @SerializedName("page_number")
    @Expose
    private Long pageNumber;
    @SerializedName("movies")
    @Expose
    private List<Movies> movies = null;
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
    private final static long serialVersionUID = -3770765788780833855L;

    protected Data(Parcel in) {
        this.movieCount = ((Long) in.readValue((Long.class.getClassLoader())));
        this.limit = ((Long) in.readValue((Long.class.getClassLoader())));
        this.pageNumber = ((Long) in.readValue((Long.class.getClassLoader())));
        in.readList(this.movies, (Movies.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param movies
     * @param pageNumber
     * @param movieCount
     * @param limit
     */
    public Data(Long movieCount, Long limit, Long pageNumber, List<Movies> movies) {
        super();
        this.movieCount = movieCount;
        this.limit = limit;
        this.pageNumber = pageNumber;
        this.movies = movies;
    }

    public Long getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(Long movieCount) {
        this.movieCount = movieCount;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(movieCount);
        dest.writeValue(limit);
        dest.writeValue(pageNumber);
        dest.writeList(movies);
    }

    public int describeContents() {
        return  0;
    }

}
