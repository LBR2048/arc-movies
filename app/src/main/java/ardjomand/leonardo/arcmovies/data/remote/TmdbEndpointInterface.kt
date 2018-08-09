package ardjomand.leonardo.arcmovies.data.remote

import ardjomand.leonardo.arcmovies.model.Genres
import ardjomand.leonardo.arcmovies.model.MovieDetails
import ardjomand.leonardo.arcmovies.model.UpcomingMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by unity on 25/02/18.
 */

interface TmdbEndpointInterface {

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int): Call<UpcomingMovies>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Call<MovieDetails>

    @get:GET("genre/movie/list")
    val genres: Call<Genres>
}
