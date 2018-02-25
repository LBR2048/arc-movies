package ardjomand.leonardo.arcmovies.data.remote;

import java.util.List;

import ardjomand.leonardo.arcmovies.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by unity on 25/02/18.
 */

public interface TmdbEndpointInterface {
    @GET("/movie/upcoming")
    Call<List<Movie>> getUpcomingMovies();

    @GET("/movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int movieId);
}
