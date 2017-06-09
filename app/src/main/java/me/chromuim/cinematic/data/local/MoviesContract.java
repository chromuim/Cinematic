package me.chromuim.cinematic.data.local;

import android.provider.BaseColumns;

/**
 * Created by chromuim on 16.05.17.
 */

public final class MoviesContract {

  private MoviesContract() {
  }

  public static abstract class MoviesEntry implements BaseColumns {

    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_MOVIE_ID = "movieID";
    public static final String COLUMN_MOVIE_TITLE = "title";
    public static final String COLUMN_MOVIE_OVERVIEW = "overview";
    public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";
    public static final String COLUMN_MOVIE_BACKDROP_PATH = "backdrop_path";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
    public static final String COLUMN_MOVIE_AVERAGE = "average";


    public static final String CREATE_TABLE_QUERY = String.format(
        "CREATE TABLE IF NOT EXISTS  %1$S ( %2$S INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " %3$S INTEGER NOT NULL, " +
            " %4$S TEXT NOT NULL,  " +
            " %5$S TEXT NOT NULL, " +
            " %6$S TEXT NOT NULL, " +
            " %7$S TEXT , " +
            " %8$S TEXT NOT NULL, " +
            " %9$S LONG NOT NULL ); "
        , TABLE_NAME
        , _ID,
        COLUMN_MOVIE_ID,
        COLUMN_MOVIE_TITLE,
        COLUMN_MOVIE_OVERVIEW,
        COLUMN_MOVIE_POSTER_PATH,
        COLUMN_MOVIE_BACKDROP_PATH,
        COLUMN_MOVIE_RELEASE_DATE,
        COLUMN_MOVIE_AVERAGE
    );
  }
}
