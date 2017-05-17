package me.chromuim.cinematic.data.local;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.local.MoviesContract.MoviesEntry;

/**
 * Created by chromuim on 17.05.17.
 */

public class MoviesLocalDataSource implements MoviesDataSource {

  private static MoviesLocalDataSource INSTANCE;
  private final MoviesDbHelper mDbHelper;

  private MoviesLocalDataSource(@NonNull Context context) {
    checkNotNull(context);
    mDbHelper = new MoviesDbHelper(context);
  }

  public static MoviesLocalDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new MoviesLocalDataSource(context);
    }
    return INSTANCE;
  }

  @Override
  public void getMovies(@NonNull LoadMoviesCallback callback) {
    SQLiteDatabase database = mDbHelper.getReadableDatabase();
    List<Movie> movieList = new ArrayList<>();

    Cursor cursor = database.query(MoviesEntry.TABLE_NAME, null, null, null, null, null, null, null);
    if (cursor != null && cursor.moveToFirst()) {
      while (cursor.moveToNext()) {
        int movieId = cursor.getInt(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_TITLE));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_OVERVIEW));
        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
        float average = cursor.getFloat(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_AVERAGE));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE));

        Movie movie = new Movie(movieId, title, overview, posterPath, average, releaseDate);
        movieList.add(movie);
      }
    }
    if (cursor != null) {
      cursor.close();
    }
    database.close();

    if (movieList.isEmpty()) {
      callback.onDataNotAvailable();
    } else {
      callback.onMoviesLoaded(movieList);
    }
  }

  @Override
  public void getMovie(int movieId, @NonNull LoadMovieCallback callback) {
    SQLiteDatabase database = mDbHelper.getReadableDatabase();

    String selection = MoviesEntry.COLUMN_MOVIE_ID + " = ?";
    String[] selectionArgs = new String[]{String.valueOf(movieId)};

    Cursor cursor = database.query(MoviesEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null, null);
    Movie movie = null;
    if (cursor != null && cursor.moveToFirst()) {
      int id = cursor.getInt(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_ID));
      String title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_TITLE));
      String overview = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_OVERVIEW));
      String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
      float average = cursor.getFloat(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_AVERAGE));
      String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE));

      movie = new Movie(id, title, overview, posterPath, average, releaseDate);
    }

    if (cursor != null) {
      cursor.close();
    }
    database.close();

    if (movie != null) {
      callback.onMovieLoaded(movie);
    } else {
      callback.onDataNotAvailable();
    }
  }

  @Override
  public void save(@NonNull Movie movie) {
    checkNotNull(movie);
    SQLiteDatabase database = mDbHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(MoviesEntry.COLUMN_MOVIE_ID, movie.getId());
    values.put(MoviesEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
    values.put(MoviesEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
    values.put(MoviesEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
    values.put(MoviesEntry.COLUMN_MOVIE_AVERAGE, movie.getAverage());
    values.put(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());

    database.insert(MoviesEntry.TABLE_NAME, null, values);
    database.close();
  }

  @Override
  public void deleteAll() {
    SQLiteDatabase database = mDbHelper.getWritableDatabase();
    database.delete(MoviesEntry.TABLE_NAME, null, null);
    database.close();
  }

  @Override
  public void refreshAll() {
    // no need
  }
}
