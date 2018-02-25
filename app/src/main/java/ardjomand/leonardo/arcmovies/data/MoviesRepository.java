package ardjomand.leonardo.arcmovies.data;

import java.util.List;

import ardjomand.leonardo.arcmovies.model.Movie;

/**
 * Created by unity on 25/02/18.
 */

public interface MoviesRepository {

    interface LoadUpcomingMoviesCallback {

        void onSuccess(List<Movie> movies);

        void onFailure();
    }

    void loadUpcomingMovies(LoadUpcomingMoviesCallback loadUpcomingMoviesCallback);

    interface LoadMovieDetailsCallback {

        void onSuccess(Movie movieDetails);

        void onFailure();
    }

    void loadMovieDetails(LoadMovieDetailsCallback loadMovieDetailsCallback, int movieId);
}
