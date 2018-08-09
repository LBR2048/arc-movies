package ardjomand.leonardo.arcmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by unity on 25/02/18.
 */

data class UpcomingMovies(
        @SerializedName("results")
        @Expose
        var results: List<UpcomingMovie>? = null,

        @SerializedName("page")
        @Expose
        var page: Int? = null,

        @SerializedName("total_results")
        @Expose
        var totalResults: Int? = null,

        @SerializedName("total_pages")
        @Expose
        var totalPages: Int? = null) {

    override fun toString(): String {
        return "UpcomingMovies{" +
                "results=" + results +
                ", page=" + page +
                '}'.toString()
    }
}