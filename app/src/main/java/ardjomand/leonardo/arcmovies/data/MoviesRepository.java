package ardjomand.leonardo.arcmovies.data;

import java.util.List;

import ardjomand.leonardo.arcmovies.model.Genre;
import ardjomand.leonardo.arcmovies.model.MovieDetails;
import ardjomand.leonardo.arcmovies.model.UpcomingMovies;

/**
 * Created by unity on 25/02/18.
 */

public interface MoviesRepository {

    interface LoadUpcomingMoviesCallback {

        void onSuccess(UpcomingMovies upcomingMovies);

        void onFailure(String message);
    }

    void loadUpcomingMovies(LoadUpcomingMoviesCallback loadUpcomingMoviesCallback, int page);


    interface LoadMovieDetailsCallback {

        void onSuccess(MovieDetails movieDetails);

        void onFailure(String message);
    }

    void loadMovieDetails(LoadMovieDetailsCallback loadMovieDetailsCallback, int movieId);


    interface LoadGenresCallBack {

        void onSuccess(List<Genre> genres);

        void onFailure(String message);
    }

    void loadGenres(LoadGenresCallBack loadGenresCallBack);
}
