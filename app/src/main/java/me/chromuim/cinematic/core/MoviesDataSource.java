package me.chromuim.cinematic.core;

import android.support.annotation.NonNull;
import java.util.List;
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

  void getMovies(@NonNull LoadMoviesCallback callback);

  void getMovies(int currentPage, @NonNull LoadMoviesCallback callback);

  void getMovie(int movieId, @NonNull LoadMovieCallback callback);

  void save(@NonNull Movie movie);

  void deleteAll();

  void refreshAll();

}
