package ardjomand.leonardo.arcmovies.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.data.MoviesRepositoryImpl;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnUpcomingMoviesFragmentListener}
 * interface.
 */
public class UpcomingMoviesFragment extends Fragment implements UpcomingMoviesContract.View {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final double PAGINATION_THRESHOLD = 5;

    private int mColumnCount = 2;
    private OnUpcomingMoviesFragmentListener mListener;
    private UpcomingMoviesContract.Presenter mPresenter;
    private UpcomingMoviesAdapter mMoviesAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UpcomingMoviesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UpcomingMoviesFragment newInstance(int columnCount) {
        UpcomingMoviesFragment fragment = new UpcomingMoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        new UpcomingMoviesPresenter(this, new MoviesRepositoryImpl());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_movies_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            mMoviesAdapter = new UpcomingMoviesAdapter(getContext(), new ArrayList<UpcomingMovie>(), mListener);
            recyclerView.setAdapter(mMoviesAdapter);

            if (mColumnCount <= 1) {
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);

                // TODO extract this code
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int visibleItemsCount = linearLayoutManager.getChildCount();
                        int totalItemsCount = linearLayoutManager.getItemCount();
                        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                        if (totalItemsCount - (visibleItemsCount + firstVisibleItemPosition) < PAGINATION_THRESHOLD) {
                            mPresenter.loadMoreUpcomingMovies();
                        }
                    }
                });

            } else {
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, mColumnCount);
                recyclerView.setLayoutManager(gridLayoutManager);

                // TODO extract this code
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int visibleItemsCount = gridLayoutManager.getChildCount();
                        int totalItemsCount = gridLayoutManager.getItemCount();
                        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                        if (totalItemsCount - (visibleItemsCount + firstVisibleItemPosition) < PAGINATION_THRESHOLD) {
                            mPresenter.loadMoreUpcomingMovies();
                        }
                    }
                });
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.reloadUpcomingMovies();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpcomingMoviesFragmentListener) {
            mListener = (OnUpcomingMoviesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUpcomingMoviesFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(UpcomingMoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoading(boolean visibility) {
        // TODO add progress bar loading
        if (visibility) Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage() {
        // TODO add error message
    }

    @Override
    public void showUpcomingMovies(List<UpcomingMovie> upcomingMovies) {
        mMoviesAdapter.replaceMovies(upcomingMovies);
    }

    @Override
    public void openMovie(int movieId) {
        // TODO open movie details
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUpcomingMoviesFragmentListener {
        void onMovieClicked(UpcomingMovie upcomingMovie);
    }
}
