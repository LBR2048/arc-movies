package ardjomand.leonardo.arcmovies.data

import ardjomand.leonardo.arcmovies.model.Genre
import ardjomand.leonardo.arcmovies.model.MovieDetails
import ardjomand.leonardo.arcmovies.model.UpcomingMovies

/**
 * Created by unity on 25/02/18.
 */

interface MoviesRepository {

    interface LoadUpcomingMoviesCallback {

        fun onSuccess(upcomingMovies: UpcomingMovies)

        fun onFailure(message: String)
    }

    fun loadUpcomingMovies(loadUpcomingMoviesCallback: LoadUpcomingMoviesCallback, page: Int)


    interface LoadMovieDetailsCallback {

        fun onSuccess(movieDetails: MovieDetails)

        fun onFailure(message: String)
    }

    fun loadMovieDetails(loadMovieDetailsCallback: LoadMovieDetailsCallback, movieId: Int)


    interface LoadGenresCallBack {

        fun onSuccess(genres: List<Genre>)

        fun onFailure(message: String)
    }

    fun loadGenres(loadGenresCallBack: LoadGenresCallBack)
}
