package uk.co.jimmyraystudios.chucknorrisjokeschallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.Joke
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.RetrofitNorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.RetrofitNorrisService

class MainActivity : AppCompatActivity() {

    lateinit var dataSource : RetrofitNorrisDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = buildRetrofit()
        val service = retrofit.create(RetrofitNorrisService::class.java)
        dataSource = RetrofitNorrisDataSource(service)

        btn_random_joke.setOnClickListener() { onGetRandomJokeClicked() }
    }


    fun onGetRandomJokeClicked() {
        dataSource.getRandomJoke(object : NorrisDataSource.ResponseListener {
            override fun onResponse(jokes : List<Joke>) {
                showJokeDialog(jokes.get(0).joke)
            }

            override fun onError(message: String) {
                showJokeDialog("Error: " + message)
            }

        })
    }

    fun showJokeDialog(joke : String) {
        AlertDialog.Builder(this@MainActivity)
                .setCancelable(true)
                .setMessage(joke.escapeHtml())
                .setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
                .show()
    }

    fun buildRetrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://api.icndb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
