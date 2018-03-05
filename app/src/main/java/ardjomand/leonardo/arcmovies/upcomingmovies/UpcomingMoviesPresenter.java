package ardjomand.leonardo.arcmovies.upcomingmovies;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.Genre;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;
import ardjomand.leonardo.arcmovies.model.UpcomingMovies;

/**
 * Created by unity on 25/02/18.
 */

class UpcomingMoviesPresenter implements UpcomingMoviesContract.Presenter {

    private static final String LOG_TAG = UpcomingMoviesPresenter.class.getSimpleName();

    //region Member Variables
    private final UpcomingMoviesContract.View mView;
    private final MoviesRepository mRepository;
    private int mCurrentPage = 0;
    private boolean isLoading = false;
    private boolean mIsFinished;
    private List<Genre> mGenres;
    //endregion

    UpcomingMoviesPresenter(UpcomingMoviesContract.View view, MoviesRepository moviesRepository) {
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
        if (!isLoading && !mIsFinished) {
            mCurrentPage++;
            loadUpcomingMovies(mCurrentPage);
        }

        if (mIsFinished) {
            Log.i(LOG_TAG, "All upcoming movies loaded");
        }
    }

    //region Helper Methods
    private void loadUpcomingMovies(int currentPage) {
        Log.i(LOG_TAG, "Loading page " + String.valueOf(mCurrentPage));
        isLoading = true;
        mView.setLoading(true);
        mRepository.loadUpcomingMovies(new MoviesRepository.LoadUpcomingMoviesCallback() {

            @Override
            public void onSuccess(UpcomingMovies upcomingMovies) {
                Log.i(LOG_TAG, "Page " + String.valueOf(mCurrentPage) + " loaded");

                List<UpcomingMovie> movies = upcomingMovies.getResults();
                if (movies.isEmpty()) {
                    mIsFinished = true;
                } else {
                    addGenreNamesToMoviesAsync(movies);
                }

                isLoading = false;
                mView.setLoading(false);
            }

            @Override
            public void onFailure(String message) {
                Log.i(LOG_TAG, "Failure while loading page " + String.valueOf(mCurrentPage));
                isLoading = false;
                mView.setLoading(false);
                mView.showErrorMessage(message);
            }
        }, currentPage);
    }

    private void addGenreNamesToMoviesAsync(final List<UpcomingMovie> upcomingMovies) {
        if (mGenres == null) {
            mRepository.loadGenres(new MoviesRepository.LoadGenresCallBack() {
                @Override
                public void onSuccess(List<Genre> genres) {
                    mGenres = genres;
                    addGenreNamesToMovies(upcomingMovies);
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else {
            addGenreNamesToMovies(upcomingMovies);
        }
    }

    private void addGenreNamesToMovies(List<UpcomingMovie> upcomingMovies) {
        for (UpcomingMovie upcomingMovie : upcomingMovies) {
            List<Integer> genreIds = upcomingMovie.getGenreIds();
            List<String> genreNames = getGenreNamesByIds(genreIds);
            upcomingMovie.setGenreNames(genreNames);
        }
        mView.showUpcomingMovies(upcomingMovies);
    }

    // This method may be improved if Retrofit returns
    // HashMap<int genreId, String genreName> instead of List<Integer> genreIds
    private List<String> getGenreNamesByIds(List<Integer> genreIds) {
        List<String> genreNames = new ArrayList<>();
        for (Integer genreId : genreIds) {
            for (Genre genre : mGenres) {
                if (genreId.equals(genre.getId())) {
                    genreNames.add(genre.getName());
                }
            }
        }
        return genreNames;
    }
    //endregion
}
