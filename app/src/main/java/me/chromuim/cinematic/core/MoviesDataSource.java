package me.chromuim.cinematic.core;

import android.support.annotation.NonNull;
import java.util.List;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.data.Movie;

/**
 * Created by chromuim on 14.05.17.
 */

public interface MoviesDataSource {

  interface LoadMoviesCallback {

    void onMoviesLoaded(List<Movie> movies);

    void onDataNotAvailable();
  }

  interface LoadMovieCallback {

    void onMovieLoaded(Movie movie);

    void onDataNotAvailable();
  }


  interface LoadMovieVideosCallback {

    void onVideosLoaded(List<MovieVideo> movieVideos);

    void onDataNotAvailable();
  }

  void getMovies(@NonNull LoadMoviesCallback callback);

  void getMovies(int currentPage, @NonNull LoadMoviesCallback callback);

  void getMovie(int movieId, @NonNull LoadMovieCallback callback);

  void getMovieVideos(int movieId, @NonNull LoadMovieVideosCallback callback);

  void save(@NonNull Movie movie);

  void deleteAll();

  void refreshAll();

}
