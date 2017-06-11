package me.chromuim.cinematic.core.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by chromuim on 11.06.17.
 */

public class MovieReviewResponse {

  @SerializedName("id")
  private int mId;

  @SerializedName("page")
  private int mPage;

  @SerializedName("results")
  private List<MovieReview> mResults;

  public MovieReviewResponse(int id, int page, List<MovieReview> results) {
    mId = id;
    mPage = page;
    mResults = results;
  }

  public int getId() {
    return mId;
  }

  public int getPage() {
    return mPage;
  }

  public List<MovieReview> getResults() {
    return mResults;
  }
}
