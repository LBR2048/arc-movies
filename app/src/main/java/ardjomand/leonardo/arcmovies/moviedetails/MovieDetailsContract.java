package ardjomand.leonardo.arcmovies.moviedetails;

import ardjomand.leonardo.arcmovies.BaseContract;
import ardjomand.leonardo.arcmovies.model.MovieDetails;

/**
 * Created by unity on 25/02/18.
 */

public interface MovieDetailsContract {

    interface Presenter {
        void loadMovieDetails(int movieId);
    }

    interface View extends BaseContract.View<Presenter> {
        void showMovieDetails(MovieDetails movieDetails);
    }
}
