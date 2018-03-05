package ardjomand.leonardo.arcmovies.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import ardjomand.leonardo.arcmovies.Constants;
import ardjomand.leonardo.arcmovies.data.MoviesRepository;
import ardjomand.leonardo.arcmovies.model.Genres;
import ardjomand.leonardo.arcmovies.model.MovieDetails;
import ardjomand.leonardo.arcmovies.model.UpcomingMovies;
import okhttp3.HttpUrl;
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

public class MoviesRemoteRepository implements MoviesRepository {

    private static final String LOG_TAG = MoviesRemoteRepository.class.getSimpleName();

    private final TmdbEndpointInterface mTmdbEndpointInterface;

    public MoviesRemoteRepository() {
        mTmdbEndpointInterface = getTmdbEndpointInterface();
    }

    @Override
    public void loadUpcomingMovies(final LoadUpcomingMoviesCallback loadUpcomingMoviesCallback, int page) {
        Call<UpcomingMovies> call = mTmdbEndpointInterface.getUpcomingMovies(page);
        call.enqueue(new Callback<UpcomingMovies>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingMovies> call, @NonNull Response<UpcomingMovies> response) {
                Log.d(LOG_TAG, response.toString());
                if (response.isSuccessful()) {
                    loadUpcomingMoviesCallback.onSuccess(response.body());
                } else {
                    loadUpcomingMoviesCallback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingMovies> call, @NonNull Throwable t) {
                loadUpcomingMoviesCallback.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadMovieDetails(final LoadMovieDetailsCallback loadMovieDetailsCallback, int movieId) {
        Call<MovieDetails> call = mTmdbEndpointInterface.getMovieDetails(movieId);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
                Log.d(LOG_TAG, response.toString());
                if (response.isSuccessful()) {
                    loadMovieDetailsCallback.onSuccess(response.body());
                } else {
                    loadMovieDetailsCallback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
                loadMovieDetailsCallback.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadGenres(final LoadGenresCallBack loadGenresCallBack) {
        Call<Genres> call = mTmdbEndpointInterface.getGenres();
        call.enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(@NonNull Call<Genres> call, @NonNull Response<Genres> response) {
                Genres body = response.body();
                if (response.isSuccessful() && body != null) {
                    loadGenresCallBack.onSuccess(body.getGenres());
                } else {
                    loadGenresCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Genres> call, @NonNull Throwable t) {
                loadGenresCallBack.onFailure(t.getMessage());
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
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(TmdbEndpointInterface.class);
    }

    private final Interceptor authInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url()
                    .newBuilder()
                    .addQueryParameter(Constants.QUERY_PARAMETER_API_KEY, Constants.API_KEY)
                    .build();

            request = request
                    .newBuilder()
                    .url(url)
                    .build();

            return chain.proceed(request);
        }
    };
}
