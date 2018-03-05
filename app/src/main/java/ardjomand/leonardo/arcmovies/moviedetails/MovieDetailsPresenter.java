package ardjomand.leonardo.arcmovies.moviedetails;

import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.MovieDetails;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

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
            public void onSuccess(MovieDetails movieDetails) {
                mView.setLoading(false);
//                mView.showMovieDetails(movieDetails.getTitle(), movieDetails.getBackdropPath(),
//                        movieDetails.getOverview(), movieDetails.getGenres(),
//                        movieDetails.getReleaseDate());
            }

            @Override
            public void onFailure() {
                mView.setLoading(false);
                mView.showErrorMessage();
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
