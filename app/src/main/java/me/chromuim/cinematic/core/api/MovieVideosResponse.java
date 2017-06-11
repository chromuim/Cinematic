package me.chromuim.cinematic.core.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by chromuim on 10.06.17.
 */

public class MovieVideosResponse {

  @SerializedName("id")
  private int mMovieId;

  @SerializedName("results")
  private List<MovieVideo> mResults;

  public MovieVideosResponse(int movieId, List<MovieVideo> results) {
    mMovieId = movieId;
    mResults = results;
  }

  public int getMovieId() {
    return mMovieId;
  }

  public List<MovieVideo> getResults() {
    return mResults;
  }

}
