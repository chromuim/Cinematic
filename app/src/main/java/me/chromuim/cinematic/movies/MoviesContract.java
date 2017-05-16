package me.chromuim.cinematic.movies;

import android.support.annotation.NonNull;
import java.util.List;
import me.chromuim.cinematic.core.BasePresenter;
import me.chromuim.cinematic.core.BaseView;
import me.chromuim.cinematic.data.Movie;

/**
 * Created by chromuim on 14.05.17.
 */

public interface MoviesContract {

  interface View extends BaseView<Presenter> {

    void showMovies(List<Movie> movies);

    void showMovieDetailsUi(int movieId);

    void showNoMovies();

    void setLoadingIndicator(boolean active);

    void showError();
  }

  interface Presenter extends BasePresenter {

    void loadMovies(boolean forceUpdate);

    void openMovieDetails(@NonNull Movie requestedMovie);
  }
}
