package ardjomand.leonardo.arcmovies.upcomingmovies;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    //region Constants
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final double PAGINATION_THRESHOLD = 5;
    private static final String LIST_DATA = "List_Data";
    private static final String LOG_LIST_STATE = "List_state";
    //endregion

    //region Member Variables
    private int mColumnCount = 2;
    private OnUpcomingMoviesFragmentListener mListener;
    private UpcomingMoviesContract.Presenter mPresenter;
    private UpcomingMoviesAdapter mAdapter;
    private List<UpcomingMovie> mUpcomingMovies = new ArrayList<>();
    //endregion

    //region Constructors
    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }

    public static UpcomingMoviesFragment newInstance(int columnCount) {
        UpcomingMoviesFragment fragment = new UpcomingMoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        new UpcomingMoviesPresenter(this, new MoviesRepositoryImpl());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_movies_list, container, false);

        // Setup the RecyclerView
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            mAdapter = new UpcomingMoviesAdapter(getContext(), new ArrayList<UpcomingMovie>(), mListener);
            recyclerView.setAdapter(mAdapter);

            final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, mColumnCount);
            recyclerView.setLayoutManager(gridLayoutManager);

            // Setup pagination
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            mPresenter.reloadUpcomingMovies();
        } else {
            mUpcomingMovies = savedInstanceState.getParcelableArrayList(LIST_DATA);
            Log.d(LOG_LIST_STATE, "Retrieving " + String.valueOf(mUpcomingMovies.size()) + " movies");
            mAdapter.replaceMovies(mUpcomingMovies);
            mAdapter.notifyDataSetChanged();
        }
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(LOG_LIST_STATE, "Saving " + String.valueOf(mUpcomingMovies.size()) + " movies");
        outState.putParcelableArrayList(LIST_DATA, (ArrayList<? extends Parcelable>) mUpcomingMovies);
    }
    //endregion

    //region Base Callbacks
    @Override
    public void setPresenter(UpcomingMoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoading(boolean visibility) {
        // TODO This can be improved by showing a progress bar as the last item of the list instead of a toast
        if (visibility) Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    //endregion

    @Override
    public void showUpcomingMovies(List<UpcomingMovie> upcomingMovies) {
        Log.d(LOG_LIST_STATE, "Received " + String.valueOf(upcomingMovies.size()) + " movies from Presenter");
        mUpcomingMovies.addAll(upcomingMovies);
        mAdapter.addMovies(upcomingMovies);
        mAdapter.notifyDataSetChanged();
        Log.d(LOG_LIST_STATE, "Total: " + String.valueOf(mUpcomingMovies.size()) + " movies");
    }

    public interface OnUpcomingMoviesFragmentListener {
        void onMovieClicked(UpcomingMovie upcomingMovie);
    }
}
