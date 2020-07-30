package com.davidlutta.ytsapp.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.davidlutta.ytsapp.models.movies.Torrent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
@Entity(tableName = "movie")
public class Movie implements Serializable, Parcelable {
    public final static Creator<Movie> CREATOR = new Creator<Movie>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    };
    private final static long serialVersionUID = 6986788485793069232L;
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("imdb_code")
    @Expose
    private String imdbCode;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_english")
    @Expose
    private String titleEnglish;
    @SerializedName("title_long")
    @Expose
    private String titleLong;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("year")
    @Expose
    private Long year;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("runtime")
    @Expose
    private Long runtime;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("download_count")
    @Expose
    private Long downloadCount;
    @SerializedName("like_count")
    @Expose
    private Long likeCount;
    @SerializedName("description_intro")
    @Expose
    private String descriptionIntro;
    @SerializedName("description_full")
    @Expose
    private String descriptionFull;
    @SerializedName("yt_trailer_code")
    @Expose
    private String ytTrailerCode;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("mpa_rating")
    @Expose
    private String mpaRating;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;
    @SerializedName("background_image_original")
    @Expose
    private String backgroundImageOriginal;
    @SerializedName("small_cover_image")
    @Expose
    private String smallCoverImage;
    @SerializedName("medium_cover_image")
    @Expose
    private String mediumCoverImage;
    @SerializedName("large_cover_image")
    @Expose
    private String largeCoverImage;
    @SerializedName("torrents")
    @Expose
    private List<Torrent> torrents = null;
    @SerializedName("date_uploaded")
    @Expose
    private String dateUploaded;
    @SerializedName("date_uploaded_unix")
    @Expose
    private Long dateUploadedUnix;

    protected Movie(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.imdbCode = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.titleEnglish = ((String) in.readValue((String.class.getClassLoader())));
        this.titleLong = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.year = ((Long) in.readValue((Long.class.getClassLoader())));
        this.rating = ((Float) in.readValue((Float.class.getClassLoader())));
        this.runtime = ((Long) in.readValue((Long.class.getClassLoader())));
        in.readList(this.genres, (String.class.getClassLoader()));
        this.downloadCount = ((Long) in.readValue((Long.class.getClassLoader())));
        this.likeCount = ((Long) in.readValue((Long.class.getClassLoader())));
        this.descriptionIntro = ((String) in.readValue((String.class.getClassLoader())));
        this.descriptionFull = ((String) in.readValue((String.class.getClassLoader())));
        this.ytTrailerCode = ((String) in.readValue((String.class.getClassLoader())));
        this.language = ((String) in.readValue((String.class.getClassLoader())));
        this.mpaRating = ((String) in.readValue((String.class.getClassLoader())));
        this.backgroundImage = ((String) in.readValue((String.class.getClassLoader())));
        this.backgroundImageOriginal = ((String) in.readValue((String.class.getClassLoader())));
        this.smallCoverImage = ((String) in.readValue((String.class.getClassLoader())));
        this.mediumCoverImage = ((String) in.readValue((String.class.getClassLoader())));
        this.largeCoverImage = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.torrents, (com.davidlutta.ytsapp.models.movies.Torrent.class.getClassLoader()));
        this.dateUploaded = ((String) in.readValue((String.class.getClassLoader())));
        this.dateUploadedUnix = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    /**
     * @param year
     * @param mpaRating
     * @param backgroundImage
     * @param rating
     * @param dateUploaded
     * @param likeCount
     * @param language
     * @param backgroundImageOriginal
     * @param title
     * @param titleEnglish
     * @param descriptionIntro
     * @param dateUploadedUnix
     * @param largeCoverImage
     * @param genres
     * @param descriptionFull
     * @param id
     * @param titleLong
     * @param mediumCoverImage
     * @param smallCoverImage
     * @param slug
     * @param imdbCode
     * @param runtime
     * @param url
     * @param ytTrailerCode
     * @param torrents
     * @param downloadCount
     */
    public Movie(@NonNull Long id, String url, String imdbCode, String title, String titleEnglish, String titleLong, String slug, Long year, Float rating, Long runtime, List<String> genres, Long downloadCount, Long likeCount, String descriptionIntro, String descriptionFull, String ytTrailerCode, String language, String mpaRating, String backgroundImage, String backgroundImageOriginal, String smallCoverImage, String mediumCoverImage, String largeCoverImage, List<Torrent> torrents, String dateUploaded, Long dateUploadedUnix) {
        super();
        this.id = id;
        this.url = url;
        this.imdbCode = imdbCode;
        this.title = title;
        this.titleEnglish = titleEnglish;
        this.titleLong = titleLong;
        this.slug = slug;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.genres = genres;
        this.downloadCount = downloadCount;
        this.likeCount = likeCount;
        this.descriptionIntro = descriptionIntro;
        this.descriptionFull = descriptionFull;
        this.ytTrailerCode = ytTrailerCode;
        this.language = language;
        this.mpaRating = mpaRating;
        this.backgroundImage = backgroundImage;
        this.backgroundImageOriginal = backgroundImageOriginal;
        this.smallCoverImage = smallCoverImage;
        this.mediumCoverImage = mediumCoverImage;
        this.largeCoverImage = largeCoverImage;
        this.torrents = torrents;
        this.dateUploaded = dateUploaded;
        this.dateUploadedUnix = dateUploadedUnix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImdbCode() {
        return imdbCode;
    }

    public void setImdbCode(String imdbCode) {
        this.imdbCode = imdbCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleLong() {
        return titleLong;
    }

    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }


    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getDescriptionIntro() {
        return descriptionIntro;
    }

    public void setDescriptionIntro(String descriptionIntro) {
        this.descriptionIntro = descriptionIntro;
    }

    public String getDescriptionFull() {
        return descriptionFull;
    }

    public void setDescriptionFull(String descriptionFull) {
        this.descriptionFull = descriptionFull;
    }

    public String getYtTrailerCode() {
        return ytTrailerCode;
    }

    public void setYtTrailerCode(String ytTrailerCode) {
        this.ytTrailerCode = ytTrailerCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImageOriginal() {
        return backgroundImageOriginal;
    }

    public void setBackgroundImageOriginal(String backgroundImageOriginal) {
        this.backgroundImageOriginal = backgroundImageOriginal;
    }

    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    public void setSmallCoverImage(String smallCoverImage) {
        this.smallCoverImage = smallCoverImage;
    }

    public String getMediumCoverImage() {
        return mediumCoverImage;
    }

    public void setMediumCoverImage(String mediumCoverImage) {
        this.mediumCoverImage = mediumCoverImage;
    }

    public String getLargeCoverImage() {
        return largeCoverImage;
    }

    public void setLargeCoverImage(String largeCoverImage) {
        this.largeCoverImage = largeCoverImage;
    }

    public List<Torrent> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<Torrent> torrents) {
        this.torrents = torrents;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Long getDateUploadedUnix() {
        return dateUploadedUnix;
    }

    public void setDateUploadedUnix(Long dateUploadedUnix) {
        this.dateUploadedUnix = dateUploadedUnix;
    }

    @Override
    public String toString() {
        return "SingleMovie{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", imdbCode='" + imdbCode + '\'' +
                ", title='" + title + '\'' +
                ", titleEnglish='" + titleEnglish + '\'' +
                ", titleLong='" + titleLong + '\'' +
                ", slug='" + slug + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", runtime=" + runtime +
                ", genres=" + genres +
                ", downloadCount=" + downloadCount +
                ", likeCount=" + likeCount +
                ", descriptionIntro='" + descriptionIntro + '\'' +
                ", descriptionFull='" + descriptionFull + '\'' +
                ", ytTrailerCode='" + ytTrailerCode + '\'' +
                ", language='" + language + '\'' +
                ", mpaRating='" + mpaRating + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", backgroundImageOriginal='" + backgroundImageOriginal + '\'' +
                ", smallCoverImage='" + smallCoverImage + '\'' +
                ", mediumCoverImage='" + mediumCoverImage + '\'' +
                ", largeCoverImage='" + largeCoverImage + '\'' +
                ", torrents=" + torrents +
                ", dateUploaded='" + dateUploaded + '\'' +
                ", dateUploadedUnix=" + dateUploadedUnix +
                '}';
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(url);
        dest.writeValue(imdbCode);
        dest.writeValue(title);
        dest.writeValue(titleEnglish);
        dest.writeValue(titleLong);
        dest.writeValue(slug);
        dest.writeValue(year);
        dest.writeValue(rating);
        dest.writeValue(runtime);
        dest.writeList(genres);
        dest.writeValue(downloadCount);
        dest.writeValue(likeCount);
        dest.writeValue(descriptionIntro);
        dest.writeValue(descriptionFull);
        dest.writeValue(ytTrailerCode);
        dest.writeValue(language);
        dest.writeValue(mpaRating);
        dest.writeValue(backgroundImage);
        dest.writeValue(backgroundImageOriginal);
        dest.writeValue(smallCoverImage);
        dest.writeValue(mediumCoverImage);
        dest.writeValue(largeCoverImage);
        dest.writeList(torrents);
        dest.writeValue(dateUploaded);
        dest.writeValue(dateUploadedUnix);
    }

    public int describeContents() {
        return 0;
    }

}
