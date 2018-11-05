package uk.co.jimmyraystudios.chucknorrisjokeschallenge.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.MainActivityInteractor
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.R
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.Joke
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSourceProvider
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragment
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragmentContract


class HomeFragment : Fragment() {

    private var interactor : MainActivityInteractor? = null

    private lateinit var dataSource : NorrisDataSource

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initViews(view)
        dataSource = NorrisDataSourceProvider.getDataSource()

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

    fun initViews(view : View) {
        view.findViewById<Button>(R.id.btn_random_joke).setOnClickListener() { onGetRandomJokeClicked() }
        view.findViewById<Button>(R.id.btn_search_joke).setOnClickListener() { onSearchForJokeClicked() }
    }

    fun onGetRandomJokeClicked() {
        dataSource.getRandomJoke(object : NorrisDataSource.ResponseListener {
            override fun onResponse(jokes : List<Joke>) {
                interactor?.showJokeDialog(jokes.get(0).joke)
            }

            override fun onError(message: String) {
                interactor?.showJokeDialog("Error: " + message)
            }

        })
    }

    fun onSearchForJokeClicked() {
        interactor?.openSearch()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
