package ardjomand.leonardo.arcmovies.upcomingmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ardjomand.leonardo.arcmovies.Constants;
import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.Utils;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;
import ardjomand.leonardo.arcmovies.upcomingmovies.UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UpcomingMovie} and makes a call to the
 * specified {@link UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener}.
 */
public class UpcomingMoviesAdapter extends RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder> {

    private final Context mContext;
    private final List<UpcomingMovie> mUpcomingMovies;
    private final OnUpcomingMoviesFragmentListener mListener;

    public UpcomingMoviesAdapter(Context context, List<UpcomingMovie> items,
            OnUpcomingMoviesFragmentListener listener) {
        mContext = context;
        mUpcomingMovies = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_upcoming_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mUpcomingMovies.get(position);
        holder.mTitleView.setText(mUpcomingMovies.get(position).getTitle());
        Picasso.with(mContext)
                .load(Constants.BASE_POSTER_URL + mUpcomingMovies.get(position).getPosterPath())
                .resize(400, 800)
                .centerInside()
                .into(holder.mPosterView);
        holder.mGenreView.setText(
                Utils.showListAsString(mUpcomingMovies.get(position).getGenreNames(), ", "));
        holder.mReleaseDateView.setText(mContext.getString(R.string.release_date,
                mUpcomingMovies.get(position).getReleaseDate()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMovieClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUpcomingMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final ImageView mPosterView;
        public final TextView mGenreView;
        public final TextView mReleaseDateView;
        public UpcomingMovie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.movie_title);
            mPosterView = view.findViewById(R.id.movie_poster);
            mGenreView = view.findViewById(R.id.movie_genre);
            mReleaseDateView = view.findViewById(R.id.movie_release_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }

    public void replaceMovies(List<UpcomingMovie> upcomingMovies) {
        mUpcomingMovies.addAll(upcomingMovies);
        notifyDataSetChanged();
    }
}
