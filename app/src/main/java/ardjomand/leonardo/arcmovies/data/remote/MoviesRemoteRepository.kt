package ardjomand.leonardo.arcmovies.data.remote

import android.util.Log

import ardjomand.leonardo.arcmovies.Constants
import ardjomand.leonardo.arcmovies.data.MoviesRepository
import ardjomand.leonardo.arcmovies.model.Genres
import ardjomand.leonardo.arcmovies.model.MovieDetails
import ardjomand.leonardo.arcmovies.model.UpcomingMovies
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by unity on 25/02/18.
 */

class MoviesRemoteRepository : MoviesRepository {

    private val mTmdbEndpointInterface: TmdbEndpointInterface = getTmdbEndpointInterface()

    override fun loadUpcomingMovies(loadUpcomingMoviesCallback: MoviesRepository.LoadUpcomingMoviesCallback, page: Int) {
        val call = mTmdbEndpointInterface.getUpcomingMovies(page)
        call.enqueue(object : Callback<UpcomingMovies> {
            override fun onResponse(call: Call<UpcomingMovies>, response: Response<UpcomingMovies>) {
                Log.d(LOG_TAG, response.toString())
                if (response.isSuccessful) {
                    loadUpcomingMoviesCallback.onSuccess(response.body()!!)
                } else {
                    loadUpcomingMoviesCallback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UpcomingMovies>, t: Throwable) {
                loadUpcomingMoviesCallback.onFailure(t.message ?: "Error")
                t.printStackTrace()
            }
        })
    }

    override fun loadMovieDetails(loadMovieDetailsCallback: MoviesRepository.LoadMovieDetailsCallback, movieId: Int) {
        val call = mTmdbEndpointInterface.getMovieDetails(movieId)
        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                Log.d(LOG_TAG, response.toString())
                if (response.isSuccessful) {
                    loadMovieDetailsCallback.onSuccess(response.body()!!)
                } else {
                    loadMovieDetailsCallback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                loadMovieDetailsCallback.onFailure(t.message ?: "Error")
                t.printStackTrace()
            }
        })
    }

    override fun loadGenres(loadGenresCallBack: MoviesRepository.LoadGenresCallBack) {
        val call = mTmdbEndpointInterface.genres
        call.enqueue(object : Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    loadGenresCallBack.onSuccess(body.genres!!)
                } else {
                    loadGenresCallBack.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                loadGenresCallBack.onFailure(t.message ?: "Error")
                t.printStackTrace()
            }
        })
    }

    // Add the getAuthInterceptor to OkHttpClient
    private fun getTmdbEndpointInterface(): TmdbEndpointInterface {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(getAuthInterceptor())
        val client = builder.build()

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        return retrofit.create(TmdbEndpointInterface::class.java)
    }

    private fun getAuthInterceptor() = Interceptor { chain ->
        var request = chain.request()
        val url = request.url()
                .newBuilder()
                .addQueryParameter(Constants.QUERY_PARAMETER_API_KEY, Constants.API_KEY)
                .build()

        request = request
                .newBuilder()
                .url(url)
                .build()

        chain.proceed(request)
    }

    companion object {
        private val LOG_TAG = MoviesRemoteRepository::class.java.simpleName
    }
}
