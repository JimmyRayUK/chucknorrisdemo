package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NorrisDataSourceProvider {

    private val dataSource : NorrisDataSource

    init {
        val retrofit = buildRetrofit()
        val service = retrofit.create(NorrisService::class.java)
        dataSource = RetrofitNorrisDataSource(service)
    }

    fun getDataSource() : NorrisDataSource = dataSource

    private fun buildRetrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://api.icndb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}