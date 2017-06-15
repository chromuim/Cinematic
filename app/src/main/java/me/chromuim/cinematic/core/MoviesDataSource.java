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

  @Nullable
  List<Movie> getMovies(String sortType, int pageNo);

  @Nullable
  Movie getMovie(int movieId);

  @Nullable
  List<MovieVideo> getMovieVideos(int movieId);

  @Nullable
  List<MovieReview> getMovieReviews(int movieId);

  void save(@NonNull Movie movie);

  void deleteAll();

  void refreshAll();

}
