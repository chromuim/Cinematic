package me.chromuim.cinematic.moviedetail;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import com.google.common.base.Strings;
import java.util.Locale;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMovieCallback;
import me.chromuim.cinematic.core.Utils;
import me.chromuim.cinematic.core.api.Constants;
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
    String posterPath = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE + movie.getPosterPath();
    mDetailView.showPoster(posterPath);

    String movieTitle = movie.getTitle();
    if (Strings.isNullOrEmpty(movieTitle)) {
      mDetailView.hideTitle();
    } else {
      mDetailView.showTitle(movieTitle);
    }

    String avg = String.format(Locale.ENGLISH, "%.1f", movie.getAverage());
    mDetailView.showAverage(avg);

    String releaseDate = Utils.formatString(movie.getReleaseDate());
    mDetailView.showReleaseDate(releaseDate);

    String overview = movie.getOverview();
    if (Strings.isNullOrEmpty(overview)) {
      mDetailView.hideOverviewCard();
    } else {
      mDetailView.showOverview(overview);
    }

  }


}
