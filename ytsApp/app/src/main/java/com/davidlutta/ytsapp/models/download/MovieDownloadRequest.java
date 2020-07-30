package com.davidlutta.ytsapp.models.download;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "downloadRequestMovies")
public class MovieDownloadRequest implements Serializable, Parcelable {
    public final static Parcelable.Creator<MovieDownloadRequest> CREATOR = new Creator<MovieDownloadRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieDownloadRequest createFromParcel(Parcel in) {
            return new MovieDownloadRequest(in);
        }

        public MovieDownloadRequest[] newArray(int size) {
            return (new MovieDownloadRequest[size]);
        }

    };
    private final static long serialVersionUID = -5062915449077612468L;
    @PrimaryKey
    @NonNull
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("downloadUrl")
    @Expose
    private String downloadUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("poster")
    @Expose
    private String poster;

    protected MovieDownloadRequest(Parcel in) {
        this.hash = ((String) in.readValue((String.class.getClassLoader())));
        this.downloadUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.timestamp = ((Long) in.readValue((Long.class.getClassLoader())));
        this.poster = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public MovieDownloadRequest() {
    }

    /**
     * @param downloadUrl
     * @param title
     * @param poster
     * @param hash
     * @param timestamp
     */
    public MovieDownloadRequest(@NonNull String hash, String downloadUrl, String title, Long timestamp, String poster) {
        super();
        this.hash = hash;
        this.downloadUrl = downloadUrl;
        this.title = title;
        this.timestamp = timestamp;
        this.poster = poster;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(hash);
        dest.writeValue(downloadUrl);
        dest.writeValue(title);
        dest.writeValue(timestamp);
        dest.writeValue(poster);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "MovieDownloadRequest{" +
                "hash='" + hash + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", title='" + title + '\'' +
                ", timestamp=" + timestamp +
                ", poster='" + poster + '\'' +
                '}';
    }
}