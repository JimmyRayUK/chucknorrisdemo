package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import com.google.gson.annotations.SerializedName

data class SingleJokeResponse(
    @SerializedName("type") val type: String = "",
    @SerializedName("value") val joke: Joke = Joke()
)