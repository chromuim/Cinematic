package me.chromuim.cinematic.movies;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMoviesCallback;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.MoviesRepository;

/**
 * Created by chromuim on 16.05.17.
 */

public class MoviesPresenter implements MoviesContract.Presenter {

  private final MoviesRepository mMoviesRepository;

  private final MoviesContract.View mMoviesView;

  private boolean mFirstLoad = true;

  public MoviesPresenter(@NonNull MoviesRepository moviesRepository, @NonNull MoviesContract.View moviesView) {
    mMoviesRepository = checkNotNull(moviesRepository);
    mMoviesView = checkNotNull(moviesView);

    mMoviesView.setPresenter(this);
  }


  @Override
  public void start() {
    loadMovies(false);
  }

  @Override
  public void loadMovies(boolean forceUpdate) {
    loadMovies(forceUpdate || mFirstLoad, true);
    mFirstLoad = false;
  }

  @Override
  public void openMovieDetails(@NonNull Movie requestedMovie) {
    checkNotNull(requestedMovie, "Requested Movies can't be null");
    mMoviesView.showMovieDetailsUi(requestedMovie.getId());
  }


  private void loadMovies(boolean force, final boolean showLoadingIndicator) {
    if (showLoadingIndicator) {
      mMoviesView.setLoadingIndicator(true);
    }

    if (force) {
      mMoviesRepository.refreshAll();
    }

    mMoviesRepository.getMovies(new LoadMoviesCallback() {
      @Override
      public void onMoviesLoaded(List<Movie> movies) {
        if (showLoadingIndicator) {
          mMoviesView.setLoadingIndicator(false);

          if (movies.isEmpty()) {
            mMoviesView.showNoMovies();
          } else {
            mMoviesView.showMovies(movies);
          }
        }
      }

      @Override
      public void onDataNotAvailable() {
        mMoviesView.showError();
      }
    });
  }
}
