package uk.co.jimmyraystudios.chucknorrisjokeschallenge

interface MainActivityInteractor {

    fun showJokeDialog(joke : String)

    fun showErrorDialog(title: String, message : String)
    fun openSearch()
}