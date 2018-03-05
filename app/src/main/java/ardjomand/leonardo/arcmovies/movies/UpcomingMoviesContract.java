package ardjomand.leonardo.arcmovies.movies;

import java.util.List;

import ardjomand.leonardo.arcmovies.BaseContract;
import ardjomand.leonardo.arcmovies.model.UpcomingMovie;

/**
 * Created by unity on 25/02/18.
 */

public interface UpcomingMoviesContract {

    interface Presenter {

        void reloadUpcomingMovies();

        void loadMoreUpcomingMovies();
    }

    interface View extends BaseContract.View<Presenter> {

        void showUpcomingMovies(List<UpcomingMovie> upcomingMovies);
    }
}
