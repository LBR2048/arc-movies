package ardjomand.leonardo.arcmovies.movies;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ardjomand.leonardo.arcmovies.R;
import ardjomand.leonardo.arcmovies.model.Movie;

public class MainActivity extends AppCompatActivity
        implements UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener {

    public static final String UPCOMING_MOVIES_FRAGMENT_TAG = "upcoming-movies-fragment-tag";

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

    @Override
    public void onMovieClicked(Movie movie) {

    }
}
