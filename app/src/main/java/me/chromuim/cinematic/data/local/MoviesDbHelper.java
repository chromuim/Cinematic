package me.chromuim.cinematic.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import me.chromuim.cinematic.data.local.MoviesContract.MoviesEntry;

/**
 * Created by chromuim on 17.05.17.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 4;
  private static final String DATABASE_NAME = "movies.db";

  public MoviesDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(MoviesEntry.CREATE_TABLE_QUERY);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
    onCreate(db);
  }
}
