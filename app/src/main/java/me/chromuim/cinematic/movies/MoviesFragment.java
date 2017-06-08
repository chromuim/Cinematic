package me.chromuim.cinematic.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import me.chromuim.cinematic.R;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.moviedetail.MovieDetailActivity;
import me.chromuim.cinematic.movies.MoviesContract.Presenter;

/**
 * Created by chromuim on 16.05.17.
 */

public class MoviesFragment extends Fragment implements MoviesContract.View, MoviesAdapter.onMovieItemClickListener {

  @BindView(R.id.rv_movies_grid)
  RecyclerView mRecyclerView;

  @BindView(R.id.swipe_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;

  @BindView(R.id.no_movies_textView)
  TextView mNoMoviesLayout;

  private MoviesContract.Presenter mPresenter;

  private MoviesAdapter mMoviesAdapter;

  public MoviesFragment() {
  }

  @NonNull
  public static Fragment newInstance() {
    return new MoviesFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.movies_grid, container, false);
    ButterKnife.bind(this, view);

    mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadMovies(false));

    setHasOptionsMenu(true);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(gridLayoutManager);
    mRecyclerView.addOnScrollListener(new EndlessScrolling(gridLayoutManager) {
      @Override
      public void loadMore(int currentPage) {
        mPresenter.loadMore(currentPage);
      }
    });
    mRecyclerView.setAdapter(mMoviesAdapter);

    return view;

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mMoviesAdapter = new MoviesAdapter(new ArrayList<>(0), this);
  }

  @Override
  public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  // from Contract.View
  @Override
  public void setPresenter(Presenter presenter) {
    mPresenter = presenter;
  }

  @Override
  public void showMovies(List<Movie> movies) {
    mMoviesAdapter.swapData(movies);

    mRecyclerView.setVisibility(View.VISIBLE);
    mNoMoviesLayout.setVisibility(View.GONE);
  }

  @Override
  public void showMovieDetailsUi(Movie movie) {
    Intent intent = MovieDetailActivity.newIntent(getContext(), movie);
    startActivity(intent);
  }

  @Override
  public void showNoMovies() {
    mRecyclerView.setVisibility(View.GONE);

    mNoMoviesLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void setLoadingIndicator(final boolean active) {
    if (getView() == null) {
      return;
    }
    mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
  }

  @Override
  public void showError() {
    Snackbar.make(getView(), "Error is Happened", Snackbar.LENGTH_LONG).show();
  }

  @Override
  public void onItemClick(@NonNull Movie movie) {
    mPresenter.openMovieDetails(movie);
  }
}
