package uk.co.jimmyraystudios.chucknorrisjokeschallenge

/**
 * Interactor interface to allow the fragments to communicate back to the main activity
 */
interface MainActivityInteractor {

    fun showJokeDialog(joke: String)

    fun showErrorDialog(title: String, message: String)

    fun openSearch()

    fun showInfiniteJokes()
}