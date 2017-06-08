package me.chromuim.cinematic.moviedetail;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMovieCallback;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.MoviesRepository;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

  private final MoviesRepository mRepository;

  private final MovieDetailContract.View mDetailView;

  private final int mMovieId;

  public MovieDetailPresenter(@NonNull MoviesRepository repository, @NonNull MovieDetailContract.View view, int movieId) {
    mRepository = checkNotNull(repository);
    mDetailView = checkNotNull(view);
    mMovieId = movieId;

    mDetailView.setPresenter(this);
  }

  @Override
  public void start() {
    openMovieDetail();
  }

  private void openMovieDetail() {
    if (mMovieId == 0) {
      mDetailView.showNoMovieDetailInformation();
      return;
    }
    mRepository.getMovie(mMovieId, new LoadMovieCallback() {
      @Override
      public void onMovieLoaded(Movie movie) {
        if (movie == null) {
          mDetailView.showNoMovieDetailInformation();
        } else {
          showMovie(movie);
        }
      }

      @Override
      public void onDataNotAvailable() {
        mDetailView.showNoMovieDetailInformation();
      }
    });
  }

  private void showMovie(Movie movie) {

  }


}
