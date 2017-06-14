package me.chromuim.cinematic.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.core.api.MovieApiService;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.core.api.MovieReviewResponse;
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


  @Nullable
  @Override
  public List<Movie> getMovies(String sortType, int pageNo) {
    return loadMovies(Constants.SORT_POPULARITY, pageNo);
  }


  @Nullable
  @Override
  public Movie getMovie(int movieId) {
    return null;
  }

  @Override
  public void getMovieVideos(int movieId, @NonNull LoadMovieVideosCallback callback) {
    Call<MovieVideosResponse> movieVideosResponseCall = mClient.loadMovieDetailVideos(movieId);
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
  public void getMovieReviews(int movieId, @NonNull LoadMovieReviewsCallback callback) {
    Call<MovieReviewResponse> movieReviewReposeCall = mClient.loadMovieDetailReviews(movieId);
    movieReviewReposeCall.enqueue(new Callback<MovieReviewResponse>() {
      @Override
      public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
        List<MovieReview> movieReviews = response.body().getResults();
        if (movieReviews.isEmpty()) {
          callback.onDataNotAvailable();
        } else {
          callback.onReviewLoaded(movieReviews);
        }
      }

      @Override
      public void onFailure(Call<MovieReviewResponse> call, Throwable t) {
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

  @Nullable
  private List<Movie> loadMovies(@NonNull String sortType, int pageNo) {
    Call<ResponseList<Movie>> movies = mClient.loadMovies(sortType, pageNo);
    List<Movie> movieList = null;
    try {
      Response<ResponseList<Movie>> response = movies.execute();
      if (response.body() != null) {
        movieList = response.body().getResults();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return movieList;
  }
}