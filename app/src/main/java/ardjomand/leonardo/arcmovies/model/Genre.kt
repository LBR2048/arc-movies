package ardjomand.leonardo.arcmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by unity on 04/03/18.
 */

data class Genre (

        @SerializedName("id")
        @Expose
        var id: Int? = null,

        @SerializedName("name")
        @Expose
        var name: String? = null
)