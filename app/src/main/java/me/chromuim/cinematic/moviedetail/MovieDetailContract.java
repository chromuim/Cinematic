package me.chromuim.cinematic.moviedetail;

import java.util.List;
import me.chromuim.cinematic.core.BasePresenter;
import me.chromuim.cinematic.core.BaseView;
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
  }

  interface Presenter extends BasePresenter {

    void loadMovieVideos();
  }
}
