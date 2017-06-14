package me.chromuim.cinematic.movies;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import java.util.List;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.MoviesLoader;
import me.chromuim.cinematic.data.MoviesRepository;
import me.chromuim.cinematic.movies.MoviesContract.View;

/**
 * Created by chromuim on 16.05.17.
 */

public class MoviesPresenter implements MoviesContract.Presenter, LoaderManager.LoaderCallbacks<List<Movie>> {

  private static final String TAG = "MoviesPresenter";

  private final static int LOADER_MOVIES = 111;

  private final MoviesRepository mMoviesRepository;

  private final MoviesContract.View mMoviesView;

  private final MoviesLoader mMoviesLoader;

  private final LoaderManager mLoaderManager;

  private final Context mContext;
  private List<Movie> mCurrentMovies;
  private boolean mFirstLoad = true;

  private int mNextPageToLoad = 1;

  public MoviesPresenter(@NonNull Context context, @NonNull MoviesRepository moviesRepository, @NonNull View moviesView,
      @NonNull MoviesLoader moviesLoader, @NonNull LoaderManager loaderManager) {
    mMoviesRepository = checkNotNull(moviesRepository);
    mMoviesView = checkNotNull(moviesView);

    mMoviesLoader = checkNotNull(moviesLoader);
    mLoaderManager = checkNotNull(loaderManager);

    mContext = checkNotNull(context);
    mMoviesView.setPresenter(this);
  }

  @Override
  public void start() {
    mLoaderManager.initLoader(LOADER_MOVIES, null, this);
  }

  @Override
  public void loadMovies(boolean forceUpdate) {
    if (forceUpdate || mFirstLoad) {
      mFirstLoad = false;
      mMoviesRepository.refreshAll();
    } else {
      processMovies(mCurrentMovies);
    }
  }

  @Override
  public void openMovieDetails(@NonNull Movie requestedMovie) {
    checkNotNull(requestedMovie, "Requested Movies can't be null");
    mMoviesView.showMovieDetailsUi(requestedMovie);
  }

  @Override
  public void loadMore(int currentPage) {
    Log.e(TAG, "loadMore: pageNo " + currentPage);
    mNextPageToLoad = currentPage;
    mMoviesRepository.setCacheIsDirty(true);
    mLoaderManager.restartLoader(LOADER_MOVIES, null, this);
  }

  // Loader
  @Override
  public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
    mMoviesView.setLoadingIndicator(true);
//    return mMoviesLoader;
    return new MoviesLoader(mContext, mMoviesRepository, Constants.SORT_POPULARITY, mNextPageToLoad);
  }

  @Override
  public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
    mMoviesView.setLoadingIndicator(false);
    mCurrentMovies = data;
    if (mCurrentMovies == null) {
      mMoviesView.showError();
    } else {
      processMovies(mCurrentMovies);
    }
  }

  @Override
  public void onLoaderReset(Loader<List<Movie>> loader) {
    //nothing yet.
  }

  private void processMovies(List<Movie> movies) {
    if (movies.isEmpty()) {
      mMoviesView.showNoMovies();
    } else {
      mMoviesView.showMovies(movies);
    }
  }
}
