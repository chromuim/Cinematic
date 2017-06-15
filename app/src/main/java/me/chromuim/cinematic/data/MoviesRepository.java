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
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieVideo;

/**
 * Created by chromuim on 14.05.17.
 */

public class MoviesRepository implements MoviesDataSource {

  private static MoviesRepository INSTANCE = null;

  private final MoviesDataSource mLocalDataSource;
  private final MoviesDataSource mRemoteDataSource;

  @VisibleForTesting
  /*package*/ Map<Integer, Movie> mCachedMovies;
  private List<MoviesRepositoryObserver> mObservers = new ArrayList<>();

  public void setCacheIsDirty(boolean cacheIsDirty) {
    mCacheIsDirty = cacheIsDirty;
  }

  private boolean mCacheIsDirty;

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

  public void addContentObserver(MoviesRepositoryObserver observer) {
    if (!mObservers.contains(observer)) {
      mObservers.add(observer);
    }
  }

  public void removeContentObserver(MoviesRepositoryObserver observer) {
    if (mObservers.contains(observer)) {
      mObservers.remove(observer);
    }
  }

  private void notifyContentObserver() {
    for (MoviesRepositoryObserver observer : mObservers) {
      observer.onMoviesChanged();
    }
  }

  @Nullable
  @Override
  public List<Movie> getMovies(String sortType, int pageNo) {
    List<Movie> movies = null;

    if (!mCacheIsDirty) {
      if (mCachedMovies != null) {
        return getCachedMovies();
      } else {
        // get the movies from cache if available
        movies = mLocalDataSource.getMovies(sortType, pageNo);
      }
    }

    if (movies == null || movies.isEmpty()) {
      movies = mRemoteDataSource.getMovies(sortType, pageNo);
      saveMoviesInLocalDataSource(movies);
    }

    processLoadedMovies(movies);
    return getCachedMovies();
  }

  public List<Movie> getCachedMovies() {
    return mCachedMovies == null ? null : new ArrayList<>(mCachedMovies.values());
  }

  public Movie getCachedMovie(int movieId) {
    return mCachedMovies.get(movieId);
  }

  public boolean isCacheAvailable() {
    return mCachedMovies != null && !mCacheIsDirty;
  }

  private void saveMoviesInLocalDataSource(List<Movie> movies) {
    if (movies != null) {
      for (Movie movie : movies) {
        mLocalDataSource.save(movie);
      }
    }
  }

  private void processLoadedMovies(List<Movie> movies) {
    if (movies == null) {
      mCachedMovies = null;
      mCacheIsDirty = false;
      return;
    }

    if (mCachedMovies == null) {
      mCachedMovies = new LinkedHashMap<>();
    }
//    mCachedMovies.clear();
    for (Movie movie : movies) {
      mCachedMovies.put(movie.getId(), movie);
    }
    mCacheIsDirty = false;
  }

  @Nullable
  @Override
  public Movie getMovie(int movieId) {
    checkNotNull(movieId);

    Movie cachedMovie = getMovieById(movieId);
    if (cachedMovie != null) {
      return cachedMovie;
    }

    Movie movie = mLocalDataSource.getMovie(movieId);
    if (movie == null) {
      movie = mRemoteDataSource.getMovie(movieId);
    }

    return movie;
  }

  //  @Override
//  public void getMovies(@NonNull final LoadMoviesCallback callback) {
//    checkNotNull(callback);
//
//    if (mCachedMovies != null && !mCacheIsDirty) {
//      callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
//      return;
//    }
//
//    if (mCacheIsDirty) {
//      //fetch new data
//      loadFromRemote(1, callback);
//    } else {
//      mLocalDataSource.getMovies(new LoadMoviesCallback() {
//        @Override
//        public void onMoviesLoaded(List<Movie> movies) {
//          refreshCache(movies);
//          callback.onMoviesLoaded(new ArrayList<>(mCachedMovies.values()));
//        }
//
//        @Override
//        public void onDataNotAvailable() {
//          loadFromRemote(1, callback);
//        }
//      });
//    }
//
//  }
//
//  @Override
//  public void getMovies(int currentPage, @NonNull LoadMoviesCallback callback) {
//    loadFromRemote(currentPage, callback);
//  }
//
//  @Override
//  public void getMovie(final int movieId, @NonNull final LoadMovieCallback callback) {
//    checkNotNull(callback);
//
//    Movie retrievedMovie = getMovieById(movieId);
//    if (retrievedMovie != null) {
//      callback.onMovieLoaded(retrievedMovie);
//      return;
//    }
//
//    // data not found in cache.. so load it from local / remote
//    mLocalDataSource.getMovie(movieId, new LoadMovieCallback() {
//      @Override
//      public void onMovieLoaded(Movie movie) {
//        if (mCachedMovies == null) {
//          mCachedMovies = new LinkedHashMap<>();
//        }
//        mCachedMovies.put(movieId, movie);
//        callback.onMovieLoaded(movie);
//      }
//
//      @Override
//      public void onDataNotAvailable() {
//        //request the date from remote
//
//        mRemoteDataSource.getMovie(movieId, new LoadMovieCallback() {
//          @Override
//          public void onMovieLoaded(Movie movie) {
//            if (mCachedMovies == null) {
//              mCachedMovies = new LinkedHashMap<>();
//            }
//            mCachedMovies.put(movieId, movie);
//            callback.onMovieLoaded(movie);
//          }
//
//          @Override
//          public void onDataNotAvailable() {
//            callback.onDataNotAvailable();
//          }
//        });
//      }
//    });
//  }

  @Nullable
  @Override
  public List<MovieVideo> getMovieVideos(int movieId) {
    //get the data from remote for now
    return mRemoteDataSource.getMovieVideos(movieId);
  }

  @Override
  public List<MovieReview> getMovieReviews(int movieId) {
    return mRemoteDataSource.getMovieReviews(movieId);
  }

  @Override
  public void deleteAll() {
    mLocalDataSource.deleteAll();
    mRemoteDataSource.deleteAll();
    if (mCachedMovies == null) {
      mCachedMovies = new LinkedHashMap<>();
    }
    mCachedMovies.clear();

    notifyContentObserver();
  }

  @Override
  public void refreshAll() {
    mCacheIsDirty = true;
    notifyContentObserver();
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

    // update the UI.
    notifyContentObserver();
  }

  @Nullable
  private Movie getMovieById(int movieId) {
    if (mCachedMovies == null || mCachedMovies.isEmpty()) {
      return null;
    } else {
      return mCachedMovies.get(movieId);
    }

  }

  public interface MoviesRepositoryObserver {

    void onMoviesChanged();
  }
}
