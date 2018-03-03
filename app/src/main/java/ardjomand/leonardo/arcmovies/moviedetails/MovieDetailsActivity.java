package ardjomand.leonardo.arcmovies.moviedetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ardjomand.leonardo.arcmovies.R;

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieDetailsFragment.OnFragmentInteractionListener {

    public static final String MOVIE_DETAILS_FRAGMENT_TAG = "movie-details-fragment-tag";
    public static final String EXTRA_MOVIE_ID = "extra-movie-id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int intExtra = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);

        showMovieDetailsFragment(intExtra);
    }

    private void showMovieDetailsFragment(int movieId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(MOVIE_DETAILS_FRAGMENT_TAG) == null) {
            MovieDetailsFragment recipesFragment = MovieDetailsFragment.newInstance(movieId);
            fragmentManager.beginTransaction()
                    .replace(R.id.movie_details_frame, recipesFragment, MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
