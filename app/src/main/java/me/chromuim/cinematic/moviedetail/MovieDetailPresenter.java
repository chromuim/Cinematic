package me.chromuim.cinematic.moviedetail;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.google.common.base.Strings;
import java.util.List;
import java.util.Locale;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMovieReviewsCallback;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMovieVideosCallback;
import me.chromuim.cinematic.core.Utils;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.MovieLoader;
import me.chromuim.cinematic.data.MoviesRepository;
import me.chromuim.cinematic.moviedetail.MovieDetailContract.View;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter, LoaderManager.LoaderCallbacks<Movie> {

  private static final int LOADER_MOVIE = 222;

  private final MoviesRepository mRepository;

  private final MovieDetailContract.View mDetailView;

  private MovieLoader mMovieLoader;

  private LoaderManager mLoaderManager;

  private final int mMovieId;

  public MovieDetailPresenter(@NonNull MoviesRepository repository, @NonNull View view,
      @NonNull MovieLoader movieLoader, @NonNull LoaderManager loaderManager, int movieId) {
    mRepository = checkNotNull(repository);
    mDetailView = checkNotNull(view);
    mMovieLoader = checkNotNull(movieLoader);
    mLoaderManager = checkNotNull(loaderManager);

    mMovieId = movieId;

    mDetailView.setPresenter(this);
  }

  @Override
  public void start() {
    mLoaderManager.initLoader(LOADER_MOVIE, null, this);
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

  @Override
  public void loadMovieVideos() {
    mRepository.getMovieVideos(mMovieId, new LoadMovieVideosCallback() {
      @Override
      public void onVideosLoaded(List<MovieVideo> movieVideos) {
        mDetailView.showVideos(movieVideos);
      }

      @Override
      public void onDataNotAvailable() {
        mDetailView.hideVideos();
      }
    });
  }

  @Override
  public void loadMovieReviews() {
    mRepository.getMovieReviews(mMovieId, new LoadMovieReviewsCallback() {
      @Override
      public void onReviewLoaded(List<MovieReview> movieReviews) {
        mDetailView.showReviews(movieReviews);
      }

      @Override
      public void onDataNotAvailable() {
        mDetailView.hideReviews();
      }
    });
  }

  @Override
  public void openYoutube(String url) {
    mDetailView.showYoutube(url);
  }

  @Override
  public void openReview(String url) {
    mDetailView.openReview(url);
  }

  //Loader
  @Override
  public Loader<Movie> onCreateLoader(int id, Bundle args) {
    if (mMovieId == 0) {
      return null;
    }
    return mMovieLoader;
  }

  @Override
  public void onLoadFinished(Loader<Movie> loader, Movie data) {
    if (data != null) {
      showMovie(data);
    } else {
      mDetailView.showNoMovieDetailInformation();
    }
  }

  @Override
  public void onLoaderReset(Loader<Movie> loader) {
    //nothing
  }
}
