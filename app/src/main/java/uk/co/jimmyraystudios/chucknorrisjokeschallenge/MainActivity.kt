package uk.co.jimmyraystudios.chucknorrisjokeschallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.*
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.home.HomeFragment
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragment

class MainActivity : AppCompatActivity(), MainActivityInteractor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMainFragment()
    }

    override fun openSearch() {
        val searchFragment = SearchFragment.newInstance()
        switchFragment(searchFragment)
    }

    override fun showJokeDialog(joke : String) {
        AlertDialog.Builder(this@MainActivity)
                .setCancelable(true)
                .setMessage(joke.escapeHtml())
                .setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
                .show()
    }

    override fun showErrorDialog(title : String, message : String) {
        AlertDialog.Builder(this@MainActivity)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
                .show()
    }

    private fun loadMainFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, HomeFragment())
                .addToBackStack("")
                .commit()
    }

    private fun switchFragment(newFragment : Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .addToBackStack("")
                .commit()
    }
}
