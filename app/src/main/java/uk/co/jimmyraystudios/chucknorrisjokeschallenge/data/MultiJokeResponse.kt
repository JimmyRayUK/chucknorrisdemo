package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import com.google.gson.annotations.SerializedName

data class MultiJokeResponse(
        @SerializedName("type") val type: String = "",
        @SerializedName("value") val jokes: List<Joke> = listOf()
)