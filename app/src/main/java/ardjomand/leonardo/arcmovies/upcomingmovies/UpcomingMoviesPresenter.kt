package ardjomand.leonardo.arcmovies.upcomingmovies

import android.util.Log

import java.util.ArrayList

import ardjomand.leonardo.arcmovies.data.MoviesRepository
import ardjomand.leonardo.arcmovies.model.Genre
import ardjomand.leonardo.arcmovies.model.UpcomingMovie
import ardjomand.leonardo.arcmovies.model.UpcomingMovies

/**
 * Created by unity on 25/02/18.
 */

internal class UpcomingMoviesPresenter(
        private val mView: UpcomingMoviesContract.View,
        private val mRepository: MoviesRepository) : UpcomingMoviesContract.Presenter {

    //region Member Variables
    private var mCurrentPage = 0
    private var isLoading = false
    private var mIsFinished: Boolean = false
    private var mGenres: List<Genre>? = null
    //endregion

    init {
        mView.setPresenter(this)
    }

    override fun reloadUpcomingMovies() {
        if (!isLoading) {
            mCurrentPage = 1
            loadUpcomingMovies(mCurrentPage)
        }
    }

    override fun loadMoreUpcomingMovies() {
        if (!isLoading && !mIsFinished) {
            mCurrentPage++
            loadUpcomingMovies(mCurrentPage)
        }

        if (mIsFinished) {
            Log.i(LOG_TAG, "All upcoming movies loaded")
        }
    }

    //region Helper Methods
    private fun loadUpcomingMovies(currentPage: Int) {
        Log.i("scroll", "Loading page " + mCurrentPage.toString())
        isLoading = true
        mView.setLoading(true)
        mRepository.loadUpcomingMovies(object : MoviesRepository.LoadUpcomingMoviesCallback {

            override fun onSuccess(upcomingMovies: UpcomingMovies) {
                Log.i("scroll", "Page " + mCurrentPage.toString() + " loaded")

                val movies = upcomingMovies.results
                if (movies!!.isEmpty()) {
                    mIsFinished = true
                } else {
                    addGenreNamesToMoviesAsync(movies)
                }

                isLoading = false
                mView.setLoading(false)
            }

            override fun onFailure(message: String) {
                Log.i("scroll", "Failure while loading page " + mCurrentPage.toString())
                isLoading = false
                mCurrentPage--
                mView.setLoading(false)
                mView.showErrorMessage(message)
            }
        }, currentPage)
    }

    private fun addGenreNamesToMoviesAsync(upcomingMovies: List<UpcomingMovie>?) {
        if (mGenres == null) {
            mRepository.loadGenres(object : MoviesRepository.LoadGenresCallBack {
                override fun onSuccess(genres: List<Genre>) {
                    mGenres = genres
                    addGenreNamesToMovies(upcomingMovies!!)
                }

                override fun onFailure(message: String) {

                }
            })
        } else {
            addGenreNamesToMovies(upcomingMovies!!)
        }
    }

    private fun addGenreNamesToMovies(upcomingMovies: List<UpcomingMovie>) {
        for (upcomingMovie in upcomingMovies) {
            val genreIds = upcomingMovie.genreIds
            val genreNames = getGenreNamesByIds(genreIds!!)
            upcomingMovie.genreNames = genreNames
        }
        mView.showUpcomingMovies(upcomingMovies)
    }

    // This method may be improved if Retrofit returns
    // HashMap<int genreId, String genreName> instead of List<Integer> genreIds
    private fun getGenreNamesByIds(genreIds: List<Int>): List<String> {
        val genreNames = ArrayList<String>()
        for (genreId in genreIds) {
            for ((id, name) in mGenres!!) {
                if (genreId == id && name != null) {
                    genreNames.add(name)
                }
            }
        }
        return genreNames
    }

    companion object {

        private val LOG_TAG = UpcomingMoviesPresenter::class.java.simpleName
    }
    //endregion
}
