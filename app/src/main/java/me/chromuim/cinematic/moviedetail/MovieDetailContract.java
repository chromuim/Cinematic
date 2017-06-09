package me.chromuim.cinematic.moviedetail;

import me.chromuim.cinematic.core.BasePresenter;
import me.chromuim.cinematic.core.BaseView;

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
  }

  interface Presenter extends BasePresenter {

  }
}
