package me.chromuim.cinematic.moviedetail;

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
import me.chromuim.cinematic.core.api.MovieVideo;
import me.chromuim.cinematic.moviedetail.MovieVideosAdapter.MovieVideosHolder;

/**
 * Created by chromuim on 10.06.17.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosHolder> {

  @NonNull
  private final Context mContext;

  @NonNull
  private List<MovieVideo> mMovieVideos;

  public MovieVideosAdapter(@NonNull Context context, @NonNull List<MovieVideo> movieVideos) {
    mMovieVideos = movieVideos;
    mContext = context;
  }

  public void swap(List<MovieVideo> videoList) {
    mMovieVideos = videoList;
    notifyDataSetChanged();
  }


  @Override
  public MovieVideosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.movie_video_list_item, parent, false);
    return new MovieVideosHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieVideosHolder holder, int position) {
    if (mMovieVideos.isEmpty()) {
      return;
    }

    MovieVideo video = mMovieVideos.get(position);
    String thumbnail = String.format(Constants.YOUTUBE_THUMBNAIL, video.getKey());
    Picasso.with(mContext).load(thumbnail).into(holder.mVideoThumbnail);
  }

  @Override
  public int getItemCount() {
    if (mMovieVideos.isEmpty()) {
      return 0;
    }
    return mMovieVideos.size();
  }

  class MovieVideosHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_detail_video_thumbnail)
    ImageView mVideoThumbnail;

    public MovieVideosHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
