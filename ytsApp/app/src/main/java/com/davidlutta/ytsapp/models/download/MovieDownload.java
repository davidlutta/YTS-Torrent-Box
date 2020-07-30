package com.davidlutta.ytsapp.models.download;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "downloadingMovies")
public class MovieDownload implements Serializable, Parcelable {

    public final static Parcelable.Creator<MovieDownload> CREATOR = new Creator<MovieDownload>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieDownload createFromParcel(Parcel in) {
            return new MovieDownload(in);
        }

        public MovieDownload[] newArray(int size) {
            return (new MovieDownload[size]);
        }

    };
    private final static long serialVersionUID = -6748929269726383975L;
    @PrimaryKey
    @NonNull
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("percentage")
    @Expose
    private Float percentage;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("title")
    @Expose
    private String title;

    protected MovieDownload(Parcel in) {
        this.hash = ((String) in.readValue(String.class.getClassLoader()));
        this.percentage = ((Float) in.readValue((Float.class.getClassLoader())));
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.poster = ((String) in.readValue((String.class.getClassLoader())));
        this.timestamp = ((Long) in.readValue((Long.class.getClassLoader())));
        this.progress = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public MovieDownload() {
    }

    /**
     * @param hash
     * @param percentage
     * @param id
     * @param poster
     * @param timestamp
     * @param progress
     * @param title
     */
    public MovieDownload(@NonNull String hash, Float percentage, Long id, String poster, Long timestamp, String progress, String title) {
        super();
        this.hash = hash;
        this.percentage = percentage;
        this.id = id;
        this.poster = poster;
        this.timestamp = timestamp;
        this.progress = progress;
        this.title = title;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(hash);
        dest.writeValue(percentage);
        dest.writeValue(id);
        dest.writeValue(poster);
        dest.writeValue(timestamp);
        dest.writeValue(progress);
        dest.writeValue(title);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "MovieDownload{" +
                "hash='" + hash + '\'' +
                ", percentage=" + percentage +
                ", id=" + id +
                ", poster='" + poster + '\'' +
                ", timestamp=" + timestamp +
                ", progress='" + progress + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}