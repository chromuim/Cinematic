package me.chromuim.cinematic.data;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.data.MoviesRepository.MoviesRepositoryObserver;

/**
 * Created by chromuim on 15.06.17.
 */

public class MovieReviewsLoader extends AsyncTaskLoader<List<MovieReview>> implements MoviesRepositoryObserver {

  private final MoviesRepository mMoviesRepository;

  private int mMovieId;

  public MovieReviewsLoader(Context context, @NonNull MoviesRepository repository, int movieId) {
    super(context);
    mMoviesRepository = checkNotNull(repository);
    mMovieId = movieId;
  }

  @Override
  public List<MovieReview> loadInBackground() {
    return mMoviesRepository.getMovieReviews(mMovieId);
  }

  @Override
  protected void onStartLoading() {
    mMoviesRepository.addContentObserver(this);
    forceLoad();
  }

  @Override
  protected void onStopLoading() {
    onCancelLoad();
  }

  @Override
  protected void onReset() {
    onStartLoading();
    mMoviesRepository.removeContentObserver(this);
  }

  @Override
  public void deliverResult(List<MovieReview> data) {
    if (isReset()) {
      return;
    }
    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override
  public void onMoviesChanged() {
    if (isStarted()) {
      forceLoad();
    }
  }
}
