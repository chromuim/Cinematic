package me.chromuim.cinematic.moviedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import me.chromuim.cinematic.moviedetail.MovieDetailContract.Presenter;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

  private static final String EXTRA_MOVIE_ID = "extra_movie_id_fragment";

  MovieDetailContract.Presenter mPresenter;

  public static MovieDetailFragment newInstance(int movieId) {
    Bundle args = new Bundle();
    args.putInt(EXTRA_MOVIE_ID, movieId);
    MovieDetailFragment fragment = new MovieDetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  // Contract Methods.
  @Override
  public void setPresenter(Presenter presenter) {
    mPresenter = presenter;
  }

  @Override
  public void showNoMovieDetailInformation() {

  }
}
