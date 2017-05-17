package me.chromuim.cinematic.core.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by chromuim on 17.05.17.
 */

public class ResponseList<T> {

  @SerializedName("page")
  private int mPage;

  @SerializedName("results")
  private List<T> mResults;

  @SerializedName("total_pages")
  private int mTotalPages;

  @SerializedName("total_results")
  private int mTotalResults;

  public ResponseList(int page, List<T> results, int totalPages, int totalResults) {
    mPage = page;
    mResults = results;
    mTotalPages = totalPages;
    mTotalResults = totalResults;
  }

  public int getPage() {
    return mPage;
  }

  public List<T> getResults() {
    return mResults;
  }

  public int getTotalPages() {
    return mTotalPages;
  }

  public int getTotalResults() {
    return mTotalResults;
  }
}
