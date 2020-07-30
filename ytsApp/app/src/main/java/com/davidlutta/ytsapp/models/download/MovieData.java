package com.davidlutta.ytsapp.models.download;

public class MovieData {
    private String id;
    private String title;
    private String poster;
    private String downloadUrl;

    public MovieData(String id, String title, String poster, String downloadUrl) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.downloadUrl = downloadUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
