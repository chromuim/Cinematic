package me.chromuim.cinematic.data;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

/**
 * Created by chromuim on 12.06.17.
 */

public class MoviesLoader extends AsyncTaskLoader<List<Movie>>
    implements MoviesRepository.MoviesRepositoryObserver {

  private MoviesRepository mRepository;

  private String mSortType;
  private int mPageNo;

  public MoviesLoader(Context context, @NonNull MoviesRepository repository, String sortType, int pageNo) {
    super(context);
    mRepository = checkNotNull(repository);
    mSortType = sortType;
    mPageNo = pageNo;
  }


  @Override
  public List<Movie> loadInBackground() {
    return mRepository.getMovies(mSortType, mPageNo);
  }

  @Override
  public void deliverResult(List<Movie> data) {
    if (isReset()) {
      return;
    }
    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override
  protected void onStartLoading() {
    if (mRepository.isCacheAvailable()) {
      deliverResult(mRepository.getCachedMovies());
    }

    mRepository.addContentObserver(this);
//    forceLoad();

    if (takeContentChanged() || !mRepository.isCacheAvailable()) {
      forceLoad();
    }
  }

  @Override
  protected void onStopLoading() {
    cancelLoad();
  }

  @Override
  protected void onReset() {
    onStartLoading();
    mRepository.removeContentObserver(this);
  }

  @Override
  public void onMoviesChanged() {
    if (isStarted()) {
      forceLoad();
    }
  }
}
