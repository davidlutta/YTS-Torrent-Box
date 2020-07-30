
package com.davidlutta.ytsapp.models.movies;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesResponse implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("@meta")
    @Expose
    private Meta meta;
    public final static Creator<MoviesResponse> CREATOR = new Creator<MoviesResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MoviesResponse createFromParcel(Parcel in) {
            return new MoviesResponse(in);
        }

        public MoviesResponse[] newArray(int size) {
            return (new MoviesResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3983320272265234016L;

    protected MoviesResponse(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MoviesResponse() {
    }

    /**
     * 
     * @param data
     * @param meta
     * @param statusMessage
     * @param status
     */
    public MoviesResponse(String status, String statusMessage, Data data, Meta meta) {
        super();
        this.status = status;
        this.statusMessage = statusMessage;
        this.data = data;
        this.meta = meta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(statusMessage);
        dest.writeValue(data);
        dest.writeValue(meta);
    }

    public int describeContents() {
        return  0;
    }

}
