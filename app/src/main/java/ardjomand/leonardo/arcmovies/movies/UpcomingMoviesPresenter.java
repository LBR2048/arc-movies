package ardjomand.leonardo.arcmovies.movies;

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

    private static String LOG_TAG = UpcomingMoviesPresenter.class.getSimpleName();

    private final UpcomingMoviesContract.View mView;
    private MoviesRepository mRepository;
    private int mCurrentPage = 0;
    private boolean isLoading = false;
    private boolean mIsFinished;
    private List<Genre> mGenres;

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
        if (!isLoading && !mIsFinished) {
            mCurrentPage++;
            loadUpcomingMovies(mCurrentPage);
        }

        if (mIsFinished) {
            Log.i(LOG_TAG, "All upcoming movies loaded");
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

    @Override
    public void onMovieClicked(int movieId) {
        mView.openMovie(movieId);
    }
}
