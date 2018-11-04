package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

interface NorrisDataSource {
    fun getRandomJoke(listener: ResponseListener)
    fun getCustomNameJoke(listener: ResponseListener, lastName : String, firstName : String)

    interface ResponseListener {
        fun onResponse(jokes : List<Joke>)

        fun onError(message : String)
    }
}