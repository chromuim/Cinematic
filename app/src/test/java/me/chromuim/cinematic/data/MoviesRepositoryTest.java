package me.chromuim.cinematic.data;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Lists;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMovieCallback;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMoviesCallback;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by chromuim on 14.05.17.
 */

public class MoviesRepositoryTest {

  private static List<Movie> MOVIES = Lists
      .newArrayList(new Movie(123, "hoho"), new Movie(111, "haha"), new Movie(444, "heheh"));


  private MoviesRepository mMoviesRepository;

  @Mock
  private MoviesDataSource mLocalDataSource;

  @Mock
  private MoviesDataSource mRemoteDataSource;

  @Mock
  private MoviesDataSource.LoadMovieCallback mLoadMovieCallback;

  @Mock
  private MoviesDataSource.LoadMoviesCallback mLoadMoviesCallback;

  @Captor
  private ArgumentCaptor<MoviesDataSource.LoadMoviesCallback> mLoadMoviesCallbackArgumentCaptor;

  @Captor
  private ArgumentCaptor<MoviesDataSource.LoadMovieCallback> mLoadMovieCallbackArgumentCaptor;


  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mMoviesRepository = MoviesRepository.getInstance(mLocalDataSource, mRemoteDataSource);
  }

  @After
  public void destroy() {
    MoviesRepository.destroyInstance();
  }

  @Test
  public void getMovies_twoCallsToRepo() {
    //when Movies are requested
    mMoviesRepository.getMovies(1, mLoadMoviesCallback);

    verify(mLocalDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());

    // and there were no data available.
    mLoadMoviesCallbackArgumentCaptor.getValue().onDataNotAvailable();

    // Then fetch data from remote
    verify(mRemoteDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());

    // load the data in cache
    mLoadMoviesCallbackArgumentCaptor.getValue().onMoviesLoaded(MOVIES);

    //second call to api
    mMoviesRepository.getMovies(anyInt(), mLoadMoviesCallback);

    verify(mLocalDataSource).getMovies(anyInt(), any(LoadMoviesCallback.class));

  }

  @Test
  public void getMovies_requestFromLocalRepo() {
    // when
    mMoviesRepository.getMovies(anyInt(), mLoadMoviesCallback);

    //then
    verify(mLocalDataSource).getMovies(anyInt(), any(LoadMoviesCallback.class));
  }

  @Test
  public void getMovie_requestMovieFromLocalRepo() {
    int movieId = 111;

    mMoviesRepository.getMovie(movieId, mLoadMovieCallback);

    verify(mLocalDataSource).getMovie(eq(movieId), any(LoadMovieCallback.class));
  }

  @Test
  public void getMovie_requestMovieFromRemoteRepo() {
    int movieId = 111;
    //when movie is requested by it's ID
    mMoviesRepository.getMovie(movieId, mLoadMovieCallback);

    //first will be looked in local repo
    verify(mLocalDataSource).getMovie(eq(movieId), mLoadMovieCallbackArgumentCaptor.capture());

    //if not found, request the data from Remote Server.
    mLoadMovieCallbackArgumentCaptor.getValue().onDataNotAvailable();

    verify(mRemoteDataSource).getMovie(eq(movieId), any(LoadMovieCallback.class));
  }

  @Test
  public void save_savesMovieToRepo() {
    Movie movie = new Movie(333, "should be saved");
    mMoviesRepository.save(movie);

    verify(mLocalDataSource).save(movie);
    verify(mRemoteDataSource).save(movie);

    Assert.assertThat(mMoviesRepository.mCachedMovies.size(), is(1));
  }

  @Test
  public void deleteAll_deleteAllEntriesFromBothRepos() {
    mMoviesRepository.deleteAll();

    verify(mLocalDataSource).deleteAll();
    verify(mRemoteDataSource).deleteAll();

    Assert.assertThat(mMoviesRepository.mCachedMovies.size(), is(0));
  }

  @Test
  public void refreshAll_loadDataFromRemoteWhenCacheIsDirty() {
    //when force load/refresh
    mMoviesRepository.refreshAll();

    //then when getting new data it will load from remote.
    mMoviesRepository.getMovies(anyInt(), mLoadMoviesCallback);

    verify(mRemoteDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());

    mLoadMoviesCallbackArgumentCaptor.getValue().onMoviesLoaded(MOVIES);

    // the local repo was never being called.
    verify(mLocalDataSource, never()).getMovies(anyInt(), mLoadMoviesCallback);
    verify(mLoadMoviesCallback).onMoviesLoaded(eq(MOVIES));

  }

  @Test
  public void refreshAll_loadedDataFromRemoteBeingSavedToCache() {
    //when force load/refresh
    mMoviesRepository.refreshAll();
    //and movies are requested
    mMoviesRepository.getMovies(anyInt(), mLoadMoviesCallback);

    //and remote going to get the data
    verify(mRemoteDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());
    mLoadMoviesCallbackArgumentCaptor.getValue().onMoviesLoaded(MOVIES);

    //then the data must be saved in cache/local
    verify(mLocalDataSource, times(MOVIES.size())).save(any(Movie.class));


  }

  @Test
  public void getMovies_localRepoNotAvailableSoGetDataFromRemote() {
    // when get movies are retrieved.
    mMoviesRepository.getMovies(anyInt(), mLoadMoviesCallback);

    // and local repo returned no values (for some reason)
    verify(mLocalDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());
    mLoadMoviesCallbackArgumentCaptor.getValue().onDataNotAvailable();

    // then the data will be fetched from remote repo
    verify(mRemoteDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());
    mLoadMoviesCallbackArgumentCaptor.getValue().onMoviesLoaded(MOVIES);

    // the movies are returned from local Repo
    mLoadMoviesCallback.onMoviesLoaded(eq(MOVIES));
  }

  @Test
  public void getMovies_bothReposNotAvailable() {
    // when data is requested
    mMoviesRepository.getMovies(anyInt(), mLoadMoviesCallback);

    // and local repo delivered nothing
    verify(mLocalDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());
    mLoadMoviesCallbackArgumentCaptor.getValue().onDataNotAvailable();

    // and remote repo being called , and also delivered nothing
    verify(mRemoteDataSource).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());
    mLoadMoviesCallbackArgumentCaptor.getValue().onDataNotAvailable();

    // then make sure no data available being called
    verify(mLoadMoviesCallback).onDataNotAvailable();
  }

  @Test
  public void getMovie_bothReposNotAvailable() {
    int movieId = 134;

    //when a movie is being requested
    mMoviesRepository.getMovie(movieId, mLoadMovieCallback);

    // request the data from local, but return nothing
    verify(mLocalDataSource).getMovie(eq(movieId), mLoadMovieCallbackArgumentCaptor.capture());
    mLoadMovieCallbackArgumentCaptor.getValue().onDataNotAvailable();

    // try to request it from remote, but also return nothing
    verify(mRemoteDataSource).getMovie(eq(movieId), mLoadMovieCallbackArgumentCaptor.capture());
    mLoadMovieCallbackArgumentCaptor.getValue().onDataNotAvailable();

    //then return no data available
    verify(mLoadMovieCallback).onDataNotAvailable();
  }


}
