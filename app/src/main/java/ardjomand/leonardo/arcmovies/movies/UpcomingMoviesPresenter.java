package ardjomand.leonardo.arcmovies.movies;

import java.util.List;

import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.Movie;

/**
 * Created by unity on 25/02/18.
 */

class UpcomingMoviesPresenter implements UpcomingMoviesContract.Presenter {

    private final UpcomingMoviesContract.View mView;
    private MoviesRepository mRepository;

    public UpcomingMoviesPresenter(UpcomingMoviesContract.View view, MoviesRepository moviesRepository) {
        mView = view;
        mView.setPresenter(this);
        mRepository = moviesRepository;
    }

    @Override
    public void loadUpcomingMovies() {
        mView.setLoading(true);
        mRepository.loadUpcomingMovies(new MoviesRepository.LoadUpcomingMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mView.setLoading(false);
                mView.showUpcomingMovies(movies);
            }

            @Override
            public void onFailure() {
                mView.setLoading(false);
                mView.showErrorMessage();
            }
        });
    }

    @Override
    public void onMovieClicked(int movieId) {
        mView.openMovie(movieId);
    }
}
