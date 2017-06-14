package me.chromuim.cinematic.data;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by chromuim on 12.06.17.
 */

public class MovieLoader extends AsyncTaskLoader<Movie>
    implements MoviesRepository.MoviesRepositoryObserver {

  private MoviesRepository mRepository;

  private int mMovieId;


  public MovieLoader(Context context, MoviesRepository repository, int movieId) {
    super(context);
    mRepository = checkNotNull(repository);
    mMovieId = movieId;
  }

  @Override
  public Movie loadInBackground() {
    return mRepository.getMovie(mMovieId);
  }

  @Override
  public void deliverResult(Movie data) {
    if (isReset()) {
      return;
    }
    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override
  protected void onStartLoading() {
    if (mRepository.isCacheAvailable()) {
      deliverResult(mRepository.getCachedMovie(mMovieId));
    }

    mRepository.addContentObserver(this);
    if (takeContentChanged() || !mRepository.isCacheAvailable()) {
      forceLoad();
    }
  }

  @Override
  protected void onReset() {
    onStopLoading();
    mRepository.removeContentObserver(this);
  }

  @Override
  public void onMoviesChanged() {
    if (isStarted()) {
      forceLoad();
    }
  }
}
