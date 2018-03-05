package ardjomand.leonardo.arcmovies.moviedetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.Utils;
import ardjomand.leonardo.arcmovies.data.MoviesRepositoryImpl;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    //region Constants
    private static final String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w780";
    private static final String ARG_INPUT_TYPE = "arg-input-type";
    private static final String INPUT_TYPE_MOVIE_ID = "arg-input-type-movie-id";
    private static final String INPUT_TYPE_MOVIE = "arg-input-type-movie";
    private static final String ARG_MOVIE = "arg-movie";
    private static final String ARG_MOVIE_ID = "arg-movie-id";
    //endregion

    //region Member Variables
    private OnFragmentInteractionListener mListener;
    private TextView mOverviewTextView;
    private MovieDetailsContract.Presenter mPresenter;
    private ImageView mPosterImageView;
    private TextView mGenreTextView;
    private TextView mReleaseDate;
    //endregion

    //region Constructors
    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(int movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INPUT_TYPE, INPUT_TYPE_MOVIE_ID);
        args.putInt(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieDetailsFragment newInstance(UpcomingMovie upcomingMovie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INPUT_TYPE, INPUT_TYPE_MOVIE);
        args.putParcelable(ARG_MOVIE, upcomingMovie);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MovieDetailsPresenter(this, new MoviesRepositoryImpl());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPosterImageView = view.findViewById(R.id.movie_details_poster);
        mOverviewTextView = view.findViewById(R.id.movie_details_overview);
        mGenreTextView = view.findViewById(R.id.movie_details_genre);
        mReleaseDate = view.findViewById(R.id.movie_details_release_date);
        loadMovieDetails();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion

    //region Base Callbacks
    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoading(boolean visibility) {
        // TODO show loading
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    //endregion

    @Override
    public void showMovieDetails(String title, String backdropPath, String overview,
                                 List<String> genreNames, String releaseDate) {
        setTitle(title);
        Picasso.with(getContext())
                .load(BASE_BACKDROP_URL + backdropPath)
                .into(mPosterImageView);
        mOverviewTextView.setText(overview);
        mGenreTextView.setText(Utils.showListAsString(genreNames, ", "));
        mReleaseDate.setText(getString(R.string.release_date, releaseDate));
    }

    private void loadMovieDetails() {
        if (getArguments() != null) {
            switch (getArguments().getString(ARG_INPUT_TYPE, null)) {
                case INPUT_TYPE_MOVIE:
                    Log.i("input", "movie");
                    UpcomingMovie upcomingMovie = getArguments().getParcelable(ARG_MOVIE);
                    mPresenter.getMovieDetails(upcomingMovie);
                    break;
                case INPUT_TYPE_MOVIE_ID:
                    Log.i("input", "movieId");
                    int movieId = getArguments().getInt(ARG_MOVIE_ID);
                    mPresenter.loadMovieDetails(movieId);
                    break;
                default:
                    Log.i("input", "unknown");
                    break;
            }
        }
    }

    private void setTitle(String title) {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(title);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
