package ardjomand.leonardo.arcmovies.upcomingmovies

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import ardjomand.leonardo.arcmovies.Constants
import ardjomand.leonardo.arcmovies.R
import ardjomand.leonardo.arcmovies.Utils
import ardjomand.leonardo.arcmovies.model.UpcomingMovie
import ardjomand.leonardo.arcmovies.upcomingmovies.UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener
import kotlinx.android.synthetic.main.fragment_upcoming_movies.view.*

/**
 * [RecyclerView.Adapter] that can display a [UpcomingMovie] and makes a call to the
 * specified [UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener].
 */
class UpcomingMoviesAdapter(private val mContext: Context,
                            private val mUpcomingMovies: MutableList<UpcomingMovie>,
                            private val mListener: OnUpcomingMoviesFragmentListener?)
    : RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_upcoming_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mUpcomingMovies[position]
        holder.mTitleView.text = mUpcomingMovies[position].title
        Picasso.with(mContext)
                .load(Constants.BASE_POSTER_URL + mUpcomingMovies[position].posterPath!!)
                .resize(400, 800)
                .centerInside()
                .into(holder.mPosterView)
        holder.mGenreView.text =
                Utils.showListAsString(mUpcomingMovies[position].genreNames, ", ")
        holder.mReleaseDateView.text = mContext.getString(R.string.release_date,
                mUpcomingMovies[position].releaseDate)

        holder.mView.setOnClickListener {
            mListener?.onMovieClicked(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mUpcomingMovies.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.movie_title
        val mPosterView: ImageView = mView.movie_poster
        val mGenreView: TextView = mView.movie_genre
        val mReleaseDateView: TextView = mView.movie_release_date
        var mItem: UpcomingMovie? = null

        override fun toString(): String {
            return super.toString() + " '" + mTitleView.text + "'"
        }
    }

    fun addMovies(upcomingMovies: List<UpcomingMovie>) {
        mUpcomingMovies.addAll(upcomingMovies)
        notifyDataSetChanged()
    }

    fun replaceMovies(upcomingMovies: List<UpcomingMovie>) {
        mUpcomingMovies.clear()
        mUpcomingMovies.addAll(upcomingMovies)
        notifyDataSetChanged()
    }
}
