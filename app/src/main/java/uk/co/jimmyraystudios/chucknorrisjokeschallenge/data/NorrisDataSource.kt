package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

/**
 * Data source for Chuck Norris Jokes
 */
interface NorrisDataSource {

    /**
     * Gets a random joke and passes it back to the listener
     */
    fun getRandomJoke(listener: ResponseListener)

    /**
     * Get a joke with a customer first name and last name
     * if either name is empty it will be left as Chuck or Norris
     */
    fun getCustomNameJoke(listener: ResponseListener, firstName: String, lastName: String)

    /**
     * Gets multiple random jokes and returns them to the listener in an array
     * multiple calls may bring back the same jokes multiple times
     */
    fun getMultipleJokes(listener: ResponseListener, count: Int)

    /**
     * Interface for DataSource Callbacks
     */
    interface ResponseListener {

        /**
         * Called on successful joke response
         */
        fun onResponse(jokes: List<Joke>)

        /**
         * Called when there's either a server or parsing error
         */
        fun onError(message: String)
    }
}