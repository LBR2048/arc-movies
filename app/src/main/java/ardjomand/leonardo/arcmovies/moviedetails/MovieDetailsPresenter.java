package ardjomand.leonardo.arcmovies.moviedetails;

import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.Movie;

/**
 * Created by unity on 25/02/18.
 */

class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final MovieDetailsContract.View mView;
    private final MoviesRepository mRepository;

    public MovieDetailsPresenter(MovieDetailsContract.View view, MoviesRepository moviesRepository) {
        mView = view;
        mView.setPresenter(this);
        mRepository = moviesRepository;
    }

    @Override
    public void loadMovieDetails(int movieId) {
        mView.setLoading(true);
        mRepository.loadMovieDetails(new MoviesRepository.LoadMovieDetailsCallback() {
            @Override
            public void onSuccess(Movie movieDetails) {
                mView.setLoading(false);
                mView.showMovieDetails(movieDetails);
            }

            @Override
            public void onFailure() {
                mView.setLoading(false);
                mView.showErrorMessage();
            }
        }, movieId);
    }
}
