package me.chromuim.cinematic;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.data.FakeMoviesRemoteRepo;
import me.chromuim.cinematic.data.MoviesRepository;

/**
 * Created by chromuim on 16.05.17.
 */

public class Injection {

  public static MoviesRepository repositoryProvider(@NonNull Context context) {
    checkNotNull(context);

    MoviesDataSource remote = FakeMoviesRemoteRepo.getInstance();

    return MoviesRepository.getInstance()
    return null;
  }
}
