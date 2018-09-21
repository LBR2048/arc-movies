package ardjomand.leonardo.arcmovies.upcomingmovies

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ardjomand.leonardo.arcmovies.R
import ardjomand.leonardo.arcmovies.data.MoviesRepositoryImpl
import ardjomand.leonardo.arcmovies.model.UpcomingMovie
import ardjomand.leonardo.arcmovies.upcomingmovies.UpcomingMoviesFragment.OnUpcomingMoviesFragmentListener
import java.util.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnUpcomingMoviesFragmentListener]
 * interface.
 */
//endregion

class UpcomingMoviesFragment : Fragment(), UpcomingMoviesContract.View {

    //region Member Variables
    private var mColumnCount = 2
    private var mListener: OnUpcomingMoviesFragmentListener? = null
    private var mPresenter: UpcomingMoviesContract.Presenter? = null
    private var mAdapter: UpcomingMoviesAdapter? = null
    private var mUpcomingMovies: MutableList<UpcomingMovie>? = ArrayList()
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        arguments?.let {
            mColumnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        UpcomingMoviesPresenter(this, MoviesRepositoryImpl())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming_movies_list, container, false)

        // Setup the RecyclerView
        if (view is RecyclerView) {
            val context = view.getContext()

            if (context != null) {
                mAdapter = UpcomingMoviesAdapter(context, ArrayList(), mListener)
                view.adapter = mAdapter

                val gridLayoutManager = GridLayoutManager(context, mColumnCount)
                view.layoutManager = gridLayoutManager

                // Setup pagination
                view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val visibleItemsCount = gridLayoutManager.childCount
                        val totalItemsCount = gridLayoutManager.itemCount
                        val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()
                        Log.d("scroll", "scroll captured")

                        if (totalItemsCount - (visibleItemsCount + firstVisibleItemPosition) < PAGINATION_THRESHOLD) {
                            mPresenter!!.loadMoreUpcomingMovies()
                            Log.d("scroll", "presenter called")
                        }
                    }
                })
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            mPresenter!!.reloadUpcomingMovies()
        } else {
            // This is not needed with setRetainInstance(true)
            val upcomingMovies: List<UpcomingMovie> = savedInstanceState.getParcelableArrayList(LIST_DATA)
            Log.d(LOG_LIST_STATE, "Retrieving ${mUpcomingMovies!!.size} movies")

            mAdapter!!.replaceMovies(upcomingMovies)
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnUpcomingMoviesFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException("${context!!} must implement OnUpcomingMoviesFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // This is not needed with setRetainInstance(true)
        Log.d(LOG_LIST_STATE, "Saving ${mUpcomingMovies!!.size} movies")
        outState.putParcelableArrayList(LIST_DATA, mUpcomingMovies as ArrayList<out Parcelable>?)
    }
    //endregion

    //region Base Callbacks
    override fun setPresenter(presenter: UpcomingMoviesContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoading(visibility: Boolean) {
        // TODO This can be improved by showing a progress bar as the last item of the list instead of a toast
        if (visibility) Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    //endregion

    override fun showUpcomingMovies(upcomingMovies: List<UpcomingMovie>) {
        Log.d(LOG_LIST_STATE, "Received ${upcomingMovies.size} movies from Presenter")
        mUpcomingMovies!!.addAll(upcomingMovies)
        mAdapter!!.addMovies(upcomingMovies)
        mAdapter!!.notifyDataSetChanged()
        Log.d(LOG_LIST_STATE, "Total: ${mUpcomingMovies!!.size} movies")
    }

    interface OnUpcomingMoviesFragmentListener {
        fun onMovieClicked(upcomingMovie: UpcomingMovie)
    }

    companion object {

        //region Constants
        private const val ARG_COLUMN_COUNT = "column-count"
        private const val PAGINATION_THRESHOLD = 5.0
        private const val LIST_DATA = "List_Data"
        private const val LOG_LIST_STATE = "List_state"

        @JvmStatic
        fun newInstance(columnCount: Int): UpcomingMoviesFragment {
            val fragment = UpcomingMoviesFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}