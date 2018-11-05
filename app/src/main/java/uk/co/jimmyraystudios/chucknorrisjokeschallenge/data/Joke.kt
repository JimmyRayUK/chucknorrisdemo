package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import com.google.gson.annotations.SerializedName

data class Joke(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("joke") val joke: String = "",
        @SerializedName("categories") val categories: List<String> = listOf()
)