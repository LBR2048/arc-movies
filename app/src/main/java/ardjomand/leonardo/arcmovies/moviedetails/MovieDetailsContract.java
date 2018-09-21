package ardjomand.leonardo.arcmovies.moviedetails;

import java.util.List;

import ardjomand.leonardo.arcmovies.BaseContract;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

/**
 * Created by unity on 25/02/18.
 */

interface MovieDetailsContract {

    interface Presenter {

        void getMovieDetails(int movieId);

        void getMovieDetails(UpcomingMovie upcomingMovie);
    }

    interface View extends BaseContract.View<Presenter> {

        void showMovieDetails(String title, String backdropPath, String overview,
                              List<String> genreNames, String releaseDate);
    }
}
