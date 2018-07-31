package ardjomand.leonardo.arcmovies.data.remote;

import ardjomand.leonardo.arcmovies.model.Genres;
import ardjomand.leonardo.arcmovies.model.MovieDetails;
import ardjomand.leonardo.arcmovies.model.UpcomingMovies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by unity on 25/02/18.
 */

public interface TmdbEndpointInterface {
    @GET("movie/upcoming")
    Call<UpcomingMovies> getUpcomingMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int movieId);

    @GET("genre/movie/list")
    Call<Genres> getGenres();
}
