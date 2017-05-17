package me.chromuim.cinematic.movies;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.List;
import me.chromuim.cinematic.R;
import me.chromuim.cinematic.core.api.Constants;
import me.chromuim.cinematic.data.Movie;
import me.chromuim.cinematic.movies.MoviesAdapter.MovieHolder;

/**
 * Created by chromuim on 16.05.17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

  @NonNull
  private List<Movie> mMovieList;

  @NonNull
  private Context mContext;

  @NonNull
  private final onMovieItemClickListener mClickListener;

  public MoviesAdapter(@NonNull List<Movie> movies, @NonNull onMovieItemClickListener listener) {
    mMovieList = checkNotNull(movies);
    mClickListener = checkNotNull(listener);
  }

  public void swapData(List<Movie> movies) {
    mMovieList = movies;
    notifyDataSetChanged();
  }

  @Override
  public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.movies_grid_item, parent, false);
    return new MovieHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieHolder holder, int position) {
    String moviePath = getItem(position).getPosterPath();
    String fullPath = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE + moviePath;
    Picasso.with(mContext).load(fullPath).into(holder.mImageView);
  }

  public Movie getItem(int position) {
    return mMovieList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return mMovieList.size();
  }

  class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.movie_grid_item)
    ImageView mImageView;


    public MovieHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
      int index = getAdapterPosition();
      mClickListener.onItemClick(index);
    }
  }


  public interface onMovieItemClickListener {

    void onItemClick(int movieIndex);
  }

}
