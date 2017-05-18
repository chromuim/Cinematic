package me.chromuim.cinematic.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by chromuim on 16.05.17.
 */

public abstract class EndlessScrolling extends RecyclerView.OnScrollListener {

  private static final String TAG = "EndlessScrolling";
  private static final int THRESHOLD = 2;

  private int mPreviousTotal = 0;

  private boolean mLoading = true;

  private int mPageNo = 0;
  @NonNull
  private GridLayoutManager mGridLayoutManager;


  public EndlessScrolling(@NonNull GridLayoutManager gridLayoutManager) {
    mGridLayoutManager = gridLayoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int visibleItemCount = recyclerView.getChildCount();
    int totalItemCount = mGridLayoutManager.getItemCount();
    int firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

    if (dy > 0) {
      if (mLoading) {
        if (totalItemCount >= mPreviousTotal) {
          mLoading = false;
          mPreviousTotal = totalItemCount;
        }
      }
      if (!mLoading && (firstVisibleItem + visibleItemCount + THRESHOLD) >= totalItemCount) {
        mPageNo++;
        loadMore(mPageNo);
        mLoading = true;
      }
    }



    /*
    Log.i(TAG, "onScrolled: VisibleItemCount : " + visibleItemCount);
    Log.i(TAG, "onScrolled: Previous Total : " + mPreviousTotal);
    Log.i(TAG, "onScrolled: Total Items : " + totalItemCount);
    Log.i(TAG, "onScrolled: FirstVisible : " + firstVisibleItem);
    Log.i(TAG, "onScrolled: IsLoading " + mLoading);
    Log.i(TAG, "onScrolled: Reached End : " + (firstVisibleItem + visibleItemCount + THRESHOLD));
    */

  }


  public abstract void loadMore(int currentPage);
}

