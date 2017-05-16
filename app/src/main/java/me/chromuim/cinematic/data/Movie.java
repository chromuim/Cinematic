package me.chromuim.cinematic.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.common.base.Objects;

/**
 * Created by chromuim on 14.05.17.
 */

public final class Movie {


  private final int mId;

  @Nullable
  private final String mPosterPath;

  @NonNull
  private final String mTitle;

  @NonNull
  private final String mOverview;

  private final float mAverage;

  @NonNull
  private final String mReleaseDate;

  public Movie(int id, @NonNull String title) {
    this(id, title, "Test Test", "/hohoh", 9, "08.03.1999");
  }

  public Movie(int id, @NonNull String title, @NonNull String overview, @Nullable String posterPath,
      float average, @NonNull String releaseDate) {
    mId = id;
    mPosterPath = posterPath;
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
}
