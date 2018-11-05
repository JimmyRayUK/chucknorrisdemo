package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitNorrisDataSource(val service : NorrisService) : NorrisDataSource {

    override fun getRandomJoke(listener: NorrisDataSource.ResponseListener) {
        service.getJoke(mapOf()).enqueue(ResponseForwarder(listener))
    }

    override fun getCustomNameJoke(listener: NorrisDataSource.ResponseListener, firstName : String, lastName : String) {
        val argMap = HashMap<String, String>()
        if(firstName.isNotEmpty()) {
            argMap.put(NorrisService.QUERY_FIRST_NAME, firstName)
        }
        if(lastName.isNotEmpty()) {
            argMap.put(NorrisService.QUERY_LAST_NAME, lastName)
        }

        service.getJoke(argMap).enqueue(ResponseForwarder(listener))
    }


    private class ResponseForwarder(val listener : NorrisDataSource.ResponseListener) : Callback<SingleJokeResponse> {
        override fun onResponse(call: Call<SingleJokeResponse>?, response: Response<SingleJokeResponse>?) {
            if(response?.body() != null) {
                listener.onResponse(listOf(response!!.body()!!.joke))
            } else {
                listener.onError("Bad Response From Server")
            }
        }

        override fun onFailure(call: Call<SingleJokeResponse>?, t: Throwable?) {
            t?.localizedMessage.let {
                listener.onError(t!!.localizedMessage)
            }
            listener.onError("Service Failure")
        }

    }


}