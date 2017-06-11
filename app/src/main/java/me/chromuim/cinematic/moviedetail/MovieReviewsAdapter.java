package me.chromuim.cinematic.moviedetail;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import me.chromuim.cinematic.R;
import me.chromuim.cinematic.core.api.MovieReview;
import me.chromuim.cinematic.moviedetail.MovieReviewsAdapter.MovieReviewHolder;

/**
 * Created by chromuim on 11.06.17.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewHolder> {

  @NonNull
  private final Context mContext;

  @NonNull
  private List<MovieReview> mMovieReviewList;

  @NonNull
  private final OnReviewClickListener mOnReviewClickListener;

  public MovieReviewsAdapter(@NonNull Context context, @NonNull List<MovieReview> reviews, @NonNull OnReviewClickListener onReviewClickListener) {
    mContext = checkNotNull(context);
    mMovieReviewList = checkNotNull(reviews);
    mOnReviewClickListener = checkNotNull(onReviewClickListener);
  }

  public void swap(List<MovieReview> movieReviews) {
    mMovieReviewList = movieReviews;
    notifyDataSetChanged();
  }

  @Override
  public MovieReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.movie_review_list_item, parent, false);
    return new MovieReviewHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieReviewHolder holder, int position) {
    MovieReview movieReview = mMovieReviewList.get(position);

    holder.mContentTextView.setText(movieReview.getContent());
    holder.mAuthorTextView.setText(movieReview.getAuthor());
  }

  @Override
  public int getItemCount() {
    if (mMovieReviewList.isEmpty()) {
      return 0;
    }
    return mMovieReviewList.size();
  }

  class MovieReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.movie_review_content_text_view)
    TextView mContentTextView;

    @BindView(R.id.movie_review_author_text_view)
    TextView mAuthorTextView;

    public MovieReviewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      int index = getAdapterPosition();
      MovieReview movieReview = mMovieReviewList.get(index);
      String url = movieReview.getUrl();
      mOnReviewClickListener.onReviewClick(url);
    }
  }

  interface OnReviewClickListener {

    void onReviewClick(String url);
  }

}
