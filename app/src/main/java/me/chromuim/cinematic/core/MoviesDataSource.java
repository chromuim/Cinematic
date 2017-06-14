package me.chromuim.cinematic.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.data.Movie;

/**
 * Created by chromuim on 14.05.17.
 */

public interface MoviesDataSource {

  interface LoadMovieVideosCallback {

    void onVideosLoaded(List<MovieVideo> movieVideos);

    void onDataNotAvailable();
  }

  interface LoadMovieReviewsCallback {

    void onReviewLoaded(List<MovieReview> movieReviews);

    void onDataNotAvailable();
  }

  @Nullable
  List<Movie> getMovies(String sortType, int pageNo);

  @Nullable
  Movie getMovie(int movieId);

  void getMovieVideos(int movieId, @NonNull LoadMovieVideosCallback callback);

  void getMovieReviews(int movieId, @NonNull LoadMovieReviewsCallback callback);

  void save(@NonNull Movie movie);

  void deleteAll();

  void refreshAll();

}
