package me.chromuim.cinematic;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.data.MoviesRepository;
import me.chromuim.cinematic.data.local.MoviesLocalDataSource;
import me.chromuim.cinematic.data.remote.MoviesRemoteDataSource;

/**
 * Created by chromuim on 17.05.17.
 */

public class Injection {


  public static MoviesRepository repositoryProvider(@NonNull Context context) {
    checkNotNull(context);

    MoviesDataSource remote = MoviesRemoteDataSource.getInstance();
    MoviesDataSource local = MoviesLocalDataSource.getInstance(context);

    return MoviesRepository.getInstance(local, remote);
  }
}
