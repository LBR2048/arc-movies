package ardjomand.leonardo.arcmovies.data

import ardjomand.leonardo.arcmovies.data.remote.MoviesRemoteRepository

/**
 * Created by unity on 25/02/18.
 */

class MoviesRepositoryImpl : MoviesRepository {

    private val mRemoteRepository = MoviesRemoteRepository()

    override fun loadUpcomingMovies(loadUpcomingMoviesCallback: MoviesRepository.LoadUpcomingMoviesCallback, page: Int) {
        mRemoteRepository.loadUpcomingMovies(loadUpcomingMoviesCallback, page)
    }

    override fun loadMovieDetails(loadMovieDetailsCallback: MoviesRepository.LoadMovieDetailsCallback, movieId: Int) {
        mRemoteRepository.loadMovieDetails(loadMovieDetailsCallback, movieId)
    }

    override fun loadGenres(loadGenresCallBack: MoviesRepository.LoadGenresCallBack) {
        mRemoteRepository.loadGenres(loadGenresCallBack)
    }
}
