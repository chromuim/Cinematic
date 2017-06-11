package me.chromuim.cinematic.core.api;

import me.chromuim.cinematic.data.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chromuim on 17.05.17.
 */

public interface MovieApiService {

  @GET("discover/movie?")
  Call<ResponseList<Movie>> loadMovies(@Query("sort_by") String sortBy, @Query("page") int page);

  @GET("movie/{id}/videos")
  Call<MovieVideosResponse> loadMovieDetailVideos(@Path("id") int movieId);

  @GET("movie/{id}/reviews")
  Call<MovieReviewResponse> loadMovieDetailReviews(@Path("id") int movieId);

}
