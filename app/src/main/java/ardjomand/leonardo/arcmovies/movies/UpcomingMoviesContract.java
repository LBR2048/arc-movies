package ardjomand.leonardo.arcmovies.movies;

import java.util.List;

import ardjomand.leonardo.arcmovies.BaseContract;
import ardjomand.leonardo.arcmovies.model.Movie;

/**
 * Created by unity on 25/02/18.
 */

public interface UpcomingMoviesContract {

    interface Presenter {

        void loadUpcomingMovies();

        void onMovieClicked(int movieId);
    }

    interface View extends BaseContract.View<Presenter> {

        void showUpcomingMovies(List<Movie> movies);

        void openMovie(int movieId);
    }
}
