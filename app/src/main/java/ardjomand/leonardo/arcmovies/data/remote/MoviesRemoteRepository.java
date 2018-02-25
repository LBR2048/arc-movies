package ardjomand.leonardo.arcmovies.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.Movie;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by unity on 25/02/18.
 */

public class MoviesRemoteRepository implements MoviesRepository{

    private static final String LOG_TAG = MoviesRemoteRepository.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String QUERY_PARAMETER_API_KEY = "api_key";
    private static final String API_KEY = "1f54bd990f1cdfb230adb312546d765d";

    private final TmdbEndpointInterface mTmdbEndpointInterface;

    public MoviesRemoteRepository() {
        mTmdbEndpointInterface = getTmdbEndpointInterface();
    }

    @Override
    public void loadUpcomingMovies(final LoadUpcomingMoviesCallback loadUpcomingMoviesCallback) {
        Call<List<Movie>> call = mTmdbEndpointInterface.getUpcomingMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                loadUpcomingMoviesCallback.onSuccess(response.body());
                Log.d(LOG_TAG, response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                loadUpcomingMoviesCallback.onFailure();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadMovieDetails(final LoadMovieDetailsCallback loadMovieDetailsCallback, int movieId) {
        Call<Movie> call = mTmdbEndpointInterface.getMovie(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                loadMovieDetailsCallback.onSuccess(response.body());
                Log.d(LOG_TAG, response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                loadMovieDetailsCallback.onFailure();
                t.printStackTrace();
            }
        });
    }

    private TmdbEndpointInterface getTmdbEndpointInterface() {
        // Add the authInterceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(authInterceptor);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(TmdbEndpointInterface.class);
    }

    private final Interceptor authInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader(QUERY_PARAMETER_API_KEY, API_KEY).build();
            return chain.proceed(newRequest);
        }
    };
}
