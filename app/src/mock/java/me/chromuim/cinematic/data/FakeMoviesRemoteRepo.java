package me.chromuim.cinematic.data;

import android.support.annotation.NonNull;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import me.chromuim.cinematic.core.MoviesDataSource;

/**
 * Created by chromuim on 16.05.17.
 */

public class FakeMoviesRemoteRepo implements MoviesDataSource {

  private static FakeMoviesRemoteRepo INSTANCE;

  private static final Map<Integer, Movie> MOVIES = new LinkedHashMap<>();

  private FakeMoviesRemoteRepo() {
  }

  public static FakeMoviesRemoteRepo getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new FakeMoviesRemoteRepo();
    }
    return INSTANCE;
  }

  @Override
  public void getMovies(@NonNull LoadMoviesCallback callback) {
    callback.onMoviesLoaded(Lists.newArrayList(MOVIES.values()));
  }

  @Override
  public void getMovie(int movieId, @NonNull LoadMovieCallback callback) {
    Movie movie = MOVIES.get(movieId);
    callback.onMovieLoaded(movie);
  }

  @Override
  public void save(@NonNull Movie movie) {
    MOVIES.put(movie.getId(), movie);
  }

  @Override
  public void deleteAll() {
    MOVIES.clear();
  }

  @Override
  public void refreshAll() {
    //no used
  }
}
