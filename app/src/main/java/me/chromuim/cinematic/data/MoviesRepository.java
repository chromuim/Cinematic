package me.chromuim.cinematic.data;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.chromuim.cinematic.core.MoviesDataSource;

/**
 * Created by chromuim on 14.05.17.
 */

public class MoviesRepository implements MoviesDataSource {

  private static MoviesRepository INSTANCE = null;

  private final MoviesDataSource mLocalDataSource;

  private final MoviesDataSource mRemoteDataSource;
  private boolean mCacheIsDirty = false;

  @VisibleForTesting
  /*package*/ Map<Integer, Movie> mCachedMovies;

  private MoviesRepository(@NonNull MoviesDataSource localDataSource,
      @NonNull MoviesDataSource remoteDataSource) {
    mLocalDataSource = checkNotNull(localDataSource);
    mRemoteDataSource = checkNotNull(remoteDataSource);
  }

  public static MoviesRepository getInstance(MoviesDataSource local, MoviesDataSource remote) {
    if (INSTANCE == null) {
      INSTANCE = new MoviesRepository(local, remote);
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }

  @Override
  public void getMovies(@NonNull final LoadMoviesCallback callback) {
    checkNotNull(callback);

    if (mCachedMovies != null && !mCacheIsDirty) {
      callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
      return;
    }

    if (mCacheIsDirty) {
      //fetch new data
      loadFromRemote(1, callback);
    } else {
      mLocalDataSource.getMovies(new LoadMoviesCallback() {
        @Override
        public void onMoviesLoaded(List<Movie> movies) {
          refreshCache(movies);
          callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
        }

        @Override
        public void onDataNotAvailable() {
          loadFromRemote(1, callback);
        }
      });
    }

  }

  @Override
  public void getMovies(int currentPage, @NonNull LoadMoviesCallback callback) {
    loadFromRemote(currentPage, callback);
  }

  @Override
  public void getMovie(final int movieId, @NonNull final LoadMovieCallback callback) {
    checkNotNull(callback);

    Movie retrievedMovie = getMovieById(movieId);
    if (retrievedMovie != null) {
      callback.onMovieLoaded(retrievedMovie);
      return;
    }

    // data not found in cache.. so load it from local / remote
    mLocalDataSource.getMovie(movieId, new LoadMovieCallback() {
      @Override
      public void onMovieLoaded(Movie movie) {
        if (mCachedMovies == null) {
          mCachedMovies = new LinkedHashMap<>();
        }
        mCachedMovies.put(movieId, movie);
        callback.onMovieLoaded(movie);
      }

      @Override
      public void onDataNotAvailable() {
        //request the date from remote

        mRemoteDataSource.getMovie(movieId, new LoadMovieCallback() {
          @Override
          public void onMovieLoaded(Movie movie) {
            if (mCachedMovies == null) {
              mCachedMovies = new LinkedHashMap<>();
            }
            mCachedMovies.put(movieId, movie);
            callback.onMovieLoaded(movie);
          }

          @Override
          public void onDataNotAvailable() {
            callback.onDataNotAvailable();
          }
        });
      }
    });
  }

  @Override
  public void deleteAll() {
    mLocalDataSource.deleteAll();
    mRemoteDataSource.deleteAll();
    if (mCachedMovies == null) {
      mCachedMovies = new LinkedHashMap<>();
    }
    mCachedMovies.clear();
  }

  @Override
  public void refreshAll() {
    mCacheIsDirty = true;
  }

  @Override
  public void save(@NonNull Movie movie) {
    checkNotNull(movie);

    mLocalDataSource.save(movie);
    mRemoteDataSource.save(movie);

    if (mCachedMovies == null) {
      mCachedMovies = new LinkedHashMap<>();
    }
    mCachedMovies.put(movie.getId(), movie);
  }

  private void loadFromRemote(int currentPage, final LoadMoviesCallback callback) {
    mRemoteDataSource.getMovies(currentPage, new LoadMoviesCallback() {
      @Override
      public void onMoviesLoaded(List<Movie> movies) {
        refreshCache(movies);
        refreshLocalDataSource(movies);
        callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
      }

      @Override
      public void onDataNotAvailable() {
        callback.onDataNotAvailable();
      }
    });
  }

  private void refreshCache(List<Movie> movies) {
    if (mCachedMovies == null) {
      mCachedMovies = new LinkedHashMap<>();
    }
//    mCachedMovies.clear();
    for (Movie movie : movies) {
      mCachedMovies.put(movie.getId(), movie);
    }
    mCacheIsDirty = false;
  }

  private void refreshLocalDataSource(List<Movie> movies) {
    mLocalDataSource.deleteAll();

     for (Movie movie : movies) {
      mLocalDataSource.save(movie);
    }
  }

  @Nullable
  private Movie getMovieById(int movieId) {
    if (mCachedMovies == null || mCachedMovies.isEmpty()) {
      return null;
    } else {
      return mCachedMovies.get(movieId);
    }

  }
}
