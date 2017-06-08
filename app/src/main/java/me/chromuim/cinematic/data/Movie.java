package me.chromuim.cinematic.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chromuim on 14.05.17.
 */

public final class Movie implements Parcelable {

  @SerializedName("id")
  private final int mId;

  @Nullable
  @SerializedName("poster_path")
  private final String mPosterPath;

  @Nullable
  @SerializedName("backdrop_path")
  private final String mBackdropPath;

  @NonNull
  @SerializedName("title")
  private final String mTitle;

  @NonNull
  @SerializedName("overview")
  private final String mOverview;

  @SerializedName("vote_average")
  private final float mAverage;

  @NonNull
  @SerializedName("release_date")
  private final String mReleaseDate;

  public Movie(int id, @NonNull String title) {
    this(id, title, "Test Test", "/hohoh", "/hahahah", 9, "08.03.1999");
  }

  public Movie(int id, @NonNull String title, @NonNull String overview, @Nullable String posterPath, @Nullable String backdropPath,
      float average, @NonNull String releaseDate) {
    mId = id;
    mPosterPath = posterPath;
    mBackdropPath = backdropPath;
    mTitle = title;
    mOverview = overview;
    mAverage = average;
    mReleaseDate = releaseDate;
  }

  public int getId() {
    return mId;
  }

  @Nullable
  public String getPosterPath() {
    return mPosterPath;
  }

  @NonNull
  public String getTitle() {
    return mTitle;
  }

  @NonNull
  public String getOverview() {
    return mOverview;
  }

  public float getAverage() {
    return mAverage;
  }

  @NonNull
  public String getReleaseDate() {
    return mReleaseDate;
  }

  @Nullable
  public String getBackdropPath() {
    return mBackdropPath;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Movie movie = (Movie) obj;
    return Objects.equal(mId, movie.mId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(mId, mTitle);
  }

  @Override
  public String toString() {
    return String.format("Movie : [ ID : %1$s , Title : %2$s  ]", mId, mTitle);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mId);
    dest.writeString(this.mPosterPath);
    dest.writeString(this.mBackdropPath);
    dest.writeString(this.mTitle);
    dest.writeString(this.mOverview);
    dest.writeFloat(this.mAverage);
    dest.writeString(this.mReleaseDate);
  }

  protected Movie(Parcel in) {
    this.mId = in.readInt();
    this.mPosterPath = in.readString();
    this.mBackdropPath = in.readString();
    this.mTitle = in.readString();
    this.mOverview = in.readString();
    this.mAverage = in.readFloat();
    this.mReleaseDate = in.readString();
  }

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override
    public Movie createFromParcel(Parcel source) {
      return new Movie(source);
    }

    @Override
    public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };
}
