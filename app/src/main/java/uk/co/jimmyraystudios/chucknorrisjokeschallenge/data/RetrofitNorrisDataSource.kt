package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitNorrisDataSource(val service: NorrisService) : NorrisDataSource {

    override fun getRandomJoke(listener: NorrisDataSource.ResponseListener) {
        service.getJoke(mapOf()).enqueue(SingleResponseForwarder(listener))
    }

    override fun getCustomNameJoke(listener: NorrisDataSource.ResponseListener, firstName: String, lastName: String) {
        val argMap = HashMap<String, String>()
        if (firstName.isNotEmpty()) {
            argMap.put(NorrisService.QUERY_FIRST_NAME, firstName)
        }
        if (lastName.isNotEmpty()) {
            argMap.put(NorrisService.QUERY_LAST_NAME, lastName)
        }

        service.getJoke(argMap).enqueue(SingleResponseForwarder(listener))
    }

    override fun getMultipleJokes(listener: NorrisDataSource.ResponseListener, count : Int) {
        service.getMultipleJokes(count).enqueue(MultiResponseForwarder(listener))
    }

    private class SingleResponseForwarder(listener: NorrisDataSource.ResponseListener) : ResponseForwarder<SingleJokeResponse>(listener) {

        override fun onSuccessResponse(response: SingleJokeResponse) {
            listener.onResponse(listOf(response.joke))
        }
    }


    private class MultiResponseForwarder(listener: NorrisDataSource.ResponseListener) : ResponseForwarder<MultiJokeResponse>(listener) {

        override fun onSuccessResponse(response: MultiJokeResponse) {
            listener.onResponse(response.jokes)
        }
    }

    private abstract class ResponseForwarder<T>(val listener: NorrisDataSource.ResponseListener) : Callback<T> {

        abstract fun onSuccessResponse(response : T)

        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            if (response?.body() != null) {
                onSuccessResponse(response?.body()!!)
            } else {
                listener.onError("Bad Response From Server")
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            t?.localizedMessage.let {
                listener.onError(t!!.localizedMessage)
            }
            listener.onError("Service Failure")
        }

    }


}