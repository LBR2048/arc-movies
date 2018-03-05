package ardjomand.leonardo.arcmovies.moviedetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieDetailsFragment.OnFragmentInteractionListener {

    //region Constants
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "movie-details-fragment-tag";
    public static final String EXTRA_MOVIE = "extra-movie";
    public static final String EXTRA_MOVIE_ID = "extra-movie-id";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Complete UpcomingMovie object available
        int movieIdExtra = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        if (movieIdExtra != -1) {
            showMovieDetailsFragment(movieIdExtra);
            return;
        }

        // Only movieId available
        UpcomingMovie upcomingMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (upcomingMovie != null) {
            showMovieDetailsFragment(upcomingMovie);
        }
    }

    // This method will be used to open a movie details page when only the id is available
    // For example:
    // 1) going from one movie details page to another movie from the same director
    // 2) moving between movies by swiping left or right
    private void showMovieDetailsFragment(int movieId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG) == null) {
            MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movieId);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_content_scrolling, movieDetailsFragment, MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void showMovieDetailsFragment(UpcomingMovie movie) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG) == null) {
            MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_content_scrolling, movieDetailsFragment, MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
