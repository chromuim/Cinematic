package me.chromuim.cinematic.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chromuim on 16.05.17.
 */

public abstract class EndlessScrolling extends RecyclerView.OnScrollListener {

  private static final String TAG = "EndlessScrolling";
  private static final int THRESHOLD = 8;

  private int mPreviousTotal = 0;

  private boolean mLoading = true;

  private int mPageNo = 1;
  @NonNull
  private GridLayoutManager mGridLayoutManager;


  public EndlessScrolling(@NonNull GridLayoutManager gridLayoutManager) {
    mGridLayoutManager = gridLayoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int totalItemCount = mGridLayoutManager.getItemCount();
    int lastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();

    boolean reachedNearEnd = lastVisibleItemPosition + THRESHOLD > totalItemCount;
    if (dy > 0) {
      if (mLoading) {
        if (totalItemCount > mPreviousTotal) {
          mLoading = false;
          mPreviousTotal = totalItemCount;
        }
      }
      if (!mLoading && reachedNearEnd) {
        loadMore(++mPageNo);
        mLoading = true;
      }
    }
  }


  public abstract void loadMore(int currentPage);
}

