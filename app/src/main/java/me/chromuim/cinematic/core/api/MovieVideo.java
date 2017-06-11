package me.chromuim.cinematic.core.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chromuim on 10.06.17.
 */

public class MovieVideo implements Parcelable {

  public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
    @Override
    public MovieVideo createFromParcel(Parcel source) {
      return new MovieVideo(source);
    }

    @Override
    public MovieVideo[] newArray(int size) {
      return new MovieVideo[size];
    }
  };

  @SerializedName("id")
  private String mVideoId;
  @SerializedName("key")
  private String mKey;
  @SerializedName("name")
  private String mVideoName;
  @SerializedName("type")
  private String mType;
  @SerializedName("site")
  private String mSite;

  public MovieVideo(String videoId, String key, String videoName, String type, String site) {
    mVideoId = videoId;
    mKey = key;
    mVideoName = videoName;
    mType = type;
    mSite = site;
  }

  protected MovieVideo(Parcel in) {
    this.mVideoId = in.readString();
    this.mKey = in.readString();
    this.mVideoName = in.readString();
    this.mType = in.readString();
    this.mSite = in.readString();
  }

  public String getType() {
    return mType;
  }

  public String getVideoId() {
    return mVideoId;
  }

  public String getKey() {
    return mKey;
  }

  public String getVideoName() {
    return mVideoName;
  }

  public boolean isYoutube() {
    return mSite.toLowerCase().equals(Constants.YOUTUBE.toLowerCase());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mVideoId);
    dest.writeString(this.mKey);
    dest.writeString(this.mVideoName);
    dest.writeString(this.mType);
  }

  public String getSite() {
    return mSite;
  }
}
