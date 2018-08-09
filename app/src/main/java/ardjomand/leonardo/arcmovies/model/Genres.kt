package ardjomand.leonardo.arcmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by unity on 04/03/18.
 */

data class Genres(

        @SerializedName("genres")
        @Expose
        var genres: List<Genre>? = null
)
