package ardjomand.leonardo.arcmovies.moviedetails;

import android.support.annotation.NonNull;

import ardjomand.leonardo.arcmovies.Utils;
import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.MovieDetails;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

/**
 * Created by unity on 25/02/18.
 */

class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final MovieDetailsContract.View mView;
    private final MoviesRepository mRepository;

    MovieDetailsPresenter(MovieDetailsContract.View view, MoviesRepository moviesRepository) {
        mView = view;
        mView.setPresenter(this);
        mRepository = moviesRepository;
    }

    @Override
    public void getMovieDetails(int movieId) {
        mView.setLoading(true);
        mRepository.loadMovieDetails(new MoviesRepository.LoadMovieDetailsCallback() {
            @Override
            public void onSuccess(@NonNull MovieDetails movieDetails) {
                mView.setLoading(false);
                mView.showMovieDetails(movieDetails.getTitle(), movieDetails.getBackdropPath(),
                        movieDetails.getOverview(), Utils.getGenreNames(movieDetails.getGenres()),
                        movieDetails.getReleaseDate());
            }

            @Override
            public void onFailure(@NonNull String message) {
                mView.setLoading(false);
                mView.showErrorMessage(message);
            }
        }, movieId);
    }

    @Override
    public void getMovieDetails(UpcomingMovie upcomingMovie) {
        mView.showMovieDetails(upcomingMovie.getTitle(), upcomingMovie.getBackdropPath(),
                upcomingMovie.getOverview(), upcomingMovie.getGenreNames(),
                upcomingMovie.getReleaseDate());
    }
}
