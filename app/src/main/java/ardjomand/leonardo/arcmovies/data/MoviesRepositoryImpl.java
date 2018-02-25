package ardjomand.leonardo.arcmovies.data;

import ardjomand.leonardo.arcmovies.data.remote.MoviesRemoteRepository;

/**
 * Created by unity on 25/02/18.
 */

public class MoviesRepositoryImpl implements MoviesRepository {

    private final MoviesRepository mRemoteRepository = new MoviesRemoteRepository();

    @Override
    public void loadUpcomingMovies(LoadUpcomingMoviesCallback loadUpcomingMoviesCallback) {
        mRemoteRepository.loadUpcomingMovies(loadUpcomingMoviesCallback);
    }

    @Override
    public void loadMovieDetails(LoadMovieDetailsCallback loadMovieDetailsCallback, int movieId) {
        mRemoteRepository.loadMovieDetails(loadMovieDetailsCallback, movieId);
    }
}
