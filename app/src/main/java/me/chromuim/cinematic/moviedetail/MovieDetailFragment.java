package me.chromuim.cinematic.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import me.chromuim.cinematic.R;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.moviedetail.MovieDetailContract.Presenter;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

  private static final String EXTRA_MOVIE_ID = "extra_movie_id_fragment";

  //region Movie Detail Info
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
  //endregion

  //region Movie Detail Overview
  @BindView(R.id.movie_detail_overview_card_view)
  CardView mMovieOverviewCard;

  @BindView(R.id.movie_detail_overview_text)
  TextView mMovieOverviewText;
  //endregion

  @BindView(R.id.movie_detail_videos_card_view)
  CardView mMovieVideosCard;

  @BindView(R.id.movie_detail_videos_recycler_view)
  RecyclerView mMovieVideosRecyclerView;


  MovieVideosAdapter mMovieVideosAdapter;

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

    mMovieVideosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    mMovieVideosRecyclerView.setAdapter(mMovieVideosAdapter);

    return rootView;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mMovieVideosAdapter = new MovieVideosAdapter(getContext(), new ArrayList<>(0));
  }

  @Override
  public void onResume() {
    super.onResume();
    mPresenter.start();
    mPresenter.loadMovieVideos();
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
  public void showVideos(List<MovieVideo> movieVideos) {
    mMovieVideosAdapter.swap(movieVideos);

    mMovieVideosCard.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideVideos() {
    mMovieVideosCard.setVisibility(View.GONE);
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
