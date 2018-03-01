package ardjomand.leonardo.arcmovies.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.model.Movie;
import ardjomand.leonardo.arcmovies.movies.UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Movie} and makes a call to the
 * specified {@link UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UpcomingMoviesAdapter extends RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder> {

    private final List<Movie> mMovies;
    private final OnUpcomingMoviesFragmentListener mListener;

    public UpcomingMoviesAdapter(List<Movie> items, OnUpcomingMoviesFragmentListener listener) {
        mMovies = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_upcoming_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mMovies.get(position);
        holder.mIdView.setText(String.valueOf(mMovies.get(position).getId()));
        holder.mContentView.setText(mMovies.get(position).getTitle());

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
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void replaceMovies(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
