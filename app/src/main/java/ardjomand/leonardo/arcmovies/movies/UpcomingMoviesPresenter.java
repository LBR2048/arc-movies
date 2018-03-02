package ardjomand.leonardo.arcmovies.movies;

import android.util.Log;

import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.UpcomingMovies;

/**
 * Created by unity on 25/02/18.
 */

class UpcomingMoviesPresenter implements UpcomingMoviesContract.Presenter {

    private static String LOG_TAG = UpcomingMoviesPresenter.class.getSimpleName();;

    private final UpcomingMoviesContract.View mView;
    private MoviesRepository mRepository;
    private int mCurrentPage = 0;
    private boolean isLoading = false;

    public UpcomingMoviesPresenter(UpcomingMoviesContract.View view, MoviesRepository moviesRepository) {
        mView = view;
        mView.setPresenter(this);
        mRepository = moviesRepository;
    }

    @Override
    public void reloadUpcomingMovies() {
        if (!isLoading) {
            mCurrentPage = 1;
            loadUpcomingMovies(mCurrentPage);
        }
    }

    @Override
    public void loadMoreUpcomingMovies() {
        if (!isLoading) {
            mCurrentPage++;
            loadUpcomingMovies(mCurrentPage);
        }
    }

    private void loadUpcomingMovies(int currentPage) {
        Log.i(LOG_TAG, "Loading page " + String.valueOf(mCurrentPage));
        isLoading = true;
        mView.setLoading(true);
        mRepository.loadUpcomingMovies(new MoviesRepository.LoadUpcomingMoviesCallback() {
            @Override
            public void onSuccess(UpcomingMovies upcomingMovies) {
                Log.i(LOG_TAG, "Page " + String.valueOf(mCurrentPage) + " loaded");
                isLoading = false;
                mView.setLoading(false);
                mView.showUpcomingMovies(upcomingMovies.getResults());
            }

            @Override
            public void onFailure() {
                Log.i(LOG_TAG, "Failure while loading page " + String.valueOf(mCurrentPage));
                isLoading = false;
                mView.setLoading(false);
                mView.showErrorMessage();
            }
        }, currentPage);
    }

    @Override
    public void onMovieClicked(int movieId) {
        mView.openMovie(movieId);
    }
}
