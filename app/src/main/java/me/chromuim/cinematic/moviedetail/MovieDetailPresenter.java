package me.chromuim.cinematic.moviedetail;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import com.google.common.base.Strings;
import java.util.List;
import java.util.Locale;
import me.chromuim.cinematic.core.Utils;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.MovieLoader;
import me.chromuim.cinematic.data.MovieReviewsLoader;
import me.chromuim.cinematic.data.MovieVideosLoader;
import me.chromuim.cinematic.data.MoviesRepository;
import me.chromuim.cinematic.moviedetail.MovieDetailContract.View;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

  private static final int LOADER_MOVIE = 2;
  private static final int LOADER_MOVIE_VIDEOS = 21;
  private static final int LOADER_MOVIE_REVIEWS = 22;

  private final MoviesRepository mRepository;

  private final MovieDetailContract.View mDetailView;
  private final int mMovieId;
  private MovieLoader mMovieLoader;
  private MovieReviewsLoader mReviewsLoader;
  private MovieVideosLoader mVideosLoader;
  private LoaderManager mLoaderManager;

  private LoaderCallbacks<Movie> mMovieLoaderCallbacks = new LoaderCallbacks<Movie>() {
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

    }
  };

  private LoaderCallbacks<List<MovieVideo>> mVideosLoaderCallbacks = new LoaderCallbacks<List<MovieVideo>>() {
    @Override
    public Loader<List<MovieVideo>> onCreateLoader(int id, Bundle args) {
      if (mMovieId == 0) {
        return null;
      }
      return mVideosLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<MovieVideo>> loader, List<MovieVideo> data) {
      if (data == null) {
        mDetailView.hideVideos();
      } else {
        if (data.isEmpty()) {
          mDetailView.hideVideos();
        } else {
          mDetailView.showVideos(data);
        }
      }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieVideo>> loader) {

    }
  };

  private LoaderCallbacks<List<MovieReview>> mReviewsLoaderCallbacks = new LoaderCallbacks<List<MovieReview>>() {
    @Override
    public Loader<List<MovieReview>> onCreateLoader(int id, Bundle args) {
      if (mMovieId == 0) {
        return null;
      }
      return mReviewsLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<MovieReview>> loader, List<MovieReview> data) {
      if (data == null) {
        mDetailView.hideReviews();
      } else {
        if (data.isEmpty()) {
          mDetailView.hideReviews();
        } else {
          mDetailView.showReviews(data);
        }
      }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieReview>> loader) {

    }
  };


  public MovieDetailPresenter(@NonNull MoviesRepository repository,
      @NonNull View view,
      @NonNull MovieLoader movieLoader,
      @NonNull MovieVideosLoader movieVideosLoader,
      @NonNull MovieReviewsLoader movieReviewsLoader,
      @NonNull LoaderManager loaderManager,
      int movieId) {
    mRepository = checkNotNull(repository);
    mDetailView = checkNotNull(view);

    mMovieLoader = checkNotNull(movieLoader);
    mVideosLoader = checkNotNull(movieVideosLoader);
    mReviewsLoader = checkNotNull(movieReviewsLoader);

    mLoaderManager = checkNotNull(loaderManager);

    mMovieId = movieId;

    mDetailView.setPresenter(this);
  }

  @Override
  public void start() {
    mLoaderManager.initLoader(LOADER_MOVIE, null, mMovieLoaderCallbacks);
    mLoaderManager.initLoader(LOADER_MOVIE_VIDEOS, null, mVideosLoaderCallbacks);
    mLoaderManager.initLoader(LOADER_MOVIE_REVIEWS, null, mReviewsLoaderCallbacks);
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
  public void openYoutube(String url) {
    mDetailView.showYoutube(url);
  }

  @Override
  public void openReview(String url) {
    mDetailView.openReview(url);
  }
}
