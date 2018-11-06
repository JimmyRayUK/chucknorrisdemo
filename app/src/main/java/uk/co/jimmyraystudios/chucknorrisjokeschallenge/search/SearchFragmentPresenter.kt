package uk.co.jimmyraystudios.chucknorrisjokeschallenge.search

import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.Joke
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.escapeHtml

/**
 * Basic example of a presenter for the search fragment
 * All dependencies are injected through the constructor and no Android classes are referenced.
 *
 */
class SearchFragmentPresenter(val view: SearchFragmentContract.View, val dataSource: NorrisDataSource) : NorrisDataSource.ResponseListener {

    fun onSearchClicked(searchTerm: String) {
        val nameParts = parseSearchString(searchTerm)
        if (nameParts.size > 2) {
            view.showError("Oops", "Please only two names max")
        } else if (nameParts.isEmpty()) {
            view.showError("Oops", "Please enter a name")
        } else {
            val first = nameParts.get(0)
            var last = ""
            if (nameParts.size > 1) {
                last = nameParts[1]
            }
            dataSource.getCustomNameJoke(this, first, last)
        }
    }

    private fun parseSearchString(searchTerm: String): List<String> {
        if (searchTerm.isNotEmpty()) {
            return searchTerm.split(" ")
        }
        return listOf()
    }

    override fun onResponse(jokes: List<Joke>) {
        view.showJoke(jokes.get(0).joke.escapeHtml().toString())
    }

    override fun onError(message: String) {
        view.showError("Error", message)
    }
}
