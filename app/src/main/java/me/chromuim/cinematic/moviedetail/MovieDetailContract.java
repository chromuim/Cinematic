package me.chromuim.cinematic.moviedetail;

import me.chromuim.cinematic.core.BasePresenter;
import me.chromuim.cinematic.core.BaseView;

/**
 * Created by chromuim on 06.06.17.
 */

public interface MovieDetailContract {

  interface View extends BaseView<Presenter> {

    void showNoMovieDetailInformation();
  }

  interface Presenter extends BasePresenter {

  }
}
