package me.chromuim.cinematic.moviedetail;

import java.util.List;
import me.chromuim.cinematic.core.BasePresenter;
import me.chromuim.cinematic.core.BaseView;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieVideo;

/**
 * Created by chromuim on 06.06.17.
 */

public interface MovieDetailContract {

  interface View extends BaseView<Presenter> {

    void showNoMovieDetailInformation();

    void hideTitle();

    void showTitle(String movieTitle);

    void showAverage(String average);

    void showReleaseDate(String releaseDate);

    void showPoster(String posterPath);

    void hideOverviewCard();

    void showOverview(String overview);

    void showVideos(List<MovieVideo> movieVideos);

    void hideVideos();

    void showYoutube(String url);

    void showReviews(List<MovieReview> movieReviews);

    void hideReviews();

    void openReview(String url);
  }

  interface Presenter extends BasePresenter {

    void openYoutube(String url);

    void openReview(String url);
  }
}
