package ardjomand.leonardo.arcmovies.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.model.Movie;
import ardjomand.leonardo.arcmovies.moviedetails.MovieDetailsActivity;
import ardjomand.leonardo.arcmovies.moviedetails.MovieDetailsFragment;

public class MainActivity extends AppCompatActivity implements
        UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener,
        MovieDetailsFragment.OnFragmentInteractionListener {

    public static final String UPCOMING_MOVIES_FRAGMENT_TAG = "upcoming-movies-fragment-tag";
    public static final String MOVIE_DETAILS_FRAGMENT_TAG = "movie-details-fragment-tag";

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

    private void showMovieDetailsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG) == null) {
            MovieDetailsFragment recipesFragment = MovieDetailsFragment.newInstance(284054);
            fragmentManager.beginTransaction()
                    .replace(R.id.upcoming_movies_frame, recipesFragment, MOVIE_DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
//        showMovieDetailsFragment();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
