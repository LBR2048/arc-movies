package ardjomand.leonardo.arcmovies.upcomingmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;
import ardjomand.leonardo.arcmovies.moviedetails.MovieDetailsActivity;
import ardjomand.leonardo.arcmovies.moviedetails.MovieDetailsFragment;

public class MainActivity extends AppCompatActivity implements
        UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener,
        MovieDetailsFragment.OnFragmentInteractionListener {

    private static final String UPCOMING_MOVIES_FRAGMENT_TAG = "upcoming-movies-fragment-tag";
    private static final String MOVIE_DETAILS_FRAGMENT_TAG = "movie-details-fragment-tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showUpcomingMoviesFragment();
    }

    private void showUpcomingMoviesFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(UPCOMING_MOVIES_FRAGMENT_TAG) == null) {
            UpcomingMoviesFragment recipesFragment = UpcomingMoviesFragment.newInstance(2);
            fragmentManager.beginTransaction()
                    .replace(R.id.upcoming_movies_frame, recipesFragment, UPCOMING_MOVIES_FRAGMENT_TAG)
                    .commit();
        }
    }

    // This method will be used for tablet optimized layout
    // When a movie is clicked on the list, a separate fragment will show the details
    // on the right pane of the same Activity
    private void showMovieDetailsFragment(int movieId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG) == null) {
            MovieDetailsFragment recipesFragment = MovieDetailsFragment.newInstance(movieId);
            fragmentManager.beginTransaction()
                    .replace(R.id.upcoming_movies_frame, recipesFragment, MOVIE_DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onMovieClicked(UpcomingMovie upcomingMovie) {
        showMovieDetailsActivity(upcomingMovie);
    }

    // This method will be used to open a movie details page when only the id is available
    // For example:
    // 1) going from one movie details page to another movie from the same director
    // 2) moving between movies by swiping left or right
    private void showMovieDetailsActivity(int movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    private void showMovieDetailsActivity(UpcomingMovie upcomingMovie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MovieDetailsActivity.EXTRA_MOVIE, upcomingMovie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
