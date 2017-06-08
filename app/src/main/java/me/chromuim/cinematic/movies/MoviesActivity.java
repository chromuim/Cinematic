package me.chromuim.cinematic.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.chromuim.cinematic.Injection;
import me.chromuim.cinematic.R;

/**
 * Created by chromuim on 14.05.17.
 */

public class MoviesActivity extends AppCompatActivity {

  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.navigation_view)
  NavigationView mNavigationView;

  private MoviesPresenter mMoviesPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    ButterKnife.bind(this);

    setSupportActionBar(mToolbar);
    setActionBar();

    if (mNavigationView != null) {
      setNavigationViewContent(mNavigationView);
    }

    //create the fragment
    FragmentManager fm = getSupportFragmentManager();
    MoviesFragment fragment = (MoviesFragment) fm.findFragmentById(R.id.movies_grid_layout_container);
    if (fragment == null) {
      fragment = (MoviesFragment) MoviesFragment.newInstance();
      fm.beginTransaction().add(R.id.movies_grid_layout_container, fragment).commit();
    }

    mMoviesPresenter = new MoviesPresenter(Injection.repositoryProvider(getApplicationContext()), fragment);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void setActionBar() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    mToolbar.setNavigationIcon(R.drawable.ic_menu);
  }

  private void setNavigationViewContent(NavigationView nv) {
    nv.setNavigationItemSelectedListener(
        item -> {
          switch (item.getItemId()) {
            // we are already in this case
            case R.id.drawer_explore_action:
              break;

            case R.id.drawer_favourite_action:
              //start favourite activity
              break;
            default:
              break;
          }
          item.setChecked(true);
          mDrawerLayout.closeDrawers();
          return true;
        });
  }
}
