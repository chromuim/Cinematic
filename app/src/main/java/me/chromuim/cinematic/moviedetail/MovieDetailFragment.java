package me.chromuim.cinematic.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import me.chromuim.cinematic.R;
import me.chromuim.cinematic.moviedetail.MovieDetailContract.Presenter;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

  private static final String EXTRA_MOVIE_ID = "extra_movie_id_fragment";

  @BindView(R.id.movie_detail_card_view)
  CardView mMovieDetail;

  @BindView(R.id.movie_detail_poster_image)
  ImageView mMovieDetailPoster;

  @BindView(R.id.movie_detail_title_text_view)
  TextView mMovieDetailTitle;

  @BindView(R.id.movie_detail_rating_text_view)
  TextView mMovieDetailRatings;

  @BindView(R.id.movie_detail_release_date_text_view)
  TextView mMovieDetailReleaseDate;

  @BindView(R.id.movie_detail_overview_card_view)
  CardView mMovieOverviewCard;

  @BindView(R.id.movie_detail_overview_text)
  TextView mMovieOverviewText;


  MovieDetailContract.Presenter mPresenter;

  public MovieDetailFragment() {
  }

  public static MovieDetailFragment newInstance(int movieId) {
    Bundle args = new Bundle();
    args.putInt(EXTRA_MOVIE_ID, movieId);
    MovieDetailFragment fragment = new MovieDetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false);
    ButterKnife.bind(this, rootView);

    return rootView;
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
    //@Todo
  }

  @Override
  public void hideTitle() {
    mMovieDetailTitle.setVisibility(View.GONE);
  }

  @Override
  public void showTitle(String movieTitle) {
    mMovieDetailTitle.setText(movieTitle);
  }

  @Override
  public void showAverage(String average) {
    mMovieDetailRatings.setText(average);
  }

  @Override
  public void hideOverviewCard() {
    mMovieOverviewCard.setVisibility(View.GONE);
  }

  @Override
  public void showOverview(String overview) {
    mMovieOverviewText.setText(overview);
  }

  @Override
  public void showPoster(String posterPath) {
    Picasso.with(getContext()).load(posterPath).into(mMovieDetailPoster);
  }

  @Override
  public void showReleaseDate(String releaseDate) {
    mMovieDetailReleaseDate.setText(getString(R.string.movie_detail_release_date, releaseDate));
  }
}
