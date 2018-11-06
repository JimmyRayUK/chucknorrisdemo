package uk.co.jimmyraystudios.chucknorrisjokeschallenge.infinite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.MainActivityInteractor
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.R
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.Joke
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSourceProvider

/**
 * Infinite jokes fragment in basic form
 * Loads jokes in batches of 20 when the last 6 are being shown
 * handles basic server errors
 */
class InfiniteJokesFragment : Fragment(), NorrisDataSource.ResponseListener {

    private var interactor: MainActivityInteractor? = null

    private lateinit var dataSource: NorrisDataSource

    private lateinit var recycler: RecyclerView

    private lateinit var adapter: InfiniteJokeRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_infinite_jokes, container, false)

        initViews(view)
        dataSource = NorrisDataSourceProvider.getDataSource()
        onLoadJokesRequested()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityInteractor) {
            interactor = context
        } else {
            throw RuntimeException(context.toString() + " must implement MainActivityInteractor")
        }
    }

    override fun onDetach() {
        super.onDetach()
        interactor = null
    }

    override fun onResponse(jokes: List<Joke>) {
        adapter.onNewItemsLoaded(jokes.map { it.joke })
    }

    override fun onError(message: String) {
        interactor?.showErrorDialog("Oops", "Something went wrong loading more jokes, try slowing down!")
    }

    fun initViews(view: View) {
        recycler = view.findViewById(R.id.recycler_inifinte_jokes)
        recycler.layoutManager = LinearLayoutManager(view.context)
        adapter = InfiniteJokeRecyclerAdapter({ onLoadJokesRequested() }, recycler)
        recycler.adapter = adapter
    }

    private fun onLoadJokesRequested() {
        dataSource.getMultipleJokes(this, 20)
    }

    companion object {
        @JvmStatic
        fun newInstance() = InfiniteJokesFragment()
    }
}
