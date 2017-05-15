package me.chromuim.cinematic.movies;

import me.chromuim.cinematic.core.BasePresenter;
import me.chromuim.cinematic.core.BaseView;

/**
 * Created by nero on 14.05.17.
 */

public interface MoviesContract {

  interface View extends BaseView<Presenter> {

    void showMovies();

    void showNoMovies();
  }

  interface Presenter extends BasePresenter {

    void loadMovies();
  }
}
