package me.chromuim.cinematic.core;

import android.support.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by chromuim on 09.06.17.
 */

public final class Utils {

  @Nullable
  public static String formatString(String inputDate) {
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    try {
      return output.format(input.parse(inputDate));
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
