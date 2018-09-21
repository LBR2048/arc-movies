package ardjomand.leonardo.arcmovies.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by unity on 25/02/18.
 */

@Parcelize // Android Kotlin Extensions experimental feature
data class UpcomingMovie(

        @SerializedName("vote_count")
        @Expose
        var voteCount: Int? = null,

        @SerializedName("id")
        @Expose
        var id: Int? = null,

        @SerializedName("video")
        @Expose
        var video: Boolean? = null,

        @SerializedName("vote_average")
        @Expose
        var voteAverage: Double? = null,

        @SerializedName("title")
        @Expose
        var title: String? = null,

        @SerializedName("popularity")
        @Expose
        var popularity: Double? = null,

        @SerializedName("poster_path")
        @Expose
        var posterPath: String? = null,

        @SerializedName("original_language")
        @Expose
        var originalLanguage: String? = null,

        @SerializedName("original_title")
        @Expose
        var originalTitle: String? = null,

        @SerializedName("genre_ids")
        @Expose
        var genreIds: List<Int>? = null,

        @SerializedName("backdrop_path")
        @Expose
        var backdropPath: String? = null,

        @SerializedName("adult")
        @Expose
        var adult: Boolean? = null,

        @SerializedName("overview")
        @Expose
        var overview: String? = null,

        @SerializedName("release_date")
        @Expose
        var releaseDate: String? = null,


        var genreNames: List<String>? = null) : Parcelable {

    override fun toString(): String {
        return "UpcomingMovie{" +
                "id=" + id +
                ", title='" + title + '\''.toString() +
                '}'.toString()
    }
}
