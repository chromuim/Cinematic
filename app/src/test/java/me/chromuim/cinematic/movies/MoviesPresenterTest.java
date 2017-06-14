package me.chromuim.cinematic.movies;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Lists;
import java.util.List;
import me.chromuim.cinematic.core.MoviesDataSource.LoadMoviesCallback;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.data.MoviesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by chromuim on 16.05.17.
 */
public class MoviesPresenterTest {

  private static final List<Movie> MOVIES = Lists.newArrayList(new Movie(1, "firs one "), new Movie(2, "lala"), new Movie(3, "hehe"));
  @Mock
  private MoviesRepository mMoviesRepository;

  @Mock
  private MoviesContract.View mMoviesView;

  @Mock
  private MoviesPresenter mMoviesPresenter;

  @Captor
  private ArgumentCaptor<LoadMoviesCallback> mLoadMoviesCallbackArgumentCaptor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mMoviesPresenter = new MoviesPresenter(mMoviesRepository, mMoviesView, moviesLoader, loaderManager);
  }

  @Test
  public void createPresenterAndSetItToView() {
    // when presenter is created
    mMoviesPresenter = new MoviesPresenter(mMoviesRepository, mMoviesView, moviesLoader, loaderManager);

    //then this presenter will be set to the View
    verify(mMoviesView).setPresenter(mMoviesPresenter);
  }

  @Test
  public void loadAllMoviesFromRepo() {
    // when load movies is requested
    //force load;
    mMoviesPresenter.loadMovies(true);

    // then show loading indicator first
//    verify(mMoviesView).setLoadingIndicator(true);

    // and make sure the data are fetched from the repo/ server.
    verify(mMoviesRepository).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());

    mLoadMoviesCallbackArgumentCaptor.getValue().onMoviesLoaded(MOVIES);

    // make sure the order is correct on the movies view
    InOrder inOrder = inOrder(mMoviesView);
    inOrder.verify(mMoviesView).setLoadingIndicator(true);
    inOrder.verify(mMoviesView).setLoadingIndicator(false);
    inOrder.verify(mMoviesView).showMovies(eq(MOVIES));

    ArgumentCaptor<List> showMoviesArgument = ArgumentCaptor.forClass(List.class);
    verify(mMoviesView).showMovies(showMoviesArgument.capture());
    assertTrue(showMoviesArgument.getValue().size() == MOVIES.size());

  }

  @Test
  public void showErrorWhenNoMoviesAreLoaded() {
    // when data are forced to be loaded
    mMoviesPresenter.loadMovies(true);

    //and the repo couldn't load any data for some reason.
    verify(mMoviesRepository).getMovies(anyInt(), mLoadMoviesCallbackArgumentCaptor.capture());
    mLoadMoviesCallbackArgumentCaptor.getValue().onDataNotAvailable();

    // then make sure the error message / view is shown
    verify(mMoviesView).showError();
  }

  @Test
  public void openMovieDetails() {
    //given a movie
    Movie movie = new Movie(123456, "test movie");

    //when movie detail is requested
    mMoviesPresenter.openMovieDetails(movie);

    //the viewer should show the details
    verify(mMoviesView).showMovieDetailsUi(movie);
  }


}