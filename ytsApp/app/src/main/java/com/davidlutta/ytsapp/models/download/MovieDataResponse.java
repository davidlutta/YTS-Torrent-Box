package com.davidlutta.ytsapp.models.download;
import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDataResponse implements Serializable, Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<MovieDataResponse> CREATOR = new Creator<MovieDataResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieDataResponse createFromParcel(Parcel in) {
            return new MovieDataResponse(in);
        }

        public MovieDataResponse[] newArray(int size) {
            return (new MovieDataResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 7877227215123705453L;

    protected MovieDataResponse(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieDataResponse() {
    }

    /**
     *
     * @param message
     */
    public MovieDataResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}