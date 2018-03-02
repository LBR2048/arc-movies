package ardjomand.leonardo.arcmovies.data;

import ardjomand.leonardo.arcmovies.model.Movie;
import ardjomand.leonardo.arcmovies.model.UpcomingMovies;

/**
 * Created by unity on 25/02/18.
 */

public interface MoviesRepository {

    interface LoadUpcomingMoviesCallback {

        void onSuccess(UpcomingMovies upcomingMovies);

        void onFailure();
    }

    void loadUpcomingMovies(LoadUpcomingMoviesCallback loadUpcomingMoviesCallback, int page);

    interface LoadMovieDetailsCallback {

        void onSuccess(Movie movieDetails);

        void onFailure();
    }

    void loadMovieDetails(LoadMovieDetailsCallback loadMovieDetailsCallback, int movieId);
}
