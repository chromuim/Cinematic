package me.chromuim.cinematic.data.local;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.local.MoviesContract.MoviesEntry;

/**
 * Created by chromuim on 17.05.17.
 */

public class MoviesLocalDataSource implements MoviesDataSource {

  private static final String TAG = "MoviesLocalDataSource";

  private static MoviesLocalDataSource INSTANCE;
  private final MoviesDbHelper mDbHelper;

  private SQLiteDatabase mDb;

  private MoviesLocalDataSource(@NonNull Context context) {
    checkNotNull(context);
    mDbHelper = new MoviesDbHelper(context);
    mDb = mDbHelper.getWritableDatabase();
  }

  public static MoviesLocalDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new MoviesLocalDataSource(context);
    }
    return INSTANCE;
  }

  @Nullable
  @Override
  public List<Movie> getMovies(String sortType, int pageNo) {
    List<Movie> movieList = new ArrayList<>();

    try {
      Cursor cursor = mDb.query(MoviesEntry.TABLE_NAME, null, null, null, null, null, null, null);
      if (cursor != null && cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
          int movieId = cursor.getInt(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_ID));
          String title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_TITLE));
          String overview = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_OVERVIEW));
          String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
          String backDropPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_BACKDROP_PATH));
          float average = cursor.getFloat(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_AVERAGE));
          String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE));

          Movie movie = new Movie(movieId, title, overview, posterPath, backDropPath, average, releaseDate);
          movieList.add(movie);
        }
      }
      if (cursor != null) {
        cursor.close();
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      Log.e(TAG, "getMovies: ", e);
    }

    return movieList;
  }

  @Nullable
  @Override
  public Movie getMovie(int movieId) {
    String selection = MoviesEntry.COLUMN_MOVIE_ID + " = ?";
    String[] selectionArgs = new String[]{String.valueOf(movieId)};

    Movie movie = null;
    try {
      Cursor cursor = mDb.query(MoviesEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null, null);
      movie = null;
      if (cursor != null && cursor.getCount() > 0) {
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_TITLE));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_OVERVIEW));
        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
        String backDropPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_BACKDROP_PATH));
        float average = cursor.getFloat(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_AVERAGE));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE));

        movie = new Movie(id, title, overview, posterPath, backDropPath, average, releaseDate);
      }

      if (cursor != null) {
        cursor.close();
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      Log.e(TAG, "getMovie: ", e);
    }

    return movie;
  }

  @Nullable
  @Override
  public List<MovieVideo> getMovieVideos(int movieId) {
    // empty for now , since i'm not saving videos in Db
    return null;
  }

  @Nullable
  @Override
  public List<MovieReview> getMovieReviews(int movieId) {
    // nothing yet
    return null;
  }

  @Override
  public void save(@NonNull Movie movie) {
    checkNotNull(movie);

    try {
      ContentValues values = new ContentValues();
      values.put(MoviesEntry.COLUMN_MOVIE_ID, movie.getId());
      values.put(MoviesEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
      values.put(MoviesEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
      values.put(MoviesEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
      values.put(MoviesEntry.COLUMN_MOVIE_BACKDROP_PATH, movie.getBackdropPath());
      values.put(MoviesEntry.COLUMN_MOVIE_AVERAGE, movie.getAverage());
      values.put(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());

      mDb.insert(MoviesEntry.TABLE_NAME, null, values);
    } catch (Exception e) {
      e.printStackTrace();
      Log.e(TAG, "save: ", e);
    }
  }

  @Override
  public void deleteAll() {
    try {
      mDb.delete(MoviesEntry.TABLE_NAME, null, null);
    } catch (Exception e) {
      e.printStackTrace();
      Log.e(TAG, "deleteAll: ", e);
    }
  }

  @Override
  public void refreshAll() {
    // no need
  }
}
