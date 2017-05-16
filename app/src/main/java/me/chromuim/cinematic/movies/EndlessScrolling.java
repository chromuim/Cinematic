package me.chromuim.cinematic.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chromuim on 16.05.17.
 */

public abstract class EndlessScrolling extends RecyclerView.OnScrollListener {

  private static final int THRESHOLD = 5;

  @NonNull
  private GridLayoutManager mGridLayoutManager;


  private boolean isLoading = false;

  public EndlessScrolling(@NonNull GridLayoutManager gridLayoutManager) {
    mGridLayoutManager = gridLayoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int totalItems = mGridLayoutManager.getItemCount();
    int lastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
    boolean reachedEnd = lastVisibleItemPosition + THRESHOLD >= totalItems;
    if (!isLoading && reachedEnd && totalItems > 0) {
      isLoading = true;
      loadMore();
    }
  }

  public abstract void loadMore();
}

