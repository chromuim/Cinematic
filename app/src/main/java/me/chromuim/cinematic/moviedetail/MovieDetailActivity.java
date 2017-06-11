package me.chromuim.cinematic.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import me.chromuim.cinematic.Injection;
import me.chromuim.cinematic.R;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.data.Movie;

/**
 * Created by chromuim on 06.06.17.
 */

public class MovieDetailActivity extends AppCompatActivity {

  private static final String EXTRA_MOVIE_ID = "extra_movie_id";


  @BindView(R.id.coordinator_layout)
  CoordinatorLayout mCoordinatorLayout;

  @BindView(R.id.collapsing_toolbar_layout_detail)
  CollapsingToolbarLayout mCollapsingToolbarLayout;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.movie_detail_thumbnail_toolbar)
  ImageView mMovieThumbnail;

  @BindView(R.id.nested_scroll_view_detail)
  NestedScrollView mNestedScrollView;

  @BindView(R.id.fab_favourite_movie)
  FloatingActionButton mFavActionButton;

  MovieDetailPresenter mPresenter;

  private Movie mMovie;


  public static Intent newIntent(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieDetailActivity.class);
    intent.putExtra(EXTRA_MOVIE_ID, movie);
    return intent;
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.movie_detail_activity);
    ButterKnife.bind(this);

    if (getIntent().hasExtra(EXTRA_MOVIE_ID)) {
      mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);

      MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
      if (movieDetailFragment == null) {
        movieDetailFragment = MovieDetailFragment.newInstance(mMovie.getId());
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_content, movieDetailFragment).commit();
      }
      mPresenter = new MovieDetailPresenter(Injection.repositoryProvider(getApplicationContext()), movieDetailFragment, mMovie.getId());
    }

    setToolbar();
  }

  private void setToolbar() {
    setSupportActionBar(mToolbar);
    ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setDisplayHomeAsUpEnabled(true);
      ab.setDisplayShowHomeEnabled(true);
      mToolbar.setNavigationOnClickListener((view -> onBackPressed()));
    }

    mCollapsingToolbarLayout.setTitle(mMovie.getTitle());
    mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
    setTitle("");
    String fullPath = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_DETAIL_TOOLBAR + mMovie.getBackdropPath();
    Picasso.with(this).load(fullPath).into(mMovieThumbnail);
  }

  //@Todo
  private void updateFabStatus() {

  }
}
