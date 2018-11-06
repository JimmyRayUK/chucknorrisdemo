package uk.co.jimmyraystudios.chucknorrisjokeschallenge.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * No time to use a DI framework in this project, so this is an example of a dependency provider (AKA Singleton)
 * It's not a pattern to use instead of dependency injection and is often considered an anti pattern if used in that way
 * However in this use case it's only being accessed to inject from top level classes
 */
object NorrisDataSourceProvider {

    private val dataSource: NorrisDataSource

    init {
        val retrofit = buildRetrofit()
        val service = retrofit.create(NorrisService::class.java)
        dataSource = RetrofitNorrisDataSource(service)
    }

    fun getDataSource(): NorrisDataSource = dataSource

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://api.icndb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}