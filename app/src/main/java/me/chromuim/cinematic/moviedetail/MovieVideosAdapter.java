package me.chromuim.cinematic.moviedetail;

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

  @NonNull
  private OnVideoClickListener mClickListener;

  public MovieVideosAdapter(@NonNull Context context, @NonNull List<MovieVideo> movieVideos, @NonNull OnVideoClickListener clickListener) {
    mMovieVideos = checkNotNull(movieVideos);
    mContext = checkNotNull(context);
    mClickListener = checkNotNull(clickListener);
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

  class MovieVideosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.movie_detail_video_thumbnail)
    ImageView mVideoThumbnail;

    public MovieVideosHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      mVideoThumbnail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      int index = getAdapterPosition();
      MovieVideo movieVideo = mMovieVideos.get(index);
      if (movieVideo.isYoutube()) {
        String url = String.format(Constants.YOUTUBE_WATCH_URL, movieVideo.getKey());
        mClickListener.onVideoClicked(url);
      }
    }
  }

  interface OnVideoClickListener {

    void onVideoClicked(String url);
  }

}
