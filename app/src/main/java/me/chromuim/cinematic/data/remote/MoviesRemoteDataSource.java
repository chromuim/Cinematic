package me.chromuim.cinematic.data.remote;

import android.support.annotation.NonNull;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.core.api.MovieApiService;
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.core.api.MovieVideosResponse;
import me.chromuim.cinematic.core.api.MoviesRetrofitClient;
import me.chromuim.cinematic.core.api.ResponseList;
import me.chromuim.cinematic.data.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chromuim on 17.05.17.
 */

public class MoviesRemoteDataSource implements MoviesDataSource {

  private static MoviesRemoteDataSource INSTANCE;

  @NonNull
  private final MovieApiService mClient;


  private MoviesRemoteDataSource() {
    mClient = MoviesRetrofitClient.createService(MovieApiService.class);
  }

  public static MoviesRemoteDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MoviesRemoteDataSource();
    }
    return INSTANCE;
  }

  @Override
  public void getMovies(@NonNull LoadMoviesCallback callback) {
//    loadMovies(Constants.SORT_POPULARITY,, callback);
  }

  @Override
  public void getMovies(int currentPage, @NonNull LoadMoviesCallback callback) {
    loadMovies(Constants.SORT_POPULARITY, currentPage, callback);
  }

  @Override
  public void getMovie(int movieId, @NonNull LoadMovieCallback callback) {

  }

  @Override
  public void getMovieVideos(int movieId, @NonNull LoadMovieVideosCallback callback) {
    Call<MovieVideosResponse> movieVideosResponseCall = mClient.laodMovieDetailVideos(movieId);
    movieVideosResponseCall.enqueue(new Callback<MovieVideosResponse>() {
      @Override
      public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
        List<MovieVideo> movieVideos = response.body().getResults();
        if (movieVideos.isEmpty()) {
          callback.onDataNotAvailable();
        } else {
          callback.onVideosLoaded(movieVideos);
        }
      }

      @Override
      public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
        callback.onDataNotAvailable();
      }
    });
  }

  @Override
  public void save(@NonNull Movie movie) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public void refreshAll() {

  }

  private void loadMovies(@NonNull String sortType, int pageNo, @NonNull LoadMoviesCallback callback) {
    Call<ResponseList<Movie>> movies = mClient.loadMovies(sortType, pageNo);
    movies.enqueue(new Callback<ResponseList<Movie>>() {
      @Override
      public void onResponse(Call<ResponseList<Movie>> call, Response<ResponseList<Movie>> response) {
        List<Movie> moviesList = response.body().getResults();
        if (moviesList.isEmpty()) {
          callback.onDataNotAvailable();
        }
        callback.onMoviesLoaded(moviesList);
      }

      @Override
      public void onFailure(Call<ResponseList<Movie>> call, Throwable t) {
        callback.onDataNotAvailable();
      }
    });
  }
}
