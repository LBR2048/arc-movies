package ardjomand.leonardo.arcmovies.moviedetails;

import ardjomand.leonardo.arcmovies.BaseContract;
import ardjomand.leonardo.arcmovies.model.MovieDetails;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

/**
 * Created by unity on 25/02/18.
 */

public interface MovieDetailsContract {

    interface Presenter {

        void loadMovieDetails(int movieId);

        void getMovieDetails(UpcomingMovie upcomingMovie);
    }

    interface View extends BaseContract.View<Presenter> {

        void showMovieDetails(String title, String backdropPath, String overview,
                              String text, String releaseDate);
    }
}
