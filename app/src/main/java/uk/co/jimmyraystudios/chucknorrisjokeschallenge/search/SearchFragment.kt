package uk.co.jimmyraystudios.chucknorrisjokeschallenge.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.MainActivityInteractor
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.R
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSourceProvider


class SearchFragment : Fragment(), uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragmentContract.View {
    private var interactor: MainActivityInteractor? = null
    private lateinit var edtSearchTerm: EditText
    private lateinit var presenter: SearchFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        presenter = SearchFragmentPresenter(this, NorrisDataSourceProvider.getDataSource())
        initViews(view)
        return view;
    }

    private fun initViews(view: View) {
        view.findViewById<Button>(R.id.btn_search).setOnClickListener { onSearchClicked() }
        edtSearchTerm = view.findViewById(R.id.edt_search)
    }

    private fun onSearchClicked() {
        presenter.onSearchClicked(edtSearchTerm?.text.toString())
    }

    override fun showJoke(joke: String) {
        interactor?.showJokeDialog(joke)
    }

    override fun exitScreen() {
        TODO()
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

    override fun showError(title: String, message: String) {
        interactor?.showErrorDialog(title, message)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
