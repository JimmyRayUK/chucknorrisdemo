package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitNorrisService {

    companion object {
        val QUERY_FIRST_NAME = "firstName"
        val QUERY_LAST_NAME = "lastName"
    }

    @GET("jokes/random")
    fun getJoke(@QueryMap arguments : Map<String, String>) : Call<SingleJokeResponse>
}