package uk.co.jimmyraystudios.chucknorrisjokeschallenge.search

interface SearchFragmentContract {

    interface View {
        fun showJoke(joke: String)
        fun exitScreen()
        fun showError(title: String, message: String)
    }

}