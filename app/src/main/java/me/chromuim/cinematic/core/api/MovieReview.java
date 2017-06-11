package me.chromuim.cinematic.core.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chromuim on 11.06.17.
 */

public class MovieReview implements Parcelable {

  public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
    @Override
    public MovieReview createFromParcel(Parcel source) {
      return new MovieReview(source);
    }

    @Override
    public MovieReview[] newArray(int size) {
      return new MovieReview[size];
    }
  };

  @SerializedName("id")
  private String mReviewId;
  @SerializedName("author")
  private String mAuthor;
  @SerializedName("url")
  private String mUrl;
  @SerializedName("content")
  private String mContent;

  public MovieReview(String reviewId, String author, String url, String content) {
    mReviewId = reviewId;
    mAuthor = author;
    mUrl = url;
    mContent = content;
  }

  protected MovieReview(Parcel in) {
    this.mReviewId = in.readString();
    this.mAuthor = in.readString();
    this.mUrl = in.readString();
    this.mContent = in.readString();
  }

  public String getReviewId() {
    return mReviewId;
  }

  public String getAuthor() {
    return mAuthor;
  }

  public String getUrl() {
    return mUrl;
  }

  public String getContent() {
    return mContent;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mReviewId);
    dest.writeString(this.mAuthor);
    dest.writeString(this.mUrl);
    dest.writeString(this.mContent);
  }
}
